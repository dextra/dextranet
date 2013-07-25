package br.com.dextra.dextranet.web.microblog;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.PaginaBase;

public class PaginaMicroBlog extends PaginaBase {
	private String idMicroPost;

	public PaginaMicroBlog(WebDriver driver) {
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
		this.waitingForLoading();
		WebElement htmlMicroPost = this.getElement("ul#list_microposts li:first-child div.micropost-conteudo div.micropost-texto");
		WebElement liMicroPost = this.getElement("ul#list_microposts li:first-child");
		String mensagemComparacao = htmlMicroPost.getText();
		if (StringUtils.isNotEmpty(mensagemComparacao) && mensagemComparacao.equals(mensagem)) {
			if(StringUtils.isNotEmpty(liMicroPost.getText())){
				idMicroPost = getIdMicroPost(liMicroPost);
			}
			return true;
		} else {
			return false;
		}
	}

	public void excluiMicroPost() {
		this.click("button#btn-excluirpost_" + idMicroPost);
		this.waitingForLoading();
	}

	private String getIdMicroPost(WebElement htmlMicroPost) {
		return htmlMicroPost.getAttribute("id");
	}
}
