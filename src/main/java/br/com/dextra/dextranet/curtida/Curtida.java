package br.com.dextra.dextranet.curtida;

import br.com.dextra.dextranet.utils.Data;



public class Curtida {

	private String data;
	private String idPost;
	private String usuarioLogado;

	public Curtida(String usuario, String id) {

		this.idPost = id;
		this.usuarioLogado = usuario;
	}


	public String getData(){
		Data data = new Data();
		this.data = data.pegaData();
		return this.data;

	}

	public String getIdPost() {
		return idPost;
	}


	public String getUsuarioLogado() {
	return usuarioLogado;

	}


}
