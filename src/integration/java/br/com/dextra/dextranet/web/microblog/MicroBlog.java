package br.com.dextra.dextranet.web.microblog;

import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.PaginaBase;

public class MicroBlog extends PaginaBase {
	public MicroBlog(WebDriver driver) {
		super(driver);
	}

	public void redigeMensagem(String conteudo) {
		this.redigeConteudoTextArea(conteudo, "textArea#conteudo_novo_micropost");
	}

	public void clicaBotaoEnviar() {
		this.click("button#form_micropost_submit");
		waitingForLoading();
	}
}
