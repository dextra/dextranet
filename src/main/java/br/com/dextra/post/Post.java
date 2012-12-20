package br.com.dextra.post;

import java.util.Date;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Post {

	public void criaNovoPost(String titulo, String conteudo, String usuario) {
		long time = new Date().getTime();
		Key key = KeyFactory.createKey("post", time);

		Entity valueEntity = new Entity(key);

		valueEntity.setProperty("titulo", titulo);
		valueEntity.setProperty("conteudo", conteudo);
		valueEntity.setProperty("usuario", usuario);
		valueEntity.setProperty("comentarios", 0);
		valueEntity.setProperty("likes", 0);
		Date data = new Date();
		valueEntity.setProperty("data", data);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		datastore.put(valueEntity);

		//System.out.println("Inserido");
	}

	public boolean pegaDadosCorretos(String titulo, String conteudo, String usuario) {
		// TODO Auto-generated method stub
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query = new Query("post");
		PreparedQuery prepare = datastore.prepare(query);

		Iterable<Entity> asIterable = prepare.asIterable();
		for (Entity entity : asIterable) {
			System.out.println("Titulo:" + entity.getProperty("titulo") + " -- Conteudo: " + entity.getProperty("conteudo") + "-- (" + entity.getProperty("data") + ")"+"\n");
			if (entity.getProperty("conteudo").equals(conteudo) &&
				   entity.getProperty("titulo").equals(titulo) &&
				   entity.getProperty("usuario").equals(usuario)) {
				return true;
			}
		}

		return true;
	}

}
