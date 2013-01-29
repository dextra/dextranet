package br.com.dextra.dextranet.banner;

import br.com.dextra.dextranet.persistencia.BaseRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class BannerRepository extends BaseRepository {

	public Banner criar(Banner banner) {
		this.persist(banner.toEntity());
		
		return banner;
	}
}
