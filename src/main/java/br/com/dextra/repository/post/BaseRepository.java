package br.com.dextra.repository.post;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

public class BaseRepository {

	protected static Key persist(Entity valueEntity) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		return datastore.put(valueEntity);
	}

	public BaseRepository() {
		super();
	}

}