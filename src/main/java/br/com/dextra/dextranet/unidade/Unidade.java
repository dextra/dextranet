package br.com.dextra.dextranet.unidade;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

public class Unidade extends Entidade {

	public String nome;

	public Unidade(String nome) {
		this.nome = new ConteudoHTML(nome).removeJavaScript();
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
		Entity entity = new Entity(KeyFactory.createKey(this.getClass().getName(), this.id));
		entity.setProperty(UnidadeFields.id.toString(), this.id);
		entity.setProperty(UnidadeFields.nome.toString(), this.nome);
		return entity;
	}

}