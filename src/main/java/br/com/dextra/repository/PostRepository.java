package br.com.dextra.repository;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class PostRepository {

	public static Iterable<Entity> buscarPosts() {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("post");
		PreparedQuery prepare = datastore.prepare(query);

		return prepare.asIterable();
	}

	//public static

}
