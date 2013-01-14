package br.com.dextra.dextranet.persistencia;

import java.util.UUID;

import br.com.dextra.dextranet.utils.DadosHelper;
import br.com.dextra.dextranet.utils.Data;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.search.Document;
import com.google.gson.JsonObject;

public abstract class Entidade {

	protected String id;
	protected String usuario;
	protected String conteudo;
	protected String dataDeCriacao;
	protected int comentarios;
	protected int likes;

	public Entidade() {
	}

	public Entidade(String usuario, String conteudo) {
		this.id = UUID.randomUUID().toString();
		this.conteudo = new DadosHelper().removeConteudoJS(conteudo);
		this.usuario = usuario;
		this.dataDeCriacao = new Data().pegaData();
		this.comentarios = 0;
		this.likes = 0;
	}

	public String getId() {
		return this.id;
	}

	@SuppressWarnings("unchecked")
	public Key getKey(Class clazz) {
		return KeyFactory.createKey(clazz.getName(), this.getId());
	}

	public abstract Entity toEntity();

	public abstract Document toDocument();

	public abstract JsonObject toJson();

}
