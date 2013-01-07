package br.com.dextra.dextranet.post;

import br.com.dextra.dextranet.entidade.Entidade;
import br.com.dextra.persistencia.PostFields;
import com.google.appengine.api.datastore.Entity;


public class Post extends Entidade {

	private String titulo;

	private int comentario;

	private int likes;

	private String dataDeAtualizacao;

	public Post(String titulo, String conteudo,String usuario) {
		super(usuario, conteudo);
		this.titulo = titulo;
		this.dataDeAtualizacao = this.dataDeCriacao;
		this.comentario = 0;
		this.likes=0;
	}

	public Post(Entity postEntity) {
		super((String) postEntity.getProperty(PostFields.USUARIO.getField()),
			  (String) postEntity.getProperty(PostFields.CONTEUDO.getField()));
		this.id = (String) postEntity.getProperty(PostFields.ID.getField());
		this.titulo = (String) postEntity.getProperty(PostFields.TITULO.getField());
		this.dataDeCriacao = (String) postEntity.getProperty(PostFields.DATA.getField());
		this.dataDeAtualizacao = (String) postEntity.getProperty(PostFields.DATA_DE_ATUALIZACAO.getField());
		this.comentario = (Integer) postEntity.getProperty(PostFields.COMENTARIO.getField());
		this.likes = (Integer) postEntity.getProperty(PostFields.LIKES.getField());

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

	public String getUsuario() {
		return usuario;
	}

	public int getComentarios() {
		return comentario;
	}

	public int getLikes() {
		return likes;
	}



}
