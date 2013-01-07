package br.com.dextra.dextranet.post;

import br.com.dextra.dextranet.entidade.Entidade;
import br.com.dextra.utils.Data;

import com.google.appengine.api.datastore.Entity;

public class Post extends Entidade {

	private String titulo;

	private String conteudo;

	private String dataDeCriacao;

	private String dataDeAtualizacao;

	public Post(String titulo, String conteudo) {
		this.geraId();
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.dataDeCriacao = new Data().pegaData();
		this.dataDeAtualizacao = this.dataDeCriacao;
	}

	public Post(Entity postEntity) {
		this.id = (String) postEntity.getProperty("id");
		this.titulo = (String) postEntity.getProperty("titulo");
		this.conteudo = (String) postEntity.getProperty("conteudo");
		this.dataDeCriacao = (String) postEntity.getProperty("dataDeCriacao");
		this.dataDeAtualizacao = (String) postEntity.getProperty("dataDeAtualizacao");
	}

	public String getTitulo() {
		return titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public String getDataDeCriacao() {
		return dataDeCriacao;
	}

	public String getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

}
