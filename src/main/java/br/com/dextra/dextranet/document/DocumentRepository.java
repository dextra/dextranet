package br.com.dextra.dextranet.document;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.IndexFacade;

public class DocumentRepository extends BaseRepository {

	public <T extends Entidade> void indexar(Entidade entidade, Class<T> clazz) {
		IndexFacade.getIndex(clazz.getName()).add(entidade.toDocument());
	}

//	TODO: indexação do conteudo do documento do post pelo novo comment
	public void alteraDocumento(String id, Comment comment){
//
//		DatastoreService datastore = DatastoreServiceFactory
//				.getDatastoreService();
//		Key key = KeyFactory.createKey(Post.class.getName(), comment.getIdReference());
//		Entity e = datastore.get(key);
//
//		Document document = Document.newBuilder().setId(
//				comment.getIdReference()).addField(
//				Field.newBuilder().setName("comment"+e.getProperty(PostFields.COMENTARIO.getField()))
//						.setHTML(comment.getText())).addField(
//				Field.newBuilder().setName(
//						PostFields.DATA_DE_ATUALIZACAO.getField())
//						.setText(comment.getDataDeCriacao())).build();
//
//		IndexFacade.getIndex(Post.class.getName()).add(document);
	}

	public void removeIndex(String indexKey, String id) {
		IndexFacade.getIndex(indexKey).remove(id);
	}
}
