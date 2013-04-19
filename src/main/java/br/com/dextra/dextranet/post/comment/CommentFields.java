package br.com.dextra.dextranet.post.comment;

public enum CommentFields {

	CONTEUDO("conteudo"), USUARIO("usuario"), DATA_DE_CRIACAO("dataDeCriacao"), ID("id"), ID_REFERENCE("idReference"), COMENTARIOS(
			"comentarios"), LIKES("likes"), TREE("tree"), USER_LIKE("userLikes");

	private String value;

	CommentFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}
}
