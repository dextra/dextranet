package br.com.dextra.dextranet.web.conteudo.post;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.PaginaBase;

public class PaginaPost extends PaginaBase {
	private String idPost;

	public PaginaPost(WebDriver driver) {
		super(driver);
	}

	public PaginaPost redigeConteudoDoPost(String titulo, String conteudo) {
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
		List<WebElement> htmlPostsEncontrados = driver.findElements(By.cssSelector("div#content_left_stretch ul#relacao_dos_posts.list_stories"));
		if (driver.findElements(By.cssSelector("div#content_left_stretch ul#relacao_dos_posts.list_stories li")).size() < 1) {
			return false;
		}

		for (WebElement htmlPost : htmlPostsEncontrados) {
			WebElement htmlTitulo = htmlPost.findElement(By.cssSelector("a.list_stories_headline h2.titulo"));
			if (htmlTitulo.getText().equals(titulo)) {
				htmlPost.findElement(By.cssSelector("a.list_stories_headline")).click();

				String conteudoDoComentarioCSSSelector = "div.story-content div.list_stories_contents div.idClassPost";
				waitForElement(conteudoDoComentarioCSSSelector);
				WebElement htmlConteudoPost = htmlPost.findElement(By.cssSelector(conteudoDoComentarioCSSSelector));

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
