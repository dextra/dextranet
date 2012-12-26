package br.com.dextra.dextranet.web.post;

import org.junit.Ignore;
import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;

public class DextraNetTest extends TesteFuncionalBase {
	PaginaDextraNet siteDextraNet = new PaginaDextraNet(driver);

	@Test
	public void testeHomePageDextraNet() {
		siteDextraNet.navigateTo("http://localhost:8080");
		siteDextraNet.verificaSeTemLogoHomePage();
	}


	public void testeCriarEListarPost() {
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.click("span.icon-sidebar-left-novopost");
		siteDextraNet.writeInputText("input#form-input-title",
				"KaiqueCineminha");
		siteDextraNet.writeTextArea("#form-input-content",
				"KaiqueVaiNoCineminha");
		siteDextraNet.click("input#form-post-submit");
		siteDextraNet.verificaSeApareceuPostIncluido();
	}

	public void testebuscaPost()
	{
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.writeInputText("#form-search-input",
				"KaiqueCin");
		siteDextraNet.click("#form-search-submit");
		siteDextraNet.verificaSeApareceuPostBuscado();
	}

}