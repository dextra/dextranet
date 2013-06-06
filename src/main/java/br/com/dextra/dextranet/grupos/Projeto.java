package br.com.dextra.dextranet.grupos;

import com.google.appengine.api.datastore.Entity;

import br.com.dextra.dextranet.persistencia.Entidade;

public class Projeto extends Entidade {
	private String nome;
	private String gitHub;

	@Override
	public Entity toEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNome() {
		return nome;
	}

	public String getGitHub() {
		return gitHub;
	}
}
