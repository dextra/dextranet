package br.com.dextra.post;

import java.util.Date;

import br.com.dextra.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;

public class Post extends Entidade {

	private String titulo;

	private String conteudo;

	private Date dataDeCriacao;

	private Date dataDeAtualizacao;

	public Post(String titulo, String conteudo) {
		this.geraId();
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.dataDeCriacao = new Date();
		this.dataDeAtualizacao = this.dataDeCriacao;
	}

	public Post(Entity postEntity) {
		this.id = (String) postEntity.getProperty("id");
		this.titulo = (String) postEntity.getProperty("titulo");
		this.conteudo = (String) postEntity.getProperty("conteudo");
		this.dataDeCriacao = (Date) postEntity.getProperty("dataDeCriacao");
		this.dataDeAtualizacao = (Date) postEntity.getProperty("dataDeAtualizacao");
	}

	public String getTitulo() {
		return titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public Date getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

}
