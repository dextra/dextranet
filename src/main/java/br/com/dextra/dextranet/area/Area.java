package br.com.dextra.dextranet.area;

import java.util.UUID;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class Area {

	public String id = UUID.randomUUID().toString();
	public String name;

	public Area(String name) {
		this.name = name;
	}

	/*public Area(String id, String name) {
		this.id = id;
		this.name = name;
	}*/

	public Area(Entity entity) {
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
		return "Area [id=" + id + ", name=" + name + "]";
	}

	public Entity toEntity() {
		Entity entity = new Entity(KeyFactory.createKey(this.getClass()
				.getName(), this.id));
		entity.setProperty("id", this.getId());
		entity.setProperty("name", this.getName());
		return entity;
	}
}
