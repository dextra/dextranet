package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class ComentarioRSTest extends TesteIntegracaoBase {

	private String usuarioLogado = "dextranet";

	private ComentarioRS rest = new ComentarioRSFake();

	private PostRepository repositorioDePosts = new PostRepository();

	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();

	@After
	public void removeDadosInseridos() {
		List<Post> postsCadastrados = repositorioDePosts.lista();
		for (Post post : postsCadastrados) {
			repositorioDePosts.remove(post.getId());
		}

		List<Comentario> comentariosCadastrados = repositorioDeComentarios.lista();
		for (Comentario comentario : comentariosCadastrados) {
			repositorioDeComentarios.remove(comentario.getId());
		}

		List<Curtida> curtidasCadastradas = repositorioDeCurtidas.lista();
		for (Curtida curtida : curtidasCadastradas) {
			repositorioDeCurtidas.remove(curtida.getId());
		}
	}

	@Test
	public void testaComentario() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo", "conteudo");
		repositorioDePosts.persiste(post);

		rest.comentar(post.getId(), "novo comentario");

		post = repositorioDePosts.obtemPorId(post.getId());
		Assert.assertEquals(1, post.getQuantidadeDeComentarios());

		List<Comentario> comentarios = repositorioDeComentarios.listaPorPost(post.getId());
		Assert.assertEquals(1, comentarios.size());
		Assert.assertEquals(usuarioLogado, comentarios.get(0).getUsuario());
	}

	@Test
	public void testaCurtir() throws EntityNotFoundException {
		Comentario comentario = new Comentario("postId", "username", "conteudo");
		repositorioDeComentarios.persiste(comentario);
		Assert.assertEquals(0, comentario.getQuantidadeDeCurtidas());

		rest.curtir(comentario.getPostId(), comentario.getId());

		List<Curtida> curtidas = repositorioDeCurtidas.listaPorConteudo(comentario.getId());
		Assert.assertEquals(1, curtidas.size());
		Assert.assertEquals(usuarioLogado, curtidas.get(0).getUsuario());

		comentario = repositorioDeComentarios.obtemPorId(comentario.getId());
		Assert.assertEquals(1, comentario.getQuantidadeDeCurtidas());
		Assert.assertTrue(comentario.getUsuariosQueCurtiram().contains(usuarioLogado));
	}

	@Test
	public void testaDescurtir() throws EntityNotFoundException {
		Comentario comentario = new Comentario("postId", "username", "comentario 01");
		repositorioDeComentarios.persiste(comentario);

		rest.curtir(comentario.getPostId(), comentario.getId());
		rest.descurtir(comentario.getPostId(), comentario.getId());

		comentario = repositorioDeComentarios.obtemPorId(comentario.getId());
		Assert.assertEquals(0, comentario.getQuantidadeDeCurtidas());
		Assert.assertFalse(comentario.getUsuariosQueCurtiram().contains(usuarioLogado));

		try {
			rest.descurtir(comentario.getPostId(), comentario.getId());
			Assert.fail();
		} catch (EntidadeNaoEncontradaException e) {
			Assert.assertTrue(true);
		}

	}

	public class ComentarioRSFake extends ComentarioRS {

		@Override
		protected String obtemUsuarioLogado() {
			return usuarioLogado;
		}

	}

}
