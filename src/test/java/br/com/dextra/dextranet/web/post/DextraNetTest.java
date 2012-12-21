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
	public void testeCriarEBuscarPost() {
		siteDextraNet.navigateTo("http://localhost:8080/");
		siteDextraNet.click("span.icon-sidebar-left-novopost");
		siteDextraNet.writeInputText("input#form-input-title",
				"Titulo teste");
		siteDextraNet.writeTextArea("#form-input-content",
				"Corpo teeste");
		siteDextraNet.click("input#form-post-submit");
		siteDextraNet.verificaSeApareceuPostIncluido();
	}

}