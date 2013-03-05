package br.com.dextra.dextranet.usuario;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;

@Path("/usuario")
public class UsuarioRS {

	UsuarioRepository usuarioRepository = new UsuarioRepository();

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosUsuario() {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		ArrayList<Usuario> listaDeUsuarios = usuarioRepository
				.getTodosUsuarios();
		if (listaDeUsuarios != null) {
			for (Usuario usuario : listaDeUsuarios) {
				if (usuario.getEmail().equals(user.getEmail())) {
					JsonObject usuarioAtual = usuario.toJson();
					usuarioAtual.addProperty("logout", UserServiceFactory
							.getUserService().createLogoutURL(""));
					return usuarioAtual.toString();
				}
			}
		}

		JsonObject novoUsuario = usuarioRepository.novoUsuario().toJson();
		novoUsuario.addProperty("logout", UserServiceFactory.getUserService()
				.createLogoutURL(""));
		novoUsuario.addProperty("id", user.getUserId());

		return novoUsuario.toString();
	}
}
