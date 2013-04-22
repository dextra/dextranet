package br.com.dextra.dextranet.microblog;

public enum MicroBlogFields {

	TEXTO("texto");

	private String value;

	MicroBlogFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}
}
