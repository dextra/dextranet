package br.com.dextra.dextranet.web.comment;

import org.openqa.selenium.WebDriver;

import br.com.dextra.teste.PaginaBase;

public class PaginaNovoComentario extends PaginaBase{
	public PaginaNovoComentario(WebDriver driver) {
		super(driver);
	}

	public PaginaNovoComentario redigeConteudoDoComentario(String conteudo) {
		this.writeCKEditor(conteudo, "textarea_comment");

		return this;
	}

	public void submeteComentario() {
		this.click("input#input_comment_submit");
		this.waitingForLoading();
	}

	public void criaNovoComentario(String conteudo) {
		this.redigeConteudoDoComentario(conteudo);
		this.submeteComentario();
	}
}