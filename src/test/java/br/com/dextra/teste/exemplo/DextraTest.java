package br.com.dextra.teste.exemplo;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;

public class DextraTest extends TesteFuncionalBase {

	PaginaDextra siteDextra = new PaginaDextra(driver);

	@Test
	public void testaBuscaDextraSistemas() {
		System.out.println("SELENIUM_PLATFORM: " + System.getProperty("SELENIUM_PLATFORM"));
		System.out.println("SELENIUM_VERSION : " + System.getProperty("SELENIUM_VERSION"));
		System.out.println("SELENIUM_BROWSER : " + System.getProperty("SELENIUM_BROWSER"));

		siteDextra.navegarNaPagina("http://www.dextra.com.br");
		siteDextra.preencheInputText("input#gsc-i-id1", "java");
		siteDextra.clica("input.gsc-search-button");
		siteDextra.confereResultadoDaBusca(225);
	}

}
