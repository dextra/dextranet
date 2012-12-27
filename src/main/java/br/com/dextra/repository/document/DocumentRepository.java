package br.com.dextra.repository.document;

import java.util.Date;

import br.com.dextra.utils.IndexFacade;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class DocumentRepository {

	public static Document criarDocument(String titulo, String conteudo,
			String usuario, String id, Date data) {
		Document document = Document.newBuilder().setId(id).addField(
				Field.newBuilder().setName("titulo").setText(titulo)).addField(
				Field.newBuilder().setName("conteudo").setText(conteudo))
				.addField(
						Field.newBuilder().setName("usuario").setText(usuario))
				.addField(
						Field.newBuilder().setName("data").setText(
								data.toString())).addField(
						Field.newBuilder().setName("dataDeAtualizacao")
								.setText(data.toString())).addField(
						Field.newBuilder().setName("id").setText(id)).build();
		IndexFacade.getIndex("post").add(document);
		return document;
	}
}
