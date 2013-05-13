package br.com.dextra.dextranet.delorian;

import java.util.Date;

public class Comentario {
	private Date data;
	private String usuario;
	private String comentario;
	private Integer nid;

	public Comentario() {

	}

	public Comentario(Date data, String usuario, String comentario, Integer nid) {
		this.data = data;
		this.usuario = usuario;
		this.comentario = comentario;
		this.nid = nid;
	}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
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

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}
}
