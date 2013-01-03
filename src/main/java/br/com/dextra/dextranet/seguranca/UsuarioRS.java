package br.com.dextra.dextranet.seguranca;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.openqa.selenium.remote.JsonException;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;


@Path("/usuario")
public class UsuarioRS {

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public static String getDadosUsuario() throws JsonException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		JsonObject json = new JsonObject();
		json.addProperty("nickName", user.getNickname());
		json.addProperty("email", user.getEmail());

		return json.toString();
	}
}
