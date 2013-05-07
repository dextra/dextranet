package br.com.dextra.dextranet.delorian;

import java.util.Date;

public class PostOld {
	private String nome;
	private Date data;
	private String titulo;
	private String conteudo;

	public PostOld() {

	}

	public PostOld(String nome, Date data, String titulo, String conteudo) {
		this.nome = nome;
		this.data = data;
		this.titulo = titulo;
		this.conteudo = conteudo;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
}
