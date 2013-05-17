package br.com.dextra.dextranet.conteudo.post;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.indexacao.IndexacaoRepository;
import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class PostRepository extends EntidadeRepository {

	private IndexacaoRepository indexacao = new IndexacaoRepository();

	public Post persiste(Post post) {
		Post postPersistido = super.persiste(post);
		indexacao.indexar(postPersistido);

		return postPersistido;
	}

	public void remove(String id) {
		super.remove(id, Post.class);
		indexacao.removeIndexacao(Post.class.getName(), id);
	}

	public Post obtemPorId(String id) throws EntityNotFoundException {
		Entity postEnt = super.obtemPorId(id, Post.class);
		Post post = new Post(postEnt);
		buscaComentariosPost(id, post);
		return post;
	}

	public List<Post> lista(EntidadeOrdenacao... criterioOrdenacao) {
		return this.lista(null, null, criterioOrdenacao);
	}

	public List<Post> lista(Integer registrosPorPagina, Integer numeroDaPagina, EntidadeOrdenacao... criterioOrdenacao) {
		List<Post> posts = new ArrayList<Post>();

		Iterable<Entity> entidades = super.lista(Post.class, registrosPorPagina, numeroDaPagina, criterioOrdenacao);
		for (Entity entidade : entidades) {
			Post post = new Post(entidade);
			buscaComentariosPost(post.getId(), post);
			posts.add(post);
		}

		return posts;
	}

	private void buscaComentariosPost(String id, Post post) {
		List<Comentario> comentarios = new ComentarioRepository().listaPorPost(id);
		post.addComentarios(comentarios);
	}

}