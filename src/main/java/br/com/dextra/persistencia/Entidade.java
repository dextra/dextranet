package br.com.dextra.persistencia;

import java.util.UUID;

import br.com.dextra.utils.JsonUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Entidade {

	protected String id;

	public String getId() {
		return id;
	}

	public Entidade geraId() {
		this.id = UUID.randomUUID().toString();
		return this;
	}

	public String toJson() {
		return JsonUtil.stringify(this);
	}

	public Key getKey() {
		return KeyFactory.createKey(this.getClass().getName(), this.getId());
	}
}
