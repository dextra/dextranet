package br.com.dextra.dextranet.web.comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.web.PostTesteFuncionalUtils;

public class CommentFuncionalTest extends PostTesteFuncionalUtils {

	private List<String> comentariosEncontrados = new ArrayList<String>();
	private List<String> comentariosInseridos = new ArrayList<String>();

	public void fluxoDeCriacaoEListagemDeComentarios() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eCriouPosts(1);
		depoisCriaVariosComentariosParaOsPostsCriados(3);
		entaoVisualizaOsComentarios();
		eTodosOsComentariosCriadosForamExibidos();
	}

	private void eTodosOsComentariosCriadosForamExibidos() {
		Assert.assertEquals("Todos os comentários inseridos deveriam ter sido visualizados.", comentariosInseridos,
				comentariosEncontrados);
	}

	private void entaoVisualizaOsComentarios() {
		List<WebElement> htmlComentariosEncontrados = driver.findElements(By.cssSelector(".list_stories_comment_lead"));
		comentariosEncontrados.clear();

		for (WebElement elementoComentario : htmlComentariosEncontrados) {
			comentariosEncontrados.add(elementoComentario.getText().toString());
		}

		Collections.sort(comentariosEncontrados);
	}

	private void depoisCriaVariosComentariosParaOsPostsCriados(int quantidadeDeComentarios) {
		PaginaNovoComentario paginaNovoComentario = paginaPrincipal.clicaEmNovoComentario();

		for (int i = 1; i <= quantidadeDeComentarios; i++) {
			String conteudo = "Texto do comentário teste numero: " + i;

			paginaNovoComentario.criaNovoComentario(conteudo);

			// armazena o conteudo dos comentarios criados para futura
			// comparacão
			alimentarBaseDosTestesDoComentario(conteudo);
			paginaNovoComentario.waitingForLoading();
		}
	}

	private void alimentarBaseDosTestesDoComentario(String conteudo) {
		this.comentariosInseridos.add(conteudo);

		Collections.sort(comentariosInseridos);
	}

	public void curtirUmComentario() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eCriouPosts(1);
		depoisCriaVariosComentariosParaOsPostsCriados(1);
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
}
