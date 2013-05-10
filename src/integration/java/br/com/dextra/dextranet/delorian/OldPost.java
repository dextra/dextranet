package br.com.dextra.dextranet.delorian;

import java.util.Date;

public class OldPost {
	private String usuario;
	private Date data;
	private String titulo;
	private String conteudo;
	private Integer nid;

	public OldPost() {

	}

	public OldPost(String nome, Date data, String titulo, String conteudo, Integer nid) {
		this.usuario = nome;
		this.data = data;
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.nid = nid;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String nome) {
		this.usuario = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}
}
