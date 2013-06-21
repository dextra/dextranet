package br.com.dextra.dextranet.grupo;

import java.util.List;

public class GrupoJSON {
	private String nome;
	private String descricao;
	private List<UsuarioMembro> usuarios;

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<UsuarioMembro> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioMembro> usuarios) {
		this.usuarios = usuarios;
	}
}
