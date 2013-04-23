package br.com.dextra.dextranet.persistencia;

import java.util.Date;

import br.com.dextra.dextranet.utils.ConteudoHTML;
import br.com.dextra.dextranet.utils.TimeMachine;

public abstract class Conteudo extends Entidade {

	protected String usuario;

	protected String conteudo;

	protected Date dataDeCriacao;

	protected int quantidadeDeCurtidas;

	protected Conteudo(String usuario) {
		super();
		this.usuario = usuario;
		this.dataDeCriacao = new TimeMachine().dataAtual();
		this.quantidadeDeCurtidas = 0;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getConteudo() {
		return conteudo;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public int getQuantidadeDeCurtidas() {
		return quantidadeDeCurtidas;
	}

	public Conteudo preenche(String conteudo) {
		this.conteudo = new ConteudoHTML(conteudo).removeJavaScript();
		return this;
	}

}
