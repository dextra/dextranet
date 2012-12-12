package br.com.dextra.teste.exemplo;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;

public class DextraTest extends TesteFuncionalBase {

	PaginaDextra siteDextra = new PaginaDextra(driver);

	@Test
	public void testaBuscaDextraSistemas() {
		System.out.println("SELENIUM_PLATFORM: " + System.getenv("SELENIUM_PLATFORM"));
		System.out.println("SELENIUM_VERSION : " + System.getenv("SELENIUM_VERSION"));
		System.out.println("SELENIUM_BROWSER : " + System.getenv("SELENIUM_BROWSER"));
		System.out.println("SELENIUM_HOST: " + System.getenv("SELENIUM_HOST"));
		System.out.println("SELENIUM_PORT: " + System.getenv("SELENIUM_PORT"));

		siteDextra.navegarNaPagina("http://www.dextra.com.br");
		siteDextra.preencheInputText("input#gsc-i-id1", "java");
		siteDextra.clica("input.gsc-search-button");
		siteDextra.confereResultadoDaBusca(225);
	}

}
