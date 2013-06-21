package br.com.dextra.dextranet.grupo;

public class UsuarioMembro {
	private String id;
	private String nome;

	public UsuarioMembro() {

	}

	public UsuarioMembro(String id, String nome) {
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

	@Override
	public String toString() {
		return "id: " + id + ", nome:" + nome;
	}
}
