package br.com.dextra.dextranet.web.conteudo.post.comentario;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.web.conteudo.post.PaginaPost;

public class ComentarioWebTest extends TesteFuncionalBase {
	private PaginaPost paginaNovoPost = null;
	private String tituloPost;
	private PaginaComentario paginaNovoComentario;

	@Test
	public void testComentario() {
		dadoQueUsuarioAcessaPaginaPrincipal();

		quandoEleCriaUmPost();
		eCriaUmComentarioParaPost();
		entaoComentarioExisteParaPost();

		quandoEleCurteComentario();
		entaoComentarioFoiCurtidoPeloUsuarioLogado();

		quandoEleExcluiComentario();
		entaoComentarioNaoMaisExisteParaPost();
	}

	private void entaoComentarioNaoMaisExisteParaPost() {
		paginaPrincipal.waitToLoad();
		Assert.assertFalse(paginaNovoComentario.existeComentario());
	}

	private void quandoEleExcluiComentario() {
		String botaoExcluir = "button#btn-excluir-comentario_" + paginaNovoComentario.getIdComentario();
		paginaPrincipal.click(botaoExcluir);
		paginaPrincipal.waitToLoad();
	}

	protected void quandoEleCriaUmPost() {
		tituloPost = "Titulo de Teste";
		String conteudo = "Texto do teste";

		paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
		paginaNovoPost.criarNovoPost(tituloPost, conteudo);
		paginaNovoPost.existePostPor(tituloPost, conteudo);
	}

	private void eCriaUmComentarioParaPost() {
		paginaNovoComentario = new PaginaComentario(driver);
		paginaNovoComentario.setIdPost(paginaNovoPost.getIdPost());
		String conteudo = "Texto do comentário.";
		paginaNovoComentario.criaNovoComentario(conteudo);
	}

	private void quandoEleCurteComentario() {
		String linkCurtir = "a#like_" + paginaNovoComentario.getIdComentario();
		linkCurtir += " span";
		paginaPrincipal.click(linkCurtir);
		paginaPrincipal.waitToLoad();
	}

	private void entaoComentarioExisteParaPost() {
		Assert.assertTrue(paginaNovoComentario.existeComentario());
	}

	private void entaoComentarioFoiCurtidoPeloUsuarioLogado() {
		String numeroCurtida = "a#showLikes_" + paginaNovoComentario.getIdComentario();
		numeroCurtida +=  " span";
		WebElement curtidas = driver.findElement(By.cssSelector(numeroCurtida));
		Assert.assertEquals("1", curtidas.getText());
	}

	private void dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acessaPaginaPrincipal();
	}
}
