package br.com.dextra.dextranet.banner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.utils.Data;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class BannerRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());
		return banner;
	}

	public Banner criar(String titulo, BlobKey blobKey, String dataInicioFormatada, String dataFimFormatada, String link) throws ParseException, DataNaoValidaException, NullBlobkeyException, NullUserException {


		Date dataInicio = Data.primeiroSegundo(Data.stringParaData(dataInicioFormatada));
		Date dataFim = Data.ultimoSegundo(Data.stringParaData(dataFimFormatada));

		if (blobKey == null)
			throw new NullBlobkeyException();

		if (dataBannerNaoEhValida(dataInicio, dataFim)) {
			throw new DataNaoValidaException();
		}
		
		String usuario = null;
		if ("local".equals(System.getProperty("env")))
			usuario = "login.google";
		else {

			User user = UserServiceFactory.getUserService().getCurrentUser();
			if (user == null)
				throw new NullUserException();
			usuario = user.getNickname();
		}

		return criar(new Banner(titulo, blobKey, dataInicio, dataFim, Data.igualADataDeHojeOuAnterior(dataInicio),
				Data.anteriorADataDeHoje(dataFim), usuario, new Date(), true, link));
	}

	private boolean dataBannerNaoEhValida(Date dataInicio, Date dataFim) {
		return dataFim.before(dataInicio);
	}

	public List<Banner> getBannerDisponiveis(Boolean atuais) {

		Query query = new Query(Banner.class.getName());

		if (atuais) {
			query.setFilter(Query.CompositeFilterOperator.and(
					FilterOperator.EQUAL.of(BannerFields.JA_COMECOU.getField(), new Boolean(true)),
					FilterOperator.EQUAL.of(BannerFields.JA_TERMINOU.getField(), new Boolean(false))));

			query.addSort(BannerFields.DATA_DE_ATUALIZACAO.getField(), SortDirection.DESCENDING);
		} else
			query.addSort(BannerFields.DATA_DE_ATUALIZACAO.getField(), SortDirection.DESCENDING);

		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable();

		List<Banner> listaDeBanners = new ArrayList<Banner>();

		for (Entity entity : asIterable) {
			listaDeBanners.add(new Banner(entity));
		}

		return listaDeBanners;
	}

	public Banner obterPorID(String id) {
		Query query = new Query(Banner.class.getName());

		query.setFilter(FilterOperator.EQUAL.of(BannerFields.ID.getField(), id));

		PreparedQuery prepared = datastore.prepare(query);

		return new Banner(prepared.asSingleEntity());
	}

	public void atualizaFlags() {
		atualizaFlagJaTerminou(BannerFields.JA_TERMINOU.getField(), FilterOperator.LESS_THAN,
				BannerFields.DATA_FIM.getField(), new Boolean(true));
		atualizaFlagJaComecou(BannerFields.JA_COMECOU.getField(), FilterOperator.LESS_THAN_OR_EQUAL,
				BannerFields.DATA_INICIO.getField(), new Boolean(true));
	}

	public Banner atualizaFlagsDepoisDaEdicao(Banner banner) {
		
		if (banner.getDataFim().after(Data.ultimoSegundoDeHoje())) 
			banner.setJaTerminou(false);
		else 
			banner.setJaTerminou(true);
		
		if (banner.getDataInicio().before(Data.primeiroSegundoDeHoje()))
			banner.setJaComecou(false);
		else
			banner.setJaComecou(true);
			
		return banner;
	}
	
	public void atualizaFlagJaTerminou(String flag, FilterOperator flagOperator, String campoData, Boolean bool) {

		Query query = criaQuery(flag, flagOperator, campoData);

		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable();

		setFlag(asIterable, bool, "terminou");
	}

	public void atualizaFlagJaComecou(String flag, FilterOperator flagOperator, String campoData, Boolean bool) {

		Query query = criaQuery(flag, flagOperator, campoData);

		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable();

		setFlag(asIterable, bool, "comecou");
	}

	public void setFlag(Iterable<Entity> asIterable, Boolean bool, String flag) {
		for (Entity entity : asIterable) {
			Banner banner = new Banner(entity);
			if (flag.equals("comecou"))
				setFlagJaComecou(banner, bool);
			else
				setFlagJaTerminou(banner, bool);
			criar(banner);
		}
	}

	public void setFlagJaComecou(Banner banner, Boolean op) {
		banner.setJaComecou(op);
	}

	public void setFlagJaTerminou(Banner banner, Boolean op) {
		banner.setJaTerminou(op);
	}

	public Query criaQuery(String flag, FilterOperator flagOperator, String campoData) {

		Query query = new Query(Banner.class.getName());

		query.setFilter(CompositeFilterOperator.and(FilterOperator.EQUAL.of(flag, new Boolean(false)),
				flagOperator.of(campoData, Data.primeiroSegundoDeHoje())));

		return query;
	}

	public Image transformaImagem(String id) {
		BlobKey blobKey = this.obterPorID(id).getBlobKey();
		
		ImagesService imagesService = ImagesServiceFactory.getImagesService();

	    Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
	    Transform resize = ImagesServiceFactory.makeResize(300, 70);
	    
	    return imagesService.applyTransform(resize, oldImage);
	}
}
