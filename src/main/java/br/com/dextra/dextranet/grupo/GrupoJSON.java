package br.com.dextra.dextranet.grupo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GrupoJSON {
	private String id;
	private String nome;
	private String descricao;
	private List<UsuarioJSON> usuarios;

	public GrupoJSON() {
	}

	public GrupoJSON(String id, String nome, String descricao, List<UsuarioJSON> usuarios) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.usuarios = usuarios;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<UsuarioJSON> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioJSON> usuarios) {
		this.usuarios = usuarios;
	}
}
