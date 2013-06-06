package br.com.dextra.dextranet.grupos;

import com.google.appengine.api.datastore.Entity;

import br.com.dextra.dextranet.persistencia.Entidade;

public class GoogleGrupo extends Entidade {
	private String email;
	private String grupo;

	@Override
	public Entity toEntity() {
		return null;
	}

	public String getEmail() {
		return email;
	}

	public String getGrupo() {
		return grupo;
	}
}
