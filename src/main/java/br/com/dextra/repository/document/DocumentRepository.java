package br.com.dextra.repository.document;

import org.jsoup.Jsoup;

import br.com.dextra.persistencia.CommentFields;
import br.com.dextra.persistencia.PostFields;
import br.com.dextra.repository.post.BaseRepository;
import br.com.dextra.utils.IndexFacade;
import br.com.dextra.utils.IndexKeys;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class DocumentRepository extends BaseRepository {

	public Document criarDocumentPost(String titulo, Text conteudo,String usuario, String id, String data) {

		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(PostFields.TITULO.getField()).setText(titulo))
				.addField(Field.newBuilder().setName(PostFields.CONTEUDO.getField()).setText(Jsoup.parse(conteudo.toString()).text()))
				.addField(Field.newBuilder().setName(PostFields.USUARIO.getField()).setText(usuario))
				.addField(Field.newBuilder().setName(PostFields.DATA.getField()).setText(data.toString()))
				.addField(Field.newBuilder().setName(PostFields.DATA_DE_ATUALIZACAO.getField()).setText(data.toString()))
				.addField(Field.newBuilder().setName(PostFields.ID.getField()).setText(id))
				.build();
		// FIXME aqui ao invés do indice post, deve ser o indice de document
		// porque vai ter que listar nao somente posts
		IndexFacade.getIndex(IndexKeys.POST.getKey()).add(document);
		return document;
	}

	public void alteraDatadoDocumento(String id, String data) throws EntityNotFoundException {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		Entity e = datastore.get(key);

		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(PostFields.TITULO.getField()).setText(e.getProperty(PostFields.TITULO.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.CONTEUDO.getField()).setText(e.getProperty(PostFields.CONTEUDO.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.USUARIO.getField()).setText(e.getProperty(PostFields.USUARIO.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.DATA.getField()).setText(e.getProperty(PostFields.DATA.getField()).toString()))
				.addField(Field.newBuilder().setName(PostFields.DATA_DE_ATUALIZACAO.getField()).setText(data))
				.addField(Field.newBuilder().setName(PostFields.ID.getField()).setText(e.getProperty(PostFields.ID.getField()).toString()))
				.build();

		System.out.println("O DOC AQUI Ó"+document);

		IndexFacade.getIndex(IndexKeys.POST.getKey()).add(document);
	}

	public Document crieUmDocumentoDoComentario(Text text, String id, String idReferencia){

		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(CommentFields.TEXT.getField()).setText(text.toString()))
				.addField(Field.newBuilder().setName(CommentFields.POSTID.getField()).setText(idReferencia))
				.addField(Field.newBuilder().setName(CommentFields.ID.getField()).setText(id))
				.build();

		IndexFacade.getIndex(IndexKeys.COMMENT.getKey()).add(document);
		return document;
	}

	public void removeIndex(String indexKey, String id) {
		IndexFacade.getIndex(indexKey).remove(id);
	}
}
