package br.com.dextra.persistencia;

public enum CommentFields {

	TEXT("text"),AUTOR("autor"),DATE("date"),ID("id");

	private String value;

	CommentFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}
}
