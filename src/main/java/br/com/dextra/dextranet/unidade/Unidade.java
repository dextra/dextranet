package br.com.dextra.dextranet.unidade;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.JsonObject;

public class Unidade extends Entidade {

	public String nome;

	public Unidade(String nome) {
		this.nome = nome;
	}

	public Unidade(Entity entity) {
		this.id = (String) entity.getProperty(UnidadeFields.id.toString());
		this.nome = (String) entity.getProperty(UnidadeFields.nome.toString());
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}


	@Override
	public String toString() {
		return "Unidade [id=" + id + ", name=" + nome + "]";
	}

	public Entity toEntity() {
		Entity entity = new Entity(KeyFactory.createKey(this.getClass()
				.getName(), this.id));
		entity.setProperty(UnidadeFields.id.toString(), this.id);
		entity.setProperty(UnidadeFields.nome.toString(), this.nome);
		return entity;
	}

	@Override
	public JsonObject toJson() {
		throw new UnsupportedOperationException();
	}

}