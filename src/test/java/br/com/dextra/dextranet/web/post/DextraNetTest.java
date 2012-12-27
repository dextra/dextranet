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
	public void testeCriarEListarPost() {
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.click("span.icon_sidebar_left_novopost");
		siteDextraNet.writeInputText("input#form_input_title",
		"KaiqueCineminha");
		siteDextraNet.writeCKEditor("KaiqueVaiNoCineminha");
		siteDextraNet.click("input#form_post_submit");
		siteDextraNet.verificaSeIncluiuPost();

	}

	public void testebuscaPost()
	{
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.writeInputText("#form_search_input",
				"KaiqueCin");
		siteDextraNet.click("#form_search_submit");
		siteDextraNet.verificaSeApareceuPostBuscado();
	}

}