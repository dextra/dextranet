// TODO: Como conversamos hoje, as classes de repositorio deverao ficar na mesma package dos
// outros objetos relacionados a um mesmo contexto. Sendo assim, esse cara aqui precisa ser
// migrado para outra package.
package br.com.dextra.repository;

import java.util.ArrayList;
import java.util.Date;

import br.com.dextra.utils.EntityJsonConverter;
import br.com.dextra.utils.Utils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.SearchServiceFactory;

public class PostRepository {

	public static Index getIndex(String index) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(index).build();
		return SearchServiceFactory.getSearchService().getIndex(indexSpec);
	}

	public static Iterable<Entity> buscarTodosOsPosts(int maxResults, int offSet) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("post");

		query.addSort("dataDeAtualizacao", SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		return prepared.asIterable(opts);
	}

	public static Iterable<Entity> buscarPosts(int maxResults, String q,
			int offSet) throws EntityNotFoundException {

		// Faço a Busca das IDs FTS
		ArrayList<String> listaDeIds = EntityJsonConverter
				.toListaDeIds(getIndex("post").search("~" + q));
		ArrayList<Key> listaDeKeys = new ArrayList<Key>();
		Key key;
		for (String id : listaDeIds) {
			key = KeyFactory.createKey("post", id);
			listaDeKeys.add(key);
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.get((Iterable<Key>) listaDeKeys);

		Query query = new Query("post");
		query.addSort("dataDeAtualizacao", SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		ArrayList<Entity> listaDeEntity = new ArrayList<Entity>();
		Entity e;
		for (String id : listaDeIds) {
			key = KeyFactory.createKey("post", id);
			e = datastore.get(key);
			listaDeEntity.add(e);
		}

/*		ArrayList<Entity> listaDatastore = new*/

		return listaDeEntity;
	}



	public Entity criaNovoPost(String titulo, String conteudo, String usuario) {


		String id = Utils.geraID();
		Key key = KeyFactory.createKey("post", id);
		Date data = new Date();

		return PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);
	}

	public static Entity criaNovoPost(String titulo, String conteudo,
			String usuario, String id, Key key, Date data) {

		Entity valueEntity = new Entity(key);

		valueEntity.setProperty("id", id);
		valueEntity.setProperty("titulo", titulo);

		valueEntity.setProperty("conteudo", new Text(conteudo));
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
						Field.newBuilder().setName("dataDeAtualizacao")
								.setText(data.toString())).addField(
						Field.newBuilder().setName("id").setText(id)).build();

		getIndex("post").add(document);

		return valueEntity;
	}
}
