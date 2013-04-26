package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

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

	@After
	public void removeDadosInseridos() {
		List<Post> postsCadastrados = repositorioDePosts.lista();
		for (Post post : postsCadastrados) {
			repositorioDePosts.remove(post.getId());
		}

		List<Curtida> curtidasCadastrados = repositorioDeCurtidas.lista();
		for (Curtida curtida : curtidasCadastrados) {
			repositorioDeCurtidas.remove(curtida.getId());
		}
	}

	@Test
	public void testaListarTodosOrdenados() {
		Post post01 = new Post("usuario");
		post01.preenche("titulo 01", "conteudo 01");

		Post post02 = new Post("usuario");
		post02.preenche("titulo 02", "conteudo 02");

		repositorioDePosts.persiste(post01);
		repositorioDePosts.persiste(post02);

		List<Post> postsOrdenados = rest.listarPostsOrdenados(2, 1);
		Assert.assertEquals(2, postsOrdenados.size());
		Assert.assertEquals(post02, postsOrdenados.get(0));
	}

	@Test
	public void testaCurtir() throws EntityNotFoundException {
		Post post = new Post("usuario");
		post.preenche("titulo 01", "conteudo 01");
		repositorioDePosts.persiste(post);

		rest.curtir(post.getId());

		List<Curtida> curtidas = repositorioDeCurtidas.listaPorConteudo(post.getId());
		Assert.assertEquals(1, curtidas.size());
		Assert.assertEquals(usuarioLogado, curtidas.get(0).getUsuario());
	}

	@Test
	public void testaDescurtir() throws EntityNotFoundException {
		Post post = new Post("usuario");
		post.preenche("titulo 01", "conteudo 01");
		post.curtir(usuarioLogado);
		repositorioDePosts.persiste(post);

		try {
			rest.descurtir(post.getId());
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
