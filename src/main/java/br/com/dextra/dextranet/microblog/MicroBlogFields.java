package br.com.dextra.dextranet.microblog;

public enum MicroBlogFields {

	ID("id"), TEXTO("texto"), DATA("data"), USUARIO("usuario"), USUARIOMD5("usuarioMD5");

	private String value;

	MicroBlogFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}
}
