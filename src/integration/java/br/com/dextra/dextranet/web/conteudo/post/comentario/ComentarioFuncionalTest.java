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
		dadoQueUsuarioAcessaPaginaPrincipal();
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
		String botaoExcluir = "div.content .comment-data button.btn-excluir-comentario";
		paginaPrincipal.click(botaoExcluir);
		paginaPrincipal.waitingForLoading();
	}

	protected void eleCriaPost() {
		tituloPost = "Titulo de Teste";
		String conteudo = "Texto do teste";

		paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
		paginaNovoPost.criarNovoPost(tituloPost, conteudo);
	}

	private void eleCriaComentarioParaPost() {
		paginaNovoComentario = paginaPrincipal.clicaEmNovoComentario(tituloPost);
		String conteudo = "Texto do comentário.";
		paginaNovoComentario.criaNovoComentario(conteudo);
	}

	private void eleCurteComentario() {
		String linkCurtir = "div.content .comment-data .list_stories_dx .linkCurtir .icon_dx";
		paginaPrincipal.click(linkCurtir);
		paginaPrincipal.waitingForLoading();
	}

	private void eChecaSeComentarioFoiInserido() {
		Assert.assertTrue(existeComentario());
	}

	private Boolean existeComentario() {
		List<WebElement> htmlComentariosEncontrados = driver.findElements(By.cssSelector("ul.list_stories_comments li.clearfix div.content p.wordwrap"));
		return (htmlComentariosEncontrados.size() > 0);
	}

	private void eChecaSeComentarioFoiCurtido() {
		String numeroCurtida = "div.content .comment-data .list_stories_dx .showLikes .numero_curtida";
		WebElement curtidas = driver.findElement(By.cssSelector(numeroCurtida));
		Assert.assertEquals("1", curtidas.getText());
	}
}
