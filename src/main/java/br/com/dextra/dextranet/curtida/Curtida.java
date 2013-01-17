package br.com.dextra.dextranet.curtida;

import br.com.dextra.dextranet.utils.Data;



public class Curtida {

	private String data;
	private String id;
	private String usuario;

	public Curtida(String usuario, String id) {
		this.id = id;
		this.usuario = usuario;
		this.data = new Data().pegaData();
	}

	public Curtida(String usuario, String id, String data) {
		this.id = id;
		this.usuario = usuario;
		this.data = data;
	}

	public String getData(){
		return this.data;
	}

	public String getIdReference() {
		return this.id;
	}


	public String getUsuarioLogado() {
		return this.usuario;
	}


}
