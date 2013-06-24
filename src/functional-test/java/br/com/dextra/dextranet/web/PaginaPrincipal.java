package br.com.dextra.dextranet.web;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.PaginaBase;
import br.com.dextra.dextranet.web.conteudo.post.PaginaPost;
import br.com.dextra.dextranet.web.conteudo.post.comentario.PaginaComentario;

public class PaginaPrincipal extends PaginaBase {

	public PaginaPrincipal(WebDriver driver) {
		super(driver);
	}

	public void acessaPaginaPrincipal() {
		acesso();
	}

	private PaginaPrincipal acesso() {
		this.navigateTo("http://localhost:8080");
		this.waitingForLoading();
		return this;
	}

	public PaginaPost clicaEmNovoPost() {
		this.click("span.icon_sidebar_left_novopost");
		this.waitingForLoading();
		return new PaginaPost(driver);
	}

	public PaginaComentario abreNovoComentario() {
		this.waitingForLoading();
		return new PaginaComentario(driver);
	}

	public PaginaPrincipal scrollAteFim() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// desce ate o fim do scroll
		js.executeScript("window.scrollTo(0, 100000);");
		this.waitingForLoading();
		// volta o scroll para poder descer novamente
		js.executeScript("window.scrollTo(0, -100000);");

		return this;
	}

	public boolean existePost(String titulo, String conteudo) {
		List<WebElement> htmlPostsEncontrados = driver.findElements(By.cssSelector("div#content_left_stretch ul#relacao_dos_posts.list_stories"));

		for (WebElement htmlPost : htmlPostsEncontrados) {
			WebElement htmlTitulo = htmlPost.findElement(By.cssSelector("a.list_stories_headline h2.titulo"));
			if (htmlTitulo.getText().equals(titulo)) {
				htmlPost.findElement(By.cssSelector("a.list_stories_headline")).click();
				waitForElement("div.list_stories_contents div p");
				WebElement htmlConteudoPost = htmlPost.findElement(By.cssSelector("div.list_stories_contents div p"));
				if(htmlConteudoPost.getText().equals(conteudo)) {
					return true;
				}
			}
		}

		return false;
	}
}