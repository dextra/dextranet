package br.com.dextra.dextranet.delorian;

import java.util.Date;
import java.util.List;

public class NewPost {

	private String titulo;
	private long quantidadeDeComentarios;
	private Date dataDeAtualizacao;
	private String usuario;
	private String usuarioMD5;
	private String conteudo;
	private Date dataDeCriacao;
	private long quantidadeDeCurtidas;
	private List<String> usuariosQueCurtiram;

	public NewPost() {
	}

	public NewPost(String titulo, long quantidadeDeComentarios,
			Date dataDeAtualizacao, String usuario, String usuarioMD5,
			String conteudo, Date dataDeCriacao, long quantidadeDeCurtidas,
			List<String> usuariosQueCurtiram) {
		this.titulo = titulo;
		this.quantidadeDeComentarios = quantidadeDeComentarios;
		this.dataDeAtualizacao = dataDeAtualizacao;
		this.usuario = usuario;
		this.usuarioMD5 = usuarioMD5;
		this.conteudo = conteudo;
		this.dataDeCriacao = dataDeCriacao;
		this.quantidadeDeCurtidas = quantidadeDeCurtidas;
		this.usuariosQueCurtiram = usuariosQueCurtiram;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public long getQuantidadeDeComentarios() {
		return quantidadeDeComentarios;
	}

	public void setQuantidadeDeComentarios(long quantidadeDeComentarios) {
		this.quantidadeDeComentarios = quantidadeDeComentarios;
	}

	public Date getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	public void setDataDeAtualizacao(Date dataDeAtualizacao) {
		this.dataDeAtualizacao = dataDeAtualizacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuarioMD5() {
		return usuarioMD5;
	}

	public void setUsuarioMD5(String usuarioMD5) {
		this.usuarioMD5 = usuarioMD5;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Date getDataDeCriacao() {
		return dataDeCriacao;
	}

	public void setDataDeCriacao(Date dataDeCriacao) {
		this.dataDeCriacao = dataDeCriacao;
	}

	public long getQuantidadeDeCurtidas() {
		return quantidadeDeCurtidas;
	}

	public void setQuantidadeDeCurtidas(long quantidadeDeCurtidas) {
		this.quantidadeDeCurtidas = quantidadeDeCurtidas;
	}

	public List<String> getUsuariosQueCurtiram() {
		return usuariosQueCurtiram;
	}

	public void setUsuariosQueCurtiram(List<String> usuariosQueCurtiram) {
		this.usuariosQueCurtiram = usuariosQueCurtiram;
	}
}
