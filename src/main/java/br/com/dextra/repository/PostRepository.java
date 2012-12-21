// TODO: Como conversamos hoje, as classes de repositorio deverao ficar na mesma package dos
// outros objetos relacionados a um mesmo contexto. Sendo assim, esse cara aqui precisa ser
// migrado para outra package.
package br.com.dextra.repository;

import java.util.ArrayList;
import java.util.Date;
import br.com.dextra.utils.EntityJsonConverter;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.gson.JsonObject;

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
		EntityJsonConverter.toJson(getIndex("post").search(query));
		return	null;



	}

	public void criaNovoPost(String titulo, String conteudo, String usuario) {

		long time = new Date().getTime();
		String id = String.valueOf(time);
		Key key = KeyFactory.createKey("post", id);
		Date data = new Date();

		this.criaNovoPost(titulo, conteudo, usuario, id,key,data);
	}
	public static void criaNovoPost(String titulo, String conteudo, String usuario, String id,Key key, Date data) {

		Entity valueEntity = new Entity(key);

		valueEntity.setProperty("titulo", titulo);
		valueEntity.setProperty("conteudo", conteudo);
		valueEntity.setProperty("usuario", usuario);
		valueEntity.setProperty("comentarios", 0);
		valueEntity.setProperty("likes", 0);
		valueEntity.setProperty("data", data);
		valueEntity.setProperty("dataDeAtualizacao", data);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		datastore.put(valueEntity);

		Document document = Document.newBuilder().setId(id).addField(
				Field.newBuilder().setName("titulo").setText(titulo)).addField(
				Field.newBuilder().setName("conteudo").setText(conteudo))
				.addField(
						Field.newBuilder().setName("usuario").setText(usuario))
				.addField(
						Field.newBuilder().setName("data").setText(
								data.toString())).addField(
						Field.newBuilder().setName("dataDeAtualizacao").setText(
								data.toString())).build();

		PostRepository.getIndex("post").add(document);
	}

	public boolean pegaDadosCorretos(String titulo, String conteudo,
			String usuario) {
		// TODO Auto-generated method stub
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("post");
		PreparedQuery prepare = datastore.prepare(query);

		Iterable<Entity> asIterable = prepare.asIterable();
		for (Entity entity : asIterable) {
			System.out.println("Titulo:" + entity.getProperty("titulo")
					+ " -- Conteudo: " + entity.getProperty("conteudo")
					+ "-- (" + entity.getProperty("data") + ")" + "\n");
			if (entity.getProperty("conteudo").equals(conteudo)
					&& entity.getProperty("titulo").equals(titulo)
					&& entity.getProperty("usuario").equals(usuario)) {
				return true;
			}
		}

		return true;
	}

}
