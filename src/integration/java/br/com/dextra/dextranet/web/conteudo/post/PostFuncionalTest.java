package br.com.dextra.dextranet.web.conteudo.post;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.conteudo.post.PostRepository;

public class PostFuncionalTest extends TesteFuncionalBase {

	private PaginaNovoPost paginaNovoPost = null;

	@After
	public void limpaDadosCriados() {
		this.limpaPostsInseridos(new PostRepository());
	}

	@Test
	public void criarNovoPost() {
		dadoQueUsuarioAcessaPaginaPrincipal();

		String titulo = "Titulo de Teste";
		String conteudo = "Texto do teste";
		eCriouUmPost(titulo, conteudo);
		entaoUsuarioVisualizaOPost(titulo, conteudo);
	}

	protected void eCriouUmPost(String titulo, String conteudo) {
		paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
		paginaNovoPost.criarNovoPost(titulo, conteudo);
	}


	private void entaoUsuarioVisualizaOPost(String titulo, String conteudo) {
		Assert.assertTrue(paginaPrincipal.existePost(titulo, conteudo));
	}


}
