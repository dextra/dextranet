package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class PostRSTest extends TesteIntegracaoBase {

	private String usuarioLogado = "dextranet";

	private PostRS rest = new PostRSFake();

	private PostRepository repositorioDePosts = new PostRepository();
	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();
	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	@After
	public void removeDadosInseridos() {
		this.limpaPostsInseridos(repositorioDePosts);
		this.limpaCurtidasInseridas(repositorioDeCurtidas);
		this.limpaComentariosInseridos(repositorioDeComentarios);
	}

	@Test
	public void testaListarTodosOrdenados() {
		Post post01 = new Post("usuario", "titulo 01", "conteudo 01");
		Post post02 = new Post("usuario", "titulo 02", "conteudo 02");

		repositorioDePosts.persiste(post01);
		repositorioDePosts.persiste(post02);

		List<Post> postsOrdenados = rest.listarPostsOrdenados(2, 1);
		Assert.assertEquals(2, postsOrdenados.size());
		Assert.assertEquals(post02, postsOrdenados.get(0));
	}

	@Test
	public void testaCurtir() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo 01", "conteudo 01");
		repositorioDePosts.persiste(post);
		Assert.assertEquals(0, post.getQuantidadeDeCurtidas());

		rest.curtir(post.getId());

		List<Curtida> curtidas = repositorioDeCurtidas.listaPorConteudo(post.getId());
		Assert.assertEquals(1, curtidas.size());
		Assert.assertEquals(usuarioLogado, curtidas.get(0).getUsuario());

		post = repositorioDePosts.obtemPorId(post.getId());
		Assert.assertEquals(1, post.getQuantidadeDeCurtidas());
		Assert.assertTrue(post.getUsuariosQueCurtiram().contains(usuarioLogado));
	}

	@Test
	public void testaDescurtir() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo 01", "conteudo 01");
		repositorioDePosts.persiste(post);

		rest.curtir(post.getId());

		rest.descurtir(post.getId());
		post = repositorioDePosts.obtemPorId(post.getId());
		Assert.assertEquals(0, post.getQuantidadeDeCurtidas());
		Assert.assertFalse(post.getUsuariosQueCurtiram().contains(usuarioLogado));

		try {
			rest.descurtir(post.getId());
			Assert.fail();
		} catch (EntidadeNaoEncontradaException e) {
			Assert.assertTrue(true);
		}

	}

	@Test(expected = EntityNotFoundException.class)
	public void testaRemover() throws EntityNotFoundException {
		Post post = new Post("dextranet", "titulo 01", "conteudo 01");
		String idPost = repositorioDePosts.persiste(post).getId();

		Response response = rest.deletar(post.getId());
		Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());

		repositorioDePosts.obtemPorId(idPost);
	}

	@Test
	public void testaRemoverComOutroUsuario() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo 01", "conteudo 01");
		String idPost = repositorioDePosts.persiste(post).getId();

		Response response = rest.deletar(post.getId());
		Assert.assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());

		post = repositorioDePosts.obtemPorId(idPost);
		Assert.assertNotNull(post);
	}

	@Test
	public void testaComentario() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo", "conteudo");
		repositorioDePosts.persiste(post);
		rest.comentar(post.getId(), "novo comentario");
		post = repositorioDePosts.obtemPorId(post.getId());
		List<Comentario> comentarios = repositorioDeComentarios.listaPorPost(post.getId());
		Assert.assertEquals(1, comentarios.size());
		Assert.assertEquals(usuarioLogado, comentarios.get(0).getUsuario());
	}

	@Test
	public void testaCurtirComentario() throws EntityNotFoundException {
		Comentario comentario = new Comentario("postId", "username", "conteudo");
		repositorioDeComentarios.persiste(comentario);
		Assert.assertEquals(0, comentario.getQuantidadeDeCurtidas());

		rest.curtirComentario(comentario.getPostId(), comentario.getId());

		List<Curtida> curtidas = repositorioDeCurtidas.listaPorConteudo(comentario.getId());
		Assert.assertEquals(1, curtidas.size());
		Assert.assertEquals(usuarioLogado, curtidas.get(0).getUsuario());

		comentario = repositorioDeComentarios.obtemPorId(comentario.getId());
		Assert.assertEquals(1, comentario.getQuantidadeDeCurtidas());
		Assert.assertTrue(comentario.getUsuariosQueCurtiram().contains(usuarioLogado));
	}

	@Test
	public void testaDescurtirComentario() throws EntityNotFoundException {
		Comentario comentario = new Comentario("postId", "username", "comentario 01");
		repositorioDeComentarios.persiste(comentario);

		rest.curtirComentario(comentario.getPostId(), comentario.getId());
		rest.descurtirComentario(comentario.getPostId(), comentario.getId());

		comentario = repositorioDeComentarios.obtemPorId(comentario.getId());
		Assert.assertEquals(0, comentario.getQuantidadeDeCurtidas());
		Assert.assertFalse(comentario.getUsuariosQueCurtiram().contains(usuarioLogado));

		try {
			rest.descurtirComentario(comentario.getPostId(), comentario.getId());
			Assert.fail();
		} catch (EntidadeNaoEncontradaException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaBuscaComentario() {
		Post post = new Post(usuarioLogado, "titulo", "conteudo");
		repositorioDePosts.persiste(post);
		Comentario comentario = new Comentario(post.getId(), usuarioLogado, "comentario01");
		repositorioDeComentarios.persiste(comentario);

	}

	public class PostRSFake extends PostRS {
		@Override
		protected String obtemUsuarioLogado() {
			return usuarioLogado;
		}

	}

	@Test(expected = EntityNotFoundException.class)
	public void testaRemoverComentario() throws EntityNotFoundException {
		Post post = new Post("dextranet", "titulo 01", "conteudo 01");
		String postId = repositorioDePosts.persiste(post).getId();

		post = repositorioDePosts.obtemPorId(postId);
		Assert.assertNotNull(post);

		Comentario comentario = new Comentario(postId, "dextranet", "comentario 01");
		String comentarioId = repositorioDeComentarios.persiste(comentario).getId();

		Response responseComentario = rest.deletarComentario(comentarioId);
		Assert.assertEquals(Status.OK.getStatusCode(), responseComentario.getStatus());

		repositorioDeComentarios.obtemPorId(comentario.getId());
	}

	@Test
	public void testaRemoverComentarioComOutroUsuario() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo 01", "conteudo 01");
		String postId = repositorioDePosts.persiste(post).getId();

		post = repositorioDePosts.obtemPorId(postId);
		Assert.assertNotNull(post);

		Comentario comentario = new Comentario(postId, "usuario", "comentario 01");
		String comentarioId = repositorioDeComentarios.persiste(comentario).getId();

		Response responseComentario = rest.deletarComentario(comentarioId);
		Assert.assertEquals(Status.FORBIDDEN.getStatusCode(), responseComentario.getStatus());

		comentario = repositorioDeComentarios.obtemPorId(comentarioId);
		Assert.assertNotNull(comentario);
	}
}
