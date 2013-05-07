package br.com.dextra.dextranet.delorian;

import java.util.LinkedList;

public class TestRestEasyRepository {
	private LinkedList<String> usuarios = new LinkedList<String>();

	public String adicionarUsuario(String usuario) {
		usuarios.add(usuario);
		return usuario;
	}

	public String removerUsuario(String usuario) {
		usuarios.remove(usuario);
		return usuario;
	}

	public String obterUsuario(int index) {
		return usuarios.get(index);
	}

	public LinkedList<String> obterTodosUsuarios() {
		return usuarios;
	}
}
