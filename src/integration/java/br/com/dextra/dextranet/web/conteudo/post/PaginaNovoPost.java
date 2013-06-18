package br.com.dextra.dextranet.web.conteudo.post;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.PaginaBase;

public class PaginaNovoPost extends PaginaBase {
	private String idPost;

	public PaginaNovoPost(WebDriver driver) {
		super(driver);
	}

	public PaginaNovoPost redigeConteudoDoPost(String titulo, String conteudo) {
		this.redigeTextoInput(titulo, "form_input_title");
		this.redigeCKEditor(conteudo, "form_input_content");

		return this;
	}

	public void submetePost() {
		this.click("button#form_post_submit");
		this.waitingForLoading();
	}

	public void criarNovoPost(String titulo, String conteudo) {
		this.redigeConteudoDoPost(titulo, conteudo);
		this.submetePost();
	}

	public void excluiPost() {
		this.click("button#btn-excluirpost_" + idPost);
		this.waitingForLoading();
	}

	public Boolean existePostPor(String titulo, String conteudo) {
		this.waitToLoad(MAX_ATTEMPT_TO_WAIT);
		List<WebElement> htmlPostsEncontrados = driver.findElements(By.cssSelector("div#content_left_stretch ul#relacao_dos_posts.list_stories"));
		if (driver.findElements(By.cssSelector("div#content_left_stretch ul#relacao_dos_posts.list_stories li")).size() < 1) {
			return false;
		}

		for (WebElement htmlPost : htmlPostsEncontrados) {
			WebElement htmlTitulo = htmlPost.findElement(By.cssSelector("a.list_stories_headline h2.titulo"));
			if (htmlTitulo.getText().equals(titulo)) {
				htmlPost.findElement(By.cssSelector("a.list_stories_headline")).click();
				waitForElement("div.list_stories_contents div p");
				WebElement htmlConteudoPost = htmlPost.findElement(By.cssSelector("div.list_stories_contents div p"));
				if(htmlConteudoPost.getText().equals(conteudo)) {
					idPost = getIdPost(htmlPost);
					return true;
				}
			}
		}

		return false;
	}

	private String getIdPost(WebElement htmlPost) {
		WebElement htmlLi = htmlPost.findElement(By.cssSelector("li"));
		return htmlLi.getAttribute("id");
	}

	public String getIdPost() {
		return idPost;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

}
