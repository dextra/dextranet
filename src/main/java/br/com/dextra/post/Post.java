package br.com.dextra.post;

import java.util.Date;

import br.com.dextra.repository.PostRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;


public class Post {

	public void criaNovoPost(String titulo, String conteudo, String usuario) {
		long time = new Date().getTime();
		String id = String.valueOf(time);
		Key key = KeyFactory.createKey("post", id);

		Entity valueEntity = new Entity(key);

		valueEntity.setProperty("titulo", titulo);
		valueEntity.setProperty("conteudo", conteudo);
		valueEntity.setProperty("usuario", usuario);
		valueEntity.setProperty("comentarios", 0);
		valueEntity.setProperty("likes", 0);
		Date data = new Date();
		valueEntity.setProperty("data", data);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		datastore.put(valueEntity);

//		Document document = Document.newBuilder().setId(id)
//			.addField(Field.newBuilder().setName("titulo").setText(titulo))
//			.addField(Field.newBuilder().setName("conteudo").setText(conteudo))
//			.addField(Field.newBuilder().setName("usuario").setText(usuario))
//			.addField(Field.newBuilder().setName("data").setText(data.toString()))
//			.build();
//
//		    // Put the document.
//		    PostRepository.getIndex("post").add(document);

		// System.out.println("Inserido");
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
