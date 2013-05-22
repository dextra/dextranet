package br.com.dextra.dextranet.web.conteudo.post.comentario;

import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.web.conteudo.post.PaginaNovoPost;

public class ComentarioFuncionalTest extends TesteFuncionalBase {
	private PaginaNovoPost paginaNovoPost = null;
	private String tituloPost;
	private PaginaNovoComentario paginaNovoComentario;

	public void criarComentario() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eCriouUmPost();
		depoisDeCriarPostCriaComentario();
		entaoChecaSeComentarioInserido();
	}

	private void entaoChecaSeComentarioInserido() {
		List<WebElement> htmlComentariosEncontrados = driver.findElements(By.cssSelector("ul.list_stories_comments li.clearfix div.content p.wordwrap"));
		Assert.assertTrue(htmlComentariosEncontrados.size() > 0);
	}

	private void depoisDeCriarPostCriaComentario() {
		paginaNovoComentario = paginaPrincipal.clicaEmNovoComentario(tituloPost);
		String conteudo = "Texto do comentário.";
		paginaNovoComentario.criaNovoComentario(conteudo);
	}

	public void curtirUmComentario() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eCriouUmPost();
		depoisDeCriarPostCriaComentario();
		eleCurteUmComentario();
		eTentaCurtirNovamente();
		eOComentarioFoiCurtidoApenasUmaVez();
	}

	private void eOComentarioFoiCurtidoApenasUmaVez() {
		WebElement curtidas = driver
				.findElement(By.cssSelector("#relacao_dos_comentarios .linkCurtir .numero_curtida"));
		Assert.assertEquals("1", curtidas.getText().toString());
	}

	private void eTentaCurtirNovamente() {
		eleCurteUmComentario();
	}

	private void eleCurteUmComentario() {
		paginaPrincipal.click("#relacao_dos_comentarios .linkCurtir");
		paginaPrincipal.waitingForLoading();
	}

	protected void eCriouUmPost() {
		tituloPost = "Titulo de Teste";
		String conteudo = "Texto do teste";

		paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
		paginaNovoPost.criarNovoPost(tituloPost, conteudo);
	}
}
