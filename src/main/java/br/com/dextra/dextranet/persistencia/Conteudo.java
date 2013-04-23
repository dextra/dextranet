package br.com.dextra.dextranet.persistencia;

import java.util.Date;

import br.com.dextra.dextranet.utils.TimeMachine;

public abstract class Conteudo extends Entidade {

	protected String usuario;

	protected String conteudo;

	protected Date dataDeCriacao;

	protected long quantidadeDeCurtidas;

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

	public long getQuantidadeDeCurtidas() {
		return quantidadeDeCurtidas;
	}

}
