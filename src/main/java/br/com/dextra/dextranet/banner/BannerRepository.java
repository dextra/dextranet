package br.com.dextra.dextranet.banner;

import br.com.dextra.dextranet.persistencia.BaseRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class BannerRepository extends BaseRepository {

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());
		
		return banner;
	}
	
	public Banner getBannerAtual() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(Banner.class.getName());

		PreparedQuery prepared = datastore.prepare(query);
		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(1);

		Iterable<Entity> asIterable = prepared.asIterable(opts);

		if (asIterable.iterator().hasNext())
			return new Banner(asIterable.iterator().next());

		return null;
	}
}
