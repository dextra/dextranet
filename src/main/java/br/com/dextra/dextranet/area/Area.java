package br.com.dextra.dextranet.area;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Area extends Entidade {

	public String name;

	public Area(String name) {
		this.name = name;
	}

	public Area(Entity entity) {
		this.id = (String) entity.getProperty("id");
		this.name = (String) entity.getProperty("name");
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + name + "]";
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty("id", this.getId());
		entidade.setProperty("name", this.getName());
		return entidade;
	}

	@Override
	public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", this.id);
        json.addProperty("name", this.name);
        return json;
	}
}
