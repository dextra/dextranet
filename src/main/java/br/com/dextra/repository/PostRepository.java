package br.com.dextra.repository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

import com.google.appengine.api.search.*;
import com.google.appengine.api.search.SearchServicePb.SearchRequest;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

public class PostRepository {

	public static Iterable<Entity> buscarTodosOsPosts(int maxResults) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query query = new Query("post");
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);

		return prepared.asIterable(opts);
	}

	public static Iterable<Entity> buscarPosts(int maxResults, String q) {

/*		QueryOptions options = QueryOptions.newBuilder().setFieldsToSnippet(
				"post").setFieldsToReturn("conteudo").setLimit(maxResults)
				.build();

		Query query = Query.newBuilder().setOptions(options).build(q);
		PreparedQuery prepared = datastore.prepare((Datastore) query);
		return prepared.asIterable();*/
		return null;
	}

	// Integer.parseInt(maxResults)

}
