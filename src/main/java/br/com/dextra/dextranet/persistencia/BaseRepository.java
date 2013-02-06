package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BaseRepository {

	protected Key persist(Entity valueEntity) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		return datastore.put(valueEntity);
	}

	protected <T extends Entidade> Entity obtemPorId(String id, Class<T> clazz) throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(clazz.getName(), id);
		return datastore.get(key);
	}

	public BaseRepository() {
		super();
	}

}