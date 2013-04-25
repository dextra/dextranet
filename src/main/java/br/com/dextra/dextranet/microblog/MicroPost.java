package br.com.dextra.dextranet.microblog;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;

public class MicroPost extends Entidade {

	private String texto;

	public MicroPost(String texto) {
		this.texto = texto;
	}

	public MicroPost(Entity microPostEntity) {
		this.texto = (String) microPostEntity.getProperty(MicroBlogFields.TEXTO.getField());
	}

	public String getTexto() {
		return texto;
	}

	public Entity toEntity() {
		Entity entidade = new Entity(getKey(getClass()));
		entidade.setProperty(MicroBlogFields.TEXTO.getField(), getTexto());
		return entidade;
	}

}
