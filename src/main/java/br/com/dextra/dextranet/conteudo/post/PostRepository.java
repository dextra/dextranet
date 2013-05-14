package br.com.dextra.dextranet.conteudo.post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.indexacao.IndexFacade;
import br.com.dextra.dextranet.indexacao.IndexacaoRepository;
import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.search.ScoredDocument;

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

	public List<Post> buscarPosts(String q) throws EntityNotFoundException {
		// FIXME: nao devemos colocar uma limitacao nos resultados encontrados?
		Collection<ScoredDocument> result = IndexFacade.getIndex(Post.class.getName()).search(q).getResults();

		List<Post> posts = new ArrayList<Post>();
		for (ScoredDocument postDocument : result) {
			String id = postDocument.getFields(PostFields.id.name()).iterator().next().getText();
			posts.add(obtemPorId(id));
		}

		return posts;

	}

	private void buscaComentariosPost(String id, Post post) {
		List<Comentario> comentarios = new ComentarioRepository().listaPorPost(id);
		post.addComentarios(comentarios);
	}

}
