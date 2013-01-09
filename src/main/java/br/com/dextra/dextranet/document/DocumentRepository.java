package br.com.dextra.dextranet.document;

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


	@SuppressWarnings("unchecked")
	public void indexar(Entidade entidade,Class clazz){
		IndexFacade.getIndex(clazz.getName()).add(entidade.toDocument());
	}


	public void alteraDatadoDocumento(String id, String data) throws EntityNotFoundException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(Post.class.getName(), id);
		Entity e = datastore.get(key);

		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(PostFields.TITULO.getField()).setText(e.getProperty(PostFields.TITULO.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.CONTEUDO.getField()).setText(e.getProperty(PostFields.CONTEUDO.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.USUARIO.getField()).setText(e.getProperty(PostFields.USUARIO.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.DATA.getField()).setText(e.getProperty(PostFields.DATA.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.DATA_DE_ATUALIZACAO.getField()).setText(data))
				.addField(Field.newBuilder().setName(PostFields.ID.getField()).setText(e.getProperty(PostFields.ID.getField()).toString()))
				.build();


		IndexFacade.getIndex(Post.class.getName()).add(document);
	}


	public void removeIndex(String indexKey, String id) {
		IndexFacade.getIndex(indexKey).remove(id);
	}
}
