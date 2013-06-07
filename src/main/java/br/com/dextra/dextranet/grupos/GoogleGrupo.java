package br.com.dextra.dextranet.grupos;

import com.google.appengine.api.datastore.Entity;

import br.com.dextra.dextranet.persistencia.Entidade;

public class GoogleGrupo extends Entidade {
	private String email;
	private String idGrupo;

	@Override
	public Entity toEntity() {
		//TODO: implementar
		return null;
	}

	public String getEmail() {
		return email;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}
}
