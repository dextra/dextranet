package br.com.dextra.dextranet.banner;

import br.com.dextra.dextranet.persistencia.BaseRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Query;

public class BannerRepository extends BaseRepository {

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());
		
		return banner;
	}
	
	public Banner getBannerAtual() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query(Banner.class.getName());
		
		return new Banner(datastore.prepare(query).asSingleEntity());
	}
}
