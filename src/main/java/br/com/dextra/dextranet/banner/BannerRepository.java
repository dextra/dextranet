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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class BannerRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());
		return banner;
	}

	public Banner criar(String titulo, BlobKey blobKey, String dataInicioFormatada, String dataFimFormatada) throws ParseException, DataNaoValidaException, NullBlobkeyException, NullUserException {


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
				Data.anteriorADataDeHoje(dataFim), usuario, new Date()));
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

			query.addSort(BannerFields.DATA_INICIO.getField(), SortDirection.DESCENDING);
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

	@SuppressWarnings("deprecation")
	public Banner obterPorID(String id) {
		Query query = new Query(Banner.class.getName());

		query.addFilter(BannerFields.ID.getField(), FilterOperator.EQUAL, id);

		PreparedQuery prepared = datastore.prepare(query);

		return new Banner(prepared.asSingleEntity());
	}

	public void atualizaFlags() {
		atualizaFlagJaComecou();
		atualizaFlagJaTerminou();
	}

	public void atualizaFlagJaTerminou() {

		Query query = criaQuery(BannerFields.JA_TERMINOU.getField(), FilterOperator.LESS_THAN,
				BannerFields.DATA_FIM.getField());

		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable();

		for (Entity entity : asIterable) {
			Banner banner = new Banner(entity);
			System.out.println("Banner " + banner.getTitulo() + " esta indisponivel");
			banner.setJaTerminou(true);
			criar(banner);
		}
	}

	public void atualizaFlagJaComecou() {

		Query query = criaQuery(BannerFields.JA_COMECOU.getField(), FilterOperator.LESS_THAN_OR_EQUAL,
				BannerFields.DATA_INICIO.getField());

		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable();

		for (Entity entity : asIterable) {
			Banner banner = new Banner(entity);
			System.out.println("Banner " + banner.getTitulo() + " esta disponivel");
			banner.setJaComecou(true);
			criar(banner);
		}
	}

	public Query criaQuery(String flag, FilterOperator flagOperator, String campoData) {

		Query query = new Query(Banner.class.getName());

		query.setFilter(CompositeFilterOperator.and(FilterOperator.EQUAL.of(flag, new Boolean(false)),
				flagOperator.of(campoData, Data.primeiroSegundoDeHoje())));

		return query;
	}

}
