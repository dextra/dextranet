package br.com.dextra.repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.FilterOperator;
//import com.google.appengine.api.search.QueryOptions;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

public class PostRepository {

	public static Iterable<Entity> buscarPosts(String maxResults, String q) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query query = new Query("post");

		PreparedQuery prepared = datastore.prepare(query);
		FetchOptions opts = FetchOptions.Builder.withDefaults();

		if (!q.equals("")){

		}

		if (!maxResults.equals("")) {
			opts.limit(Integer.parseInt(maxResults));
		}

		return prepared.asIterable(opts);
	}

	// Integer.parseInt(maxResults)

}
