package br.com.dextra.dextranet.servicos;

import java.util.List;

import br.com.dextra.dextranet.grupo.UsuarioJSON;

public interface ServicosExternos {
	public void adicionaMembros(String token, List<UsuarioJSON> usuarios) throws Exception;

	public void removeMembros(String token, List<UsuarioJSON> usuarios) throws Exception;

	public void obtemMembro(String token, String grupoEmail, String emailUsuario) throws Exception;

	public void listaMembros();
}
