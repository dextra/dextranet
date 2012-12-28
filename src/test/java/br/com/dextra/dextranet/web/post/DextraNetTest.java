package br.com.dextra.dextranet.web.post;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;



public class DextraNetTest extends TesteFuncionalBase {
	PaginaDextraNet siteDextraNet = new PaginaDextraNet(driver);

	@Test
	public void testeHomePageDextraNet() {
		siteDextraNet.navigateTo("http://localhost:8080");
		siteDextraNet.verificaSeTemLogoHomePage();
	}

	@Test
	public void testeCriarPost() {
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.click("span.icon_sidebar_left_novopost");
		siteDextraNet.writeInputText("input#form_input_title",
		"Kaique cineminha");
		siteDextraNet.writeCKEditor("Kaique vai no cineminha");
		siteDextraNet.click("input#form_post_submit");
		siteDextraNet.verificaSeIncluiuPost();
	}

	@Test
	public void testebuscaPost()
	{
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.click("span.icon_sidebar_left_novopost");
		siteDextraNet.writeInputText("input#form_input_title",
		"OutroPost");
		siteDextraNet.writeCKEditor("Texto do outro post :B");
		siteDextraNet.click("input#form_post_submit");
		siteDextraNet.writeInputText("#form_search_input",
				"Kaique");
		siteDextraNet.click("#form_search_submit");
		siteDextraNet.verificaSeApareceuPostBuscado();
	}

}