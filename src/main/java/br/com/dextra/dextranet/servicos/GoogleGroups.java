package br.com.dextra.dextranet.servicos;

import java.util.List;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.dextra.dextranet.grupo.UsuarioJSON;

public class GoogleGroups implements ServicosExternos {
	private List<String> gruposEmail;

	public GoogleGroups(List<String> gruposEmail) {
		this.gruposEmail = gruposEmail;
	}

	@Override
	public void adicionaMembros(String token, List<UsuarioJSON> usuarios) throws Exception {
		ClientRequest client = null;
		for (String grupoEmail : gruposEmail) {
			client = new ClientRequest("https://www.googleapis.com/admin/directory/v1/groups/[groupKey]/members".replace("[groupKey]", grupoEmail));

			for (UsuarioJSON usuarioJSON : usuarios) {
				client.body("email", usuarioJSON.getEmail());
			}

			client.header("Authorization", "Bearer " + token);
			try {
				client.execute();
			} catch (Exception e) {
				throw new Exception("Erro ao aprovisionar usuários ao Google Groups");
			}
		}
	}

	@Override
	public void removeMembros(String token, List<UsuarioJSON> usuarios) throws Exception {
		ClientRequest client = null;
		for (String grupoEmail : gruposEmail) {
			for (UsuarioJSON usuario : usuarios) {
				client = new ClientRequest("https://www.googleapis.com/admin/directory/v1/groups/" + grupoEmail + "/members/" + usuario);
				client.header("Authorization", "Bearer " + token);
				client.execute();
			}
		}
	}

	@Override
	public void obtemMembro(String token, String grupoEmail, String emailUsuario) throws Exception {
		ClientRequest client = null;
		client = new ClientRequest("https://www.googleapis.com/admin/directory/v1/groups/" + grupoEmail + "/members/" + emailUsuario);
		ClientResponse response = client.post();
		//TODO: refatorar
	}

	@Override
	public void listaMembros() {
		// TODO Auto-generated method stub
	}
}
