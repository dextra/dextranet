package br.com.dextra.dextranet.persistencia;

import br.com.dextra.dextranet.utils.DadosHelper;
import br.com.dextra.dextranet.utils.Data;

public abstract class Conteudo extends Entidade {

	protected String usuario;

	protected String conteudo;

	protected String dataDeCriacao;

	protected int comentarios;

	protected int likes;

	protected String userLikes;

	public Conteudo() {
		super();
	}

	public Conteudo(String usuario, String conteudo) {
		super();
		this.conteudo = new DadosHelper().removeConteudoJS(conteudo);
		this.usuario = usuario;
		this.dataDeCriacao = new Data().pegaData();
		this.comentarios = 0;
		this.likes = 0;
		this.userLikes = "";
	}

}
