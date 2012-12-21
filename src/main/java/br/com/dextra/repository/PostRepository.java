// TODO: Como conversamos hoje, as classes de repositorio deverao ficar na mesma package dos
// outros objetos relacionados a um mesmo contexto. Sendo assim, esse cara aqui precisa ser
// migrado para outra package.
package br.com.dextra.repository;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.appengine.api.search.*;
import com.google.appengine.api.search.SearchServicePb.SearchRequest;
import com.google.gson.JsonObject;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

import br.com.dextra.utils.EntityJsonConverter;





public class PostRepository {

	public static Index getIndex(String index) {
	    IndexSpec indexSpec = IndexSpec.newBuilder().setName(index).build();
	    return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}

	public static Iterable<Entity> buscarTodosOsPosts(int maxResults, String key) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("post");
		if (!key.equals("")) {
			Key key2 = KeyFactory.createKey("post", key.substring(6, key
					.length() - 2));

			System.out.println(key2.toString() + " # " + key);
			query.setAncestor(key2);

		}

		query.addSort("dataDeAtualizacao", SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);

		return prepared.asIterable(opts);
	}

	//FIXME: Gabriel/Tonho estão com problemas que serão resolvidos rapida e futuramente!

	public static ArrayList<JsonObject> buscarPosts(int maxResults, String q) {

		QueryOptions options = QueryOptions.newBuilder().setFieldsToSnippet(
				"conteudo").setFieldsToReturn("conteudo","titulo").setLimit(maxResults)
				.build();

		com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query.newBuilder().setOptions(options).build(q);

		return	EntityJsonConverter.toJson(getIndex("post").search(query));



	}

	// Integer.parseInt(maxResults)

}
