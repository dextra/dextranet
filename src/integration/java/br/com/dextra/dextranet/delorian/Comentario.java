package br.com.dextra.dextranet.delorian;

public class Comentario {
	private String postId;
	private String data;
	private String usuario;
	private String comentario;

	public Comentario() {

	}

	public Comentario(String data, String usuario, String comentario) {
		this.data = data;
		this.usuario = usuario;
		this.comentario = comentario;
	}

	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
}
