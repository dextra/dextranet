package br.com.dextra.dextranet.delorian;

public class OldPost {
	private String usuario;
	private String data;
	private String titulo;
	private String conteudo;

	public OldPost() {

	}

	public OldPost(String nome, String data, String titulo, String conteudo) {
		this.usuario = nome;
		this.data = data;
		this.titulo = titulo;
		this.conteudo = conteudo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String nome) {
		this.usuario = nome;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
