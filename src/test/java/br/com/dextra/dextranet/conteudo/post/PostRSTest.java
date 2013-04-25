package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

public class PostRSTest extends TesteIntegracaoBase {

	private PostRS rest = new PostRS();

	private PostRepository repositorio = new PostRepository();

	@Test
	public void testaListarTodosOrdenados() {
		Post post01 = new Post("usuario");
		post01.preenche("titulo 01", "conteudo 01");

		Post post02 = new Post("usuario");
		post02.preenche("titulo 02", "conteudo 02");

		repositorio.persiste(post01);
		repositorio.persiste(post02);

		List<Post> postsOrdenados = rest.listarPostsOrdenados(2, 1);
		Assert.assertEquals(2, postsOrdenados.size());
		Assert.assertEquals(post02, postsOrdenados.get(0));
	}

}
