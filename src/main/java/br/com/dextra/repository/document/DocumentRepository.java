package br.com.dextra.repository.document;

import java.util.Date;

import br.com.dextra.persistencia.PostFields;
import br.com.dextra.utils.IndexFacade;
import br.com.dextra.utils.IndexKeys;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class DocumentRepository {

	public static Document criarDocumentPost(String titulo, String conteudo,
			String usuario, String id, Date data) {
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
		IndexFacade.getIndex(IndexKeys.POST.getKey()).add(document);
		return document;
	}
}
