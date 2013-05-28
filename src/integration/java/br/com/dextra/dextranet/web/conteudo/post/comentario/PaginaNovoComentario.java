package br.com.dextra.dextranet.web.conteudo.post.comentario;

import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.PaginaBase;

public class PaginaNovoComentario extends PaginaBase {
	private String idTextAreaComentario;

	public PaginaNovoComentario(WebDriver driver) {
		super(driver);
	}

	public PaginaNovoComentario redigeConteudoDoComentario(String conteudo) {
		String idTextArea = "idConteudo_" + idTextAreaComentario;
		this.redigeConteudoTextArea(conteudo, idTextArea);

		return this;
	}

	public void criaNovoComentario(String conteudo) {
		this.redigeConteudoDoComentario(conteudo);
		this.submeteComentario();
	}

	private void submeteComentario() {
		this.click("button#form_comentar_submit");
		this.waitingForLoading();
	}

	public String getIdTextAreaComentario() {
		return idTextAreaComentario;
	}

	public void setIdTextAreaComentario(String idConteudoComentario) {
		this.idTextAreaComentario = idConteudoComentario;
	}
}