package br.com.dextra.dextranet.validator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class PerfilException extends RuntimeException {

	private static final long serialVersionUID = 6007540664085406808L;
	private JsonElement data;
	private JsonArray jsonArray;

	public PerfilException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

	public PerfilException(String mensagem) {
		super(mensagem);
	}

	public PerfilException(Throwable causa) {
		super(causa);
	}

	public PerfilException(JsonArray jsonArray) {
		super();
		this.jsonArray = jsonArray;
	}

	public JsonElement getData() {
		return this.data;
	}

	public void setData(JsonElement data) {
		this.data = data;
	}

	public JsonArray getJsonArray() {
		return this.jsonArray;
	}
}
