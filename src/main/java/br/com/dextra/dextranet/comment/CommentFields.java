package br.com.dextra.dextranet.comment;

public enum CommentFields {

	CONTEUDO("coteudo"), USUARIO("usuario"), DATA_DE_CRIACAO("dataDeCriacao"), ID(
			"id"), ID_REFERENCE("idReference"), COMENTARIOS("comentarios"),LIKES("likes"), TREE("tree") ;

	private String value;

	CommentFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}
}
