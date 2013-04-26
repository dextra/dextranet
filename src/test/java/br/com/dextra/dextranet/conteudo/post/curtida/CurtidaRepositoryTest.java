package br.com.dextra.dextranet.conteudo.post.curtida;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class CurtidaRepositoryTest extends TesteIntegracaoBase {

	private PostRepository repositorioDePosts = new PostRepository();

	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();

	@Test
	public void testaRemocao() {
		Post novoPost = new Post("usuario", "titulo", "conteudo");
		Curtida curtida = novoPost.curtir("dextranet");

		Curtida curtidaCriada = repositorioDeCurtidas.persiste(curtida);

		String idDaCurtidaCriada = curtidaCriada.getId();
		repositorioDeCurtidas.remove(idDaCurtidaCriada);

		try {
			repositorioDeCurtidas.obtemPorId(idDaCurtidaCriada);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaRemocaoPorConteudoId() {
		Post novoPost = new Post("usuario", "titulo", "conteudo");
		Curtida curtida = novoPost.curtir("dextranet");

		Curtida curtidaCriada = repositorioDeCurtidas.persiste(curtida);

		String idDaCurtidaCriada = curtidaCriada.getId();
		repositorioDeCurtidas.remove(curtida.getConteudoId(), curtida.getUsuario());

		try {
			repositorioDeCurtidas.obtemPorId(idDaCurtidaCriada);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaExcecaoObtemPorConteudoEUsusuario() {
		try {
			repositorioDeCurtidas.obtemPorConteudoEUsusuario("conteudoId", "usuario");
			Assert.fail();
		} catch (EntidadeNaoEncontradaException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaListaPorConteudo() {
		Post post01 = new Post("usuario", "titulo 01", "conteudo 01");
		Curtida curtida01 = post01.curtir("dextranet");
		Curtida curtida02 = post01.curtir("outro-usuario");

		Post post02 = new Post("dextranet", "titulo 02", "conteudo 02");
		Curtida curtida03 = post02.curtir("usuario");
		Curtida curtida04 = post02.curtir("outro-usuario");

		repositorioDePosts.persiste(post01);
		repositorioDeCurtidas.persiste(curtida01);
		repositorioDeCurtidas.persiste(curtida02);

		repositorioDePosts.persiste(post02);
		repositorioDeCurtidas.persiste(curtida03);
		repositorioDeCurtidas.persiste(curtida04);

		List<Curtida> curtidasDoPost = repositorioDeCurtidas.listaPorConteudo(post01.getId());
		Assert.assertEquals(2, curtidasDoPost.size());
		Assert.assertEquals(curtida02, curtidasDoPost.get(0));
		Assert.assertEquals(curtida01, curtidasDoPost.get(1));
	}

}
