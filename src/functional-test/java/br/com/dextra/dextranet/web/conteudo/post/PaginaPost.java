package br.com.dextra.dextranet.web.conteudo.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
		String seletorPostsEncontrados = "div#content_left_stretch ul#relacao_dos_posts.list_stories li";
		waitForElement(seletorPostsEncontrados);
		List<WebElement> htmlPostsEncontrados = encontraPosts();
		if (driver.findElements(By.cssSelector(seletorPostsEncontrados)).size() < 1) {
			return false;
		}
		for (WebElement htmlPost : htmlPostsEncontrados) {
			WebElement htmlTitulo = htmlPost.findElement(By.cssSelector("a.list_stories_headline h2.titulo"));
			if (StringUtils.isNotEmpty(htmlTitulo.getText()) && htmlTitulo.getText().equals(titulo)) {
				String seletorLinkPost = "a.list_stories_headline";
				clickWithJQuery(seletorLinkPost);
				WebElement htmlConteudoPost = getConteudoPost(htmlPost);
				if(htmlConteudoPost.getText().equals(conteudo)) {
					idPost = getIdPost(htmlPost);
					return true;
				}
			}
		}

		return false;
	}

	private void clickWithJQuery(String seletorLink) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("jQuery('"+ seletorLink +"').click();");
	}

	private WebElement getConteudoPost(WebElement htmlPost) {
		String conteudoDoComentarioCSSSelector = "div.story-content div.list_stories_contents div.idClassPost";
		WebElement htmlConteudoPost = htmlPost.findElement(By.cssSelector(conteudoDoComentarioCSSSelector));

		return htmlConteudoPost;
	}

	private List<WebElement> encontraPosts() {
		List<WebElement> htmlPostsEncontrados = driver.findElements(By.cssSelector("div#content_left_stretch ul#relacao_dos_posts.list_stories"));
		if (htmlPostsEncontrados != null) {
			return htmlPostsEncontrados;
		}
		return new ArrayList<WebElement>();
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

//	public void waitForElement(String cssSelector) {
//		int MAX_WAIT = 200;
//		int TIME_WAIT = 3;
//		driver.manage().timeouts().pageLoadTimeout(TIME_WAIT, TimeUnit.MILLISECONDS);
//
//		int attempts = 1;
//
//		while (attempts < MAX_WAIT) {
//			try {
//				if (this.driver.findElement(By.cssSelector(cssSelector)).isDisplayed()) {
//					break;
//				}
//			} catch (NoSuchElementException nsee) {
//			}
//			attempts++;
//
//			driver.manage().timeouts().pageLoadTimeout(TIME_WAIT, TimeUnit.MILLISECONDS);
//		}
//
//		if (attempts >= MAX_WAIT) {
//			throw new TimedOutException(cssSelector + " element did not find.");
//		}
//	}
}
