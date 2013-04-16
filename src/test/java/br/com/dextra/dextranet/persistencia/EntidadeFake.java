package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class EntidadeFake extends Entidade {

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty("id", this.getId());
		return entidade;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("id", this.id);
		return json;
	}

}
