package br.com.dextra.dextranet.web.post;

import org.openqa.selenium.WebDriver;

import br.com.dextra.teste.PaginaBase;

public class PaginaNovoPost extends PaginaBase {

	public PaginaNovoPost(WebDriver driver) {
		super(driver);
	}

	public PaginaNovoPost redigeConteudoDoPost(String titulo, String conteudo) {
		this.writeInputText("input#form_input_title",titulo);
		this.writeCKEditor(conteudo, "#form_new_post");

		return this;
	}

	public void submetePost() {
		this.click("input#form_post_submit");
		this.waitingForLoading();
	}

	public void criaNovoPost(String titulo, String conteudo) {
		this.redigeConteudoDoPost(titulo, conteudo);
		this.submetePost();
	}

}
