package br.com.dextra.dextranet.usuario;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.MD5;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Usuario extends Entidade {

	private static final String DEFAULT_DOMAIN = "@dextra-sw.com";

	private String username;

	private String md5;

	private String nome;

	private String apelido;

	private String area;

	private String unidade;

	private String ramal;

	private String telefoneResidencial;

	private String telefoneCelular;

	public Usuario(String username) {
		this.username = username.trim();
		this.md5 = MD5.hash(this.getEmail());
	}

	public String getEmail() {
		return this.username + DEFAULT_DOMAIN;
	}

	public String getUsername() {
		return username;
	}

	public String getMD5() {
		return md5;
	}

	public String getNome() {
		return nome;
	}

	public String getApelido() {
		return apelido;
	}

	public String getArea() {
		return area;
	}

	public String getUnidade() {
		return unidade;
	}

	public String getRamal() {
		return ramal;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	@Override
	public Entity toEntity() {
		throw new UnsupportedOperationException();
	}

	@Override
	public JsonObject toJson() {
		throw new UnsupportedOperationException();
	}

}
