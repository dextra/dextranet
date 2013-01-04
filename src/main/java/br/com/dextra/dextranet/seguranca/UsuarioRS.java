package br.com.dextra.dextranet.seguranca;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;


@Path("/usuario")
public class UsuarioRS {

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosUsuario(@Context HttpServletRequest re){
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String logout = userService.createLogoutURL(((HttpServletRequest)re).getRequestURI());

		JsonObject json = new JsonObject();
		json.addProperty("nickName", user.getNickname());
		json.addProperty("email", user.getEmail());
		json.addProperty("logout", logout);

		return json.toString();
	}
}
