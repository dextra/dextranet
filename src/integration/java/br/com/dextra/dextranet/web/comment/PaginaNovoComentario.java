package br.com.dextra.dextranet.web.comment;

import org.openqa.selenium.WebDriver;

import br.com.dextra.teste.PaginaBase;

public class PaginaNovoComentario extends PaginaBase {
	private String idConteudoComentario;

	public PaginaNovoComentario(WebDriver driver) {
		super(driver);
	}

	public PaginaNovoComentario redigeConteudoDoComentario(String conteudo) {
		String idTextArea = "idConteudo_" + idConteudoComentario;
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

	public String getIdConteudoComentario() {
		return idConteudoComentario;
	}

	public void setIdConteudoComentario(String idConteudoComentario) {
		this.idConteudoComentario = idConteudoComentario;
	}
}