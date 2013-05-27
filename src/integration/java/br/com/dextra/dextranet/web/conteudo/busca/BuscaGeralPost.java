package br.com.dextra.dextranet.web.conteudo.busca;

import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.PaginaBase;

public class BuscaGeralPost extends PaginaBase {

	public BuscaGeralPost(WebDriver driver) {
		super(driver);
	}

	public void redigeConteudoDaBusca(String conteudo) {
		String idInputBusca = "form_search_input";
		this.redigeTextoInput(conteudo, idInputBusca);
	}

	public void clicaNoBotaoPesquisa() {
		this.click("input#form_search_submit");
		waitingForLoading();
	}
}
