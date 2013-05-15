package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

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
	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();
	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();

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

	@Test
	public void testaBuscaPosts() throws EntityNotFoundException {
		Post post01 = new Post(usuarioLogado, "post1", "esse eh um post de teste");
		Post post02 = new Post("usuario", "post2", "esse eh um post de teste");
		repositorioDePosts.persiste(post01);
		repositorioDePosts.persiste(post02);

		List<Post> busca = repositorioDePosts.buscarPosts(PostFields.titulo.name() + ": post1");
		Assert.assertEquals(1, busca.size());

		busca = repositorioDePosts.buscarPosts(PostFields.conteudo.name() + ": esse eh um post de teste");
		Assert.assertEquals(2, busca.size());

		busca = repositorioDePosts.buscarPosts(PostFields.usuario.name() + ": usuario");
		Assert.assertEquals(1, busca.size());

		busca = repositorioDePosts.buscarPosts("esse eh um post de teste");
		Assert.assertEquals(2, busca.size());

	}

	@Test
	public void testaRemover() throws EntityNotFoundException {
		Post post = new Post("usuario", "titulo 01", "conteudo 01");
		repositorioDePosts.persiste(post);

		try {
			rest.deletar(post.getId());
			Assert.fail();
		} catch (UsarioNaoPodeRemoverException e) {
			Assert.assertTrue(true);
		}

		Post post2 = new Post("dextranet", "titulo 02", "conteudo 02");
		repositorioDePosts.persiste(post2);

		try {
			rest.deletar(post2.getId());
		} catch (UsarioNaoPodeRemoverException e) {
			Assert.fail();
		}
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

	public class PostRSFake extends PostRS {
		@Override
		protected String obtemUsuarioLogado() {
			return usuarioLogado;
		}

	}
}
