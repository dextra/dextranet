package br.com.dextra.dextranet.grupo;

public class UsuarioJSON {
	private String id;
	private String nome;
	private String email;

	public UsuarioJSON() {
	}

	public UsuarioJSON(String id, String nome) {
		this.id = id;
		this.nome = nome;
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
}
