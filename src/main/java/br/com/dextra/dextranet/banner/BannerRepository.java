package br.com.dextra.dextranet.banner;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.utils.Data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class BannerRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());

		return banner;
	}

	public List<Banner> getBannerDisponiveis() {
		CompositeFilter bannerDisponivel = null;
		
		if (!"local".equals(System.getProperty("env")))
			bannerDisponivel = Query.CompositeFilterOperator.and(
				FilterOperator.LESS_THAN_OR_EQUAL.of(BannerFields.DATA_INICIO.getField(), Data.ultimoSegundoDoDia()),
				FilterOperator.GREATER_THAN_OR_EQUAL.of(BannerFields.DATA_FIM.getField(), Data.primeiroSegundoDoDia())
			);
		
		Query query = new Query(Banner.class.getName());
		query.setFilter(bannerDisponivel);

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
}
