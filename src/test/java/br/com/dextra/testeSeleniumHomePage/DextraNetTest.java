package br.com.dextra.testeSeleniumHomePage;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;

public class DextraNetTest extends TesteFuncionalBase {
	PaginaDextraNet siteDextraNet = new PaginaDextraNet(driver);

	@Test
	public void testeHomePageDextraNet() {
		siteDextraNet.navegarNaPagina("http://dextranet-desenvolvimento.appspot.com");
		siteDextraNet.verificaSeTemLogoHomePage();
	}

	@Test
	public void testeCriarEBuscarPost()
	{
		siteDextraNet.navegarNaPagina("http://localhost:8080");
		siteDextraNet.clica("span.icon-sidebar-left-novopost");
		siteDextraNet.preencheInputText("input#form-input-title", "Titulo teste");
		siteDextraNet.preencheInputText("textarea#form-input-content", "Corpo teeste");
		siteDextraNet.clica("input#form-post-submit");
		siteDextraNet.verificaSeApareceuPostIncluido();
	}


}