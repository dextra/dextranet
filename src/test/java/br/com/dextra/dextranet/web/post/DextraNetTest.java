package br.com.dextra.dextranet.web.post;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;

public class DextraNetTest extends TesteFuncionalBase {

	PaginaDextraNet siteDextraNet = new PaginaDextraNet(driver);

	@Test
	public void testeHomePageDextraNet() {
		this.acessaPaginaPrincipal();
		siteDextraNet.verificaSeTemLogoHomePage();
	}

	@Test
	public void testeCriarPost() {
		try {
			this.acessaPaginaPrincipal();

			siteDextraNet.click("span.icon_sidebar_left_novopost");
			siteDextraNet.waitForElement("input#form_input_title");

			siteDextraNet.writeInputText("input#form_input_title", "Titulo de Teste");
			siteDextraNet.writeCKEditor("Comentario de Teste");

			siteDextraNet.click("input#form_post_submit");
			siteDextraNet.esperaCarregarPaginaInicial();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		siteDextraNet.verificaSeIncluiuPost();
	}

	@Test
	public void testebuscaPost() {
		try{
		this.acessaPaginaPrincipal();

		siteDextraNet.click("span.icon_sidebar_left_novopost");
		siteDextraNet.waitForElement("input#form_input_title");

		siteDextraNet.writeInputText("input#form_input_title", "Titulo de Teste de Busca");
		siteDextraNet.writeCKEditor("Conteudo do Teste de Busca");
		siteDextraNet.click("input#form_post_submit");
		siteDextraNet.esperaCarregarPaginaInicial();

		Thread.sleep(1000);
		siteDextraNet.writeInputText("#form_search_input", "Busca");
		siteDextraNet.click("#form_search_submit");
		siteDextraNet.waitToLoad();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		siteDextraNet.verificaSeApareceuPostBuscado();
	}

	protected void acessaPaginaPrincipal() {
		siteDextraNet.navigateTo("http://localhost:8080");
		siteDextraNet.esperaCarregarPaginaInicial();
	}

}