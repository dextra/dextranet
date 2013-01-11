package br.com.dextra.dextranet.web;

import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.web.post.PaginaNovoPost;
import br.com.dextra.teste.PaginaBase;

public class PaginaPrincipal extends PaginaBase {

	public PaginaPrincipal(WebDriver driver) {
		super(driver);
	}

	public PaginaPrincipal acesso() {
		this.navigateTo("http://localhost:8080");
		this.waitingForLoading();
		return this;
	}

	public PaginaNovoPost clicaEmNovoPost() {
		this.click("span.icon_sidebar_left_novopost");
		this.waitingForLoading();
		return new PaginaNovoPost(driver);
	}

}