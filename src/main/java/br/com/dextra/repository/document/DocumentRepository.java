package br.com.dextra.repository.document;

import java.util.Date;

import br.com.dextra.persistencia.PostFields;
import br.com.dextra.repository.post.BaseRepository;
import br.com.dextra.repository.post.PostRepository;
import br.com.dextra.utils.IndexFacade;
import br.com.dextra.utils.IndexKeys;
import br.com.dextra.utils.Utils;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class DocumentRepository extends BaseRepository {

	public static Document criarDocumentPost(String titulo, String conteudo,
			String usuario, String id, String data) {
		Document document = Document.newBuilder().setId(id).addField(
				Field.newBuilder().setName(PostFields.TITULO.getField())
						.setText(titulo)).addField(
				Field.newBuilder().setName(PostFields.CONTEUDO.getField())
						.setText(conteudo)).addField(
				Field.newBuilder().setName(PostFields.USUARIO.getField())
						.setText(usuario)).addField(
				Field.newBuilder().setName(PostFields.DATA.getField()).setText(
						data.toString())).addField(
				Field.newBuilder().setName(
						PostFields.DATA_DE_ATUALIZACAO.getField()).setText(
						data.toString())).addField(
				Field.newBuilder().setName(PostFields.ID.getField())
						.setText(id)).build();
		// FIXME aqui ao invés do indice post, deve ser o indice de document
		// porque vai ter que listar nao somente posts
		IndexFacade.getIndex(IndexKeys.POST.getKey()).add(document);
		return document;
	}

	public static void alteraDatadoDocumento(String id, String data) {
		Document doc = Document.newBuilder().setId(id).addField(
				Field.newBuilder().setName(
						PostFields.DATA_DE_ATUALIZACAO.getField()).setText(
						data)).build();
		IndexFacade.getIndex(IndexKeys.POST.getKey()).add(doc);
	}
}
