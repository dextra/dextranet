package br.com.dextra.repository.comment;

import br.com.dextra.comment.CommentFields;
import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.utils.IndexKeys;
import br.com.dextra.utils.Data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class CommentRepository extends BaseRepository  {

	public void criar(String text,String autor,String PostId){
		Entity entidade = criarUmaEntidade(text, autor);
		persist(entidade);
		persistDocument(entidade,PostId);
	}

	private Entity criarUmaEntidade(String text, String autor){
		String id = Data.geraID();
		Key key = KeyFactory.createKey(IndexKeys.COMMENT.getKey(), id);

		Entity entidade = new Entity(key);
		entidade.setProperty(CommentFields.ID.getField(),id);
		entidade.setProperty(CommentFields.AUTOR.getField(),autor);
		entidade.setProperty(CommentFields.DATE.getField(),new Data().pegaData());
		entidade.setProperty(CommentFields.TEXT.getField(),new Text(text));

		return entidade;
	}

	private void persistDocument(Entity entidade, String PostID) {
		Text text = (Text) entidade.getProperty(CommentFields.TEXT.getField());
		String id = (String) entidade.getProperty(CommentFields.ID.getField());

		DocumentRepository respositorioDeDocumentos = new DocumentRepository();
		respositorioDeDocumentos.crieUmDocumentoDoComentario(text,id,PostID);
	}

	public Iterable<Entity> buscar(){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query query = new Query(IndexKeys.COMMENT.getKey());
		PreparedQuery prepared = datastore.prepare(query);

		return prepared.asIterable();
	}
}
