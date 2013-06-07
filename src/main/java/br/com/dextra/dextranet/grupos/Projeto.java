package br.com.dextra.dextranet.grupos;

import com.google.appengine.api.datastore.Entity;

import br.com.dextra.dextranet.persistencia.Entidade;

public class Projeto extends Entidade {
	private String nome;
	private String gitHub;
	private String idGrupo;

	@Override
	public Entity toEntity() {
		// TODO IMPLEMENTAR
		return null;
	}

	public String getNome() {
		return nome;
	}

	public String getGitHub() {
		return gitHub;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
}
