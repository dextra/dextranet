package br.com.dextra.dextranet.web.microblog;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.PaginaBase;

public class MicroBlog extends PaginaBase {
	public MicroBlog(WebDriver driver) {
		super(driver);
	}

	public void redigeMensagem(String conteudo) {
		this.redigeConteudoTextArea(conteudo, "conteudo_novo_micropost");
	}

	public void clicaBotaoEnviar() {
		this.click("button#form_micropost_submit");
		waitingForLoading();
	}

	public Boolean microPostExistente(String mensagem) {
		WebElement htmlMicroPost = this.getElement("ul#list_microposts li:first-child div.micropost-conteudo div.micropost-texto.wordwrap");
		String mensagemComparacao = htmlMicroPost.getText();
		if (StringUtils.isNotEmpty(mensagemComparacao) && mensagemComparacao.equals(mensagem)) {
			return true;
		} else {
			return false;
		}
	}
}
