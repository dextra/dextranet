package br.com.dextra.dextranet.document;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostFields;
import br.com.dextra.dextranet.utils.IndexFacade;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class DocumentRepository extends BaseRepository {

	public <T extends Entidade> void indexar(Entidade entidade, Class<T> clazz) {
		IndexFacade.getIndex(clazz.getName()).add(entidade.toDocument());
	}

	public void alteraDocumento(Comment comment)
			throws EntityNotFoundException {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key = KeyFactory.createKey(Post.class.getName(), comment.getIdReference());
		Entity e = datastore.get(key);

		Document document = Document.newBuilder().setId(
				comment.getIdReference()).addField(
				Field.newBuilder().setName("comment"+e.getProperty(PostFields.COMENTARIO.getField()))
						.setHTML(comment.getText())).addField(
				Field.newBuilder().setName(
						PostFields.DATA_DE_ATUALIZACAO.getField())
						.setText(comment.getDataDeCriacao())).build();

		IndexFacade.getIndex(Post.class.getName()).add(document);
	}

	public void removeIndex(String indexKey, String id) {
		IndexFacade.getIndex(indexKey).remove(id);
	}
}
