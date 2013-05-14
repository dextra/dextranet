package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;

// FIXME: essa classe estah sendo utilizada?

public class PostVO {
	private Post post;
	private List<Comentario> comentarios;

	public Post getPost() {
		return post;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
}
