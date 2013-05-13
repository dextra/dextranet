package br.com.dextra.dextranet.microblog;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;

public class MicroPost extends Entidade {

	private String texto;
	private Date data;

	public MicroPost(String texto) {
		this.texto = texto;
		this.data = new Date();
	}

	public MicroPost(String texto, Date data) {
		this.texto = texto;
		this.data = data;
	}

	public MicroPost(Entity microPostEntity) {
		super((String) microPostEntity.getProperty(MicroBlogFields.ID
				.getField()));
		this.texto = (String) microPostEntity.getProperty(MicroBlogFields.TEXTO
				.getField());
		this.data = (Date) microPostEntity.getProperty(MicroBlogFields.DATA
				.getField());
	}

	public String getTexto() {
		return texto;
	}

	public Date getData() {
		return data;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(getKey(MicroPost.class));
		entidade.setProperty(MicroBlogFields.TEXTO.getField(), getTexto());
		entidade.setProperty(MicroBlogFields.DATA.getField(), getData());
		entidade.setProperty(MicroBlogFields.ID.getField(), getId());
		return entidade;
	}

	@Override
	public String toString() {
		return "MicroPost [texto=" + texto + ", id=" + id + "]";
	}

}
