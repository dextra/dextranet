package br.com.dextra.persistencia;

public enum PostFields {

	DATA_DE_ATUALIZACAO("dataDeAtualizacao"), TITULO("titulo"), CONTEUDO(
			"conteudo"), USUARIO("usuario"), ID("id"), COMENTARIO("comentarios"), LIKES("likes"), DATA("data");

	private String value;

	PostFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}

}
