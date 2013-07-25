package br.com.dextra.dextranet.web.conteudo.post.comentario;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.dextranet.PaginaBase;

public class PaginaComentario extends PaginaBase {
	private String idPost;
	private String idComentario;

	public PaginaComentario(WebDriver driver) {
		super(driver);
	}

	public PaginaComentario redigeConteudoDoComentario(String conteudo) {
		String idTextArea = "idConteudo_" + idPost;
		this.redigeConteudoTextArea(conteudo, idTextArea);

		return this;
	}

	public void criaNovoComentario(String conteudo) {
		this.redigeConteudoDoComentario(conteudo);
		this.submeteComentario();
	}

	public void clicaEmMostrarComentarios() {
		this.click("a#idComentarioLink_" + idPost);
	}

	public void clicaEmMostrarComentarioPrimeiroPost() {
		this.click("#relacao_dos_posts li:first-child div.list_stories_data div.list_stories_numbercomments a");
	}

	private void submeteComentario() {
		this.click("button#form_comentar_submit");
		this.waitingForLoading();
	}

	public Boolean existeComentarioPor(String comentario) {
		WebElement htmlComentario = this.getElement("#relacao_dos_posts li:first-child div.list_stories_newcomment.hidden ul.list_stories_comments li:first-child p:first-child");
		String comentarioPagina = htmlComentario.getText();
		if (StringUtils.isNotEmpty(comentarioPagina) && comentario.equals(comentarioPagina)) {
			return true;
		}

		return false;
	}

	public Boolean existeComentario() {
		List<WebElement> htmlComentariosEncontrados = driver.findElements(By.cssSelector("ul.list_stories_comments li.clearfix"));
		if (htmlComentariosEncontrados != null && htmlComentariosEncontrados.size() > 0) {
			idComentario = htmlComentariosEncontrados.get(0).getAttribute("id");
			return true;
		}
		return false;
	}

	public String getIdPost() {
		return idPost;
	}

	public String getIdComentario() {
		return idComentario;
	}

	public void setIdPost(String idPost) {
		this.idPost = idPost;
	}

	public void setIdComentario(String idComentario) {
		this.idComentario = idComentario;
	}

}