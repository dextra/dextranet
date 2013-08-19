package br.com.dextra.dextranet.grupo.servico.google;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;

public class GoogleKey extends Entidade {

	public String key;

	public GoogleKey(String key) {
		this.key = key;
	}

	public GoogleKey(Entity entity) {
		this.id = (String) entity.getProperty(GoogleKeyFields.id.name());
		this.key = (String) entity.getProperty(GoogleKeyFields.key.name());
	}

	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "Key [id=" + id + ", key=" + key + "]";
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(GoogleKeyFields.id.name(), this.id);
		entidade.setProperty(GoogleKeyFields.key.name(), this.key);
		return entidade;
	}

}
