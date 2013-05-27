package br.com.dextra.dextranet.web.conteudo.post.comentario;

import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.PaginaBase;

public class PaginaNovoComentario extends PaginaBase {
	private String idPost;
	private String idComentario;

	public PaginaNovoComentario(WebDriver driver) {
		super(driver);
	}

	public PaginaNovoComentario redigeConteudoDoComentario(String conteudo) {
		String idTextArea = "idConteudo_" + idPost;
		this.redigeConteudoTextArea(conteudo, idTextArea);

		return this;
	}

	public void criaNovoComentario(String conteudo) {
		this.redigeConteudoDoComentario(conteudo);
		this.submeteComentario();
	}

	public void clicaEmNovoComentario() {
		this.click("a#idComentarioLink_" + idPost);
	}

	private void submeteComentario() {
		this.click("button#form_comentar_submit");
		this.waitingForLoading();
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