package br.com.dextra.teste.post;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.post.Post;
import br.com.dextra.teste.TesteFuncionalBase;

public class TesteCriaPost extends TesteFuncionalBase {
	Post post = new Post();

	@Test
	public void testaNovoPost() {
		String titulo = "#1 Post", conteudo = "Primeiro Post!", usuario = "Pina";
		post.criaNovoPost(titulo, conteudo, usuario);
		Assert.assertEquals(true, post.pegaDadosCorretos(titulo, conteudo, usuario));
	}

}
