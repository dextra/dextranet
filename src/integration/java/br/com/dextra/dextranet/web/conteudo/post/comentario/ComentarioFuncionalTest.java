package br.com.dextra.dextranet.web.conteudo.post.comentario;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.web.conteudo.post.PaginaNovoPost;

public class ComentarioFuncionalTest extends TesteFuncionalBase {
	private PaginaNovoPost paginaNovoPost = null;
	private String tituloPost;
	private PaginaNovoComentario paginaNovoComentario;

	@Test
	public void testComentario() {
		paginaPrincipal.dadoQueUsuarioAcessaPaginaPrincipal();
		eleCriaPost();
		eleCriaComentarioParaPost();
		eChecaSeComentarioFoiInserido();
		eleCurteComentario();
		eChecaSeComentarioFoiCurtido();
		eleExcluiComentario();
		eChecaSeComentarioFoiExcluido();
	}

	private void eChecaSeComentarioFoiExcluido() {
		Assert.assertFalse(existeComentario());
	}

	private void eleExcluiComentario() {
		String botaoExcluir = "button#btn-excluir-comentario_" + paginaNovoComentario.getIdComentario();
		paginaPrincipal.click(botaoExcluir);
		paginaPrincipal.waitingForLoading();
	}

	protected void eleCriaPost() {
		tituloPost = "Titulo de Teste";
		String conteudo = "Texto do teste";

		paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
		paginaNovoPost.criarNovoPost(tituloPost, conteudo);
		paginaNovoPost.existePostPor(tituloPost, conteudo);
	}

	private void eleCriaComentarioParaPost() {
		paginaNovoComentario = new PaginaNovoComentario(driver);
		paginaNovoComentario.setIdPost(paginaNovoPost.getIdPost());
		String conteudo = "Texto do comentário.";
		paginaNovoComentario.criaNovoComentario(conteudo);
	}

	private void eleCurteComentario() {
		String linkCurtir = "a#like_" + paginaNovoComentario.getIdComentario();
		linkCurtir += " span";
		paginaPrincipal.click(linkCurtir);
		paginaPrincipal.waitToLoad();
	}

	private void eChecaSeComentarioFoiInserido() {
		Assert.assertTrue(existeComentario());
	}

	private Boolean existeComentario() {
		List<WebElement> htmlComentariosEncontrados = driver.findElements(By.cssSelector("ul.list_stories_comments li.clearfix"));
		if (htmlComentariosEncontrados != null && htmlComentariosEncontrados.size() > 0) {
			paginaNovoComentario.setIdComentario(htmlComentariosEncontrados.get(0).getAttribute("id"));
			return true;
		}
		return false;
	}

	private void eChecaSeComentarioFoiCurtido() {
		String numeroCurtida = "a#showLikes_" + paginaNovoComentario.getIdComentario();
		numeroCurtida +=  " span";
		WebElement curtidas = driver.findElement(By.cssSelector(numeroCurtida));
		Assert.assertEquals("1", curtidas.getText());
	}
}
