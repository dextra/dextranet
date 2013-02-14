package br.com.dextra.dextranet.unidade;

import java.util.UUID;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class Unidade {
	public String id = UUID.randomUUID().toString();
	public String name;

	public Unidade(String name) {
		this.name = name;
	}

	public Unidade(Entity entity) {
		this.id = (String) entity.getProperty("id");
		this.name = (String) entity.getProperty("name");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Unidade [id=" + id + ", name=" + name + "]";
	}

	public Entity toEntity() {
		Entity entity = new Entity(KeyFactory.createKey(this.getClass()
				.getName(), this.id));
		entity.setProperty("id", this.getId());
		entity.setProperty("name", this.getName());
		return entity;
	}
}