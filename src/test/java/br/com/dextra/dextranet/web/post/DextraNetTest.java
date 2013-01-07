package br.com.dextra.dextranet.web.post;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;
import br.com.dextra.teste.utils.LoremIpsum4J;

public class DextraNetTest extends TesteFuncionalBase {

	private LoremIpsum4J loremIpsum = new LoremIpsum4J();

	PaginaDextraNet siteDextraNet = new PaginaDextraNet(driver);

	@Test
	public void testeHomePageDextraNet() {
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.acessaPaginaPrincipal();
		siteDextraNet.verificaSeTemLogoHomePage();
	}

	@Test
	public void testeCriarPost() {
		this.acessaPaginaPrincipal();

		siteDextraNet.click("span.icon_sidebar_left_novopost");
		siteDextraNet.waitForElement("input#form_input_title");

		siteDextraNet.writeInputText("input#form_input_title", loremIpsum.getWordsAsText(5));
		siteDextraNet.writeCKEditor(loremIpsum.getParagraphsAsText(1));

		siteDextraNet.click("input#form_post_submit");
		siteDextraNet.esperaCarregarPaginaInicial();

		siteDextraNet.verificaSeIncluiuPost();
	}

	@Test
	public void testebuscaPost() {
		this.acessaPaginaPrincipal();

		siteDextraNet.click("span.icon_sidebar_left_novopost");
		siteDextraNet.waitForElement("input#form_input_title");

		siteDextraNet.writeInputText("input#form_input_title", "Titulo de Teste de Busca");
		siteDextraNet.writeCKEditor("Conteudo do Teste de Busca");
		siteDextraNet.click("input#form_post_submit");
		siteDextraNet.esperaCarregarPaginaInicial();

		siteDextraNet.writeInputText("#form_search_input", "Teste");
		siteDextraNet.click("#form_search_submit");
		siteDextraNet.waitToLoad();

		siteDextraNet.verificaSeApareceuPostBuscado();
	}

	protected void acessaPaginaPrincipal() {
		siteDextraNet.navigateTo("http://localhost:8080");
		siteDextraNet.esperaCarregarPaginaInicial();
	}

}