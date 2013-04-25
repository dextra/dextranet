package br.com.dextra.dextranet.conteudo.post;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class PostRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Post.class);
	}

	public Post obtemPorId(String id) throws EntityNotFoundException {
		Entity post = super.obtemPorId(id, Post.class);
		return new Post(post);
	}

	public List<Post> lista(EntidadeOrdenacao... criterioOrdenacao) {
		return this.lista(null, null, criterioOrdenacao);
	}

	public List<Post> lista(Integer registrosPorPagina, Integer numeroDaPagina, EntidadeOrdenacao... criterioOrdenacao) {
		List<Post> posts = new ArrayList<Post>();

		Iterable<Entity> entidades = super.lista(Post.class, registrosPorPagina, numeroDaPagina, criterioOrdenacao);
		for (Entity entidade : entidades) {
			posts.add(new Post(entidade));
		}

		return posts;
	}

}
