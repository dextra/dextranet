package br.com.dextra.dextranet.grupo;

import com.google.appengine.api.datastore.Entity;

public class UsuarioJSON {
	private String id;
	private String nome;
	private String email;
	private Boolean ativo;
	private String apelido;
	private String username;
	
	public UsuarioJSON() {
	}
	
	public UsuarioJSON(Entity entity) {
		
	}
	
	public UsuarioJSON(String id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public String getApelido() {
		return apelido;
	}

	public String getUsername() {
		return username;
	}
}
