package br.com.dextra.dextranet.conteudo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.utils.TimeMachine;

public abstract class Conteudo extends Entidade {

	protected String usuario;

	protected String usuarioMD5;

	protected String conteudo;

	protected Date dataDeCriacao;

	protected long quantidadeDeCurtidas;

	protected List<String> usuariosQueCurtiram;

	protected Conteudo(String usuario) {
		super();
		this.usuario = usuario;
		this.usuarioMD5 = Usuario.geraMD5(this.usuario);
		this.dataDeCriacao = new TimeMachine().dataAtual();
		this.quantidadeDeCurtidas = 0;
		this.usuariosQueCurtiram = new ArrayList<String>();
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

	public String getUsuarioMD5() {
		return usuarioMD5;
	}

	public List<String> getUsuariosQueCurtiram() {
		return this.usuariosQueCurtiram;
	}

	public Curtida curtir(String username) {
		Curtida curtida = null;

		if (!this.usuarioJaCurtiu(username)) {
			this.quantidadeDeCurtidas++;
			this.usuariosQueCurtiram.add(username);
			curtida = new Curtida(this.getId(), username);
		}

		return curtida;
	}

	public Conteudo descurtir(String username) {
		if (this.usuarioJaCurtiu(username)) {
			this.quantidadeDeCurtidas--;
			this.usuariosQueCurtiram.remove(username);
		}

		return this;
	}

	public boolean usuarioJaCurtiu(String username) {
		return this.usuariosQueCurtiram.contains(username);
	}

	public void registraDataDeMigracao(Date data) {
		this.dataDeCriacao = data;
	}


}