package br.com.dextra.respository.comment;

import br.com.dextra.persistencia.CommentFields;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.repository.post.BaseRepository;
import br.com.dextra.utils.IndexKeys;
import br.com.dextra.utils.Utils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class CommentRepository extends BaseRepository  {

	public Entity criar(String text,String autor){
		String id = Utils.geraID();
		Key key = KeyFactory.createKey(IndexKeys.COMMENT.getKey(), id);

		String data = Utils.pegaData();

		return CommentRepository.criar(text, autor, data, id, key);
	}

	public static Entity criar(String text, String autor, String data, String id, Key key){

		Entity entidade = criarEntidade(text, autor, data, id, key);
		persist(entidade);
		DocumentRepository.criarDocumentComment(text, autor, data, id);

		return entidade;
	}

	public static Entity criarEntidade(String text, String autor, String date, String id, Key key){
		Entity entidade = new Entity(key);

		entidade.setProperty(CommentFields.ID.getField(),id);
		entidade.setProperty(CommentFields.AUTOR.getField(),autor);
		entidade.setProperty(CommentFields.DATE.getField(),date);
		entidade.setProperty(CommentFields.TEXT.getField(),new Text(text));

		return entidade;
	}

	public Iterable<Entity> buscar(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query query = new Query(IndexKeys.COMMENT.getKey());
		PreparedQuery prepared = datastore.prepare(query);

		return prepared.asIterable();
	}
}
