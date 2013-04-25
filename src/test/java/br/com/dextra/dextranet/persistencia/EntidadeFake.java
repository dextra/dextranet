package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.datastore.Entity;

public class EntidadeFake extends Entidade {

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty("id", this.getId());
		return entidade;
	}

}
