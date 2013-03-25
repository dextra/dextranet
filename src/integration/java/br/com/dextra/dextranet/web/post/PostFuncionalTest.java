package br.com.dextra.dextranet.web.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.web.PostTesteFuncionalUtils;

public class PostFuncionalTest extends PostTesteFuncionalUtils {

	private List<String> postsEncontrados = new ArrayList<String>();

	private int quantidadePosts = 5;
	private int vezesQueOScrollDescera = (int) Math.round(quantidadePosts / 20.0d + 0.5);

	@Test
	public void fluxoDeCriacaoPesquisaEPaginacaoDePosts() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eCriouPosts(quantidadePosts);
		quandoUsuarioDesceScrollAteFimDaPagina(vezesQueOScrollDescera);
		entaoUsuarioVisualizaOsPosts();
		eTodosOsPostsCriadosForamExibidos();
	}

	private void quandoUsuarioDesceScrollAteFimDaPagina(int quantidadeDeVezesQueDescoOScroll) {
		for (int i = 0; i <= quantidadeDeVezesQueDescoOScroll; i++) {
			paginaPrincipal.scrollAteFim();
		}
	}

	private void entaoUsuarioVisualizaOsPosts() {
		List<WebElement> htmlPostsEncontrados = driver.findElements(By.cssSelector("div.list_stories_headline h2"));
		postsEncontrados.clear();

		for (WebElement elementoPost : htmlPostsEncontrados) {
			// obtem o titulo do post para futura comparacao
			postsEncontrados.add(elementoPost.getText().toString().toUpperCase());
		}
	}

	private void eTodosOsPostsCriadosForamExibidos() {
		Collections.sort(postsInseridos);
		Collections.sort(postsEncontrados);
		Assert.assertEquals("Todos os posts inseridos deveriam ter sido visualizados.", postsInseridos,
				postsEncontrados);
	}

	@Test
	public void curtirUmPost() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eCriouPosts(1);
		eleCurteUmPost();
		eTentaCurtirNovamente();
		eOPostFoiCurtidoApenasUmaVez();
	}

	private void eOPostFoiCurtidoApenasUmaVez() {
		WebElement curtidas = driver.findElement(By.cssSelector("span.numero_curtida"));
		Assert.assertEquals("1", curtidas.getText().toString());
	}

	private void eTentaCurtirNovamente() {
		eleCurteUmPost();
	}

	private void eleCurteUmPost() {
		paginaPrincipal.click(".linkCurtir");
		paginaPrincipal.waitingForLoading();
	}

}
