package br.com.dextra.dextranet.banner;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.BaseRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class BannerRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());

		return banner;
	}

	public List<Banner> getBannerDisponiveis() {
		
		Query query = new Query(Banner.class.getName());
		// TODO: adicionar filtro usando flags
		query.addSort(BannerFields.DATA_INICIO.getField(), SortDirection.DESCENDING);

		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable();

		List<Banner> listaDeBanners = new ArrayList<Banner>();

		for (Entity entity : asIterable) {
			listaDeBanners.add(new Banner(entity));
		}
		System.out.println(listaDeBanners.size());
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
