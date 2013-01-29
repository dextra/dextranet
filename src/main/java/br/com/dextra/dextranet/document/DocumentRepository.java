package br.com.dextra.dextranet.document;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.curtida.Curtida;
import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.persistencia.ConteudoIndexavel;
import br.com.dextra.dextranet.utils.IndexFacade;

public class DocumentRepository extends BaseRepository {

	public void indexar(ConteudoIndexavel conteudo) {
		IndexFacade.getIndex(conteudo.getClass().getName()).add(conteudo.toDocument());
	}

	public void alteraDocumento(Curtida curtida) {

//		Document document = Document.newBuilder().setId(
//				id).addField(
//				Field.newBuilder().setName(
//						PostFields.DATA_DE_ATUALIZACAO.getField()).setText(curtida.getData())).build();
//
//		IndexFacade.getIndex(Post.class.getName()).add(document);
	}

//	TODO: indexação do conteudo do documento do post pelo novo comment
	public void alteraDocumento(Comment comment){
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
