package br.com.dextra.dextranet.banner;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.BaseRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.JsonObject;

public class BannerRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());
		
		return banner;
	}
	
	public List<JsonObject> getBannerDisponiveis() {
		
		Query query = new Query(Banner.class.getName());
//		query.addFilter(BannerFields.DATA_INICIO.getField(), FilterOperator.LESS_THAN_OR_EQUAL, new Date());
//		query.addFilter(BannerFields.DATA_FIM.getField(), FilterOperator.GREATER_THAN_OR_EQUAL, new Date());
		query.addSort(BannerFields.DATA_FIM.getField(), SortDirection.DESCENDING); //pegando banner de acordo com a maior data final TESTE

		PreparedQuery prepared = datastore.prepare(query);
		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(1);

		Iterable<Entity> asIterable = prepared.asIterable(opts);

		List<JsonObject> listaDeBanners = new ArrayList<JsonObject>();
		
		for(Entity entity : asIterable) {
			listaDeBanners.add(new Banner(entity).toJson());
		}

		return listaDeBanners;
	}
	
	@SuppressWarnings("deprecation")
	public Banner obterPorID(String id) {
		Query query = new Query(Banner.class.getName());
		query.addFilter(BannerFields.ID.getField(), FilterOperator.EQUAL, id);
		PreparedQuery prepared = datastore.prepare(query);
		Iterable<Entity> asIterable = prepared.asIterable(FetchOptions.Builder.withDefaults().limit(1));
		
		if(asIterable.iterator().hasNext())
			return new Banner(asIterable.iterator().next());
		
		return new Banner();
	}
}
