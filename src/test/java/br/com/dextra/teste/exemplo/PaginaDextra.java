package br.com.dextra.teste.exemplo;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;

import br.com.dextra.teste.PaginaBase;

public class PaginaDextra extends PaginaBase {

	public PaginaDextra(WebDriver driver) {
		super(driver);
	}

	public void confereResultadoDaBusca(int registrosEsperados) {
		String resultadoEncontrado = this.obtemConteudoDoElemebto("div#resInfo-0");
		int registrosEncontrados = Integer.valueOf(resultadoEncontrado.split(" ")[1]);
		Assert.assertEquals(registrosEsperados, registrosEncontrados);
	}

}
