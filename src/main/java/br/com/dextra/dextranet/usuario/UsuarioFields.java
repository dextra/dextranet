package br.com.dextra.dextranet.usuario;

public enum UsuarioFields {

	ID("id"), NICK_NAME("nickName"), EMAIL("email"), BLOBKEY_FOTO("blobKeyFoto");

	private String value;

	UsuarioFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}

}

