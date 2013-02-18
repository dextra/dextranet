package br.com.dextra.dextranet.perfil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import br.com.dextra.dextranet.area.Area;
import br.com.dextra.dextranet.area.AreaRepository;
import br.com.dextra.dextranet.unidade.Unidade;
import br.com.dextra.dextranet.unidade.UnidadeRepository;
import br.com.dextra.dextranet.utils.JsonUtil;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;

@Path("/perfil")
public class PerfilRS {

	private PerfilRepository repo = new PerfilRepository();
	private AreaRepository ar = new AreaRepository();
	private UnidadeRepository ur = new UnidadeRepository();

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosPerfil(@Context HttpServletRequest re) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String logout = userService.createLogoutURL("");

		JsonObject json = new JsonObject();
		json.addProperty("nickName", user.getNickname());
		json.addProperty("email", user.getEmail());
		json.addProperty("logout", logout);
		json.addProperty("id", user.getUserId());

		return json.toString();
	}

	@Path("/inserir")
	@POST
	@Produces("application/json;charset=UTF-8")
	public String inserir(@FormParam("name") String name,
			@FormParam("nickName") String nickName,
			@FormParam("area") String area, @FormParam("unit") String unit,
			@FormParam("branch") String branch,
			@FormParam("skype") String skype, @FormParam("gTalk") String gTalk,
			@FormParam("phoneResidence") String phoneResidence,
			@FormParam("phoneMobile") String phoneMobile,
			@FormParam("image") String image) throws EntityNotFoundException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Perfil perfil = new Perfil(user.getUserId(), name, user.getNickname(),
				area, unit, branch, skype, gTalk, phoneResidence, phoneMobile,
				image);

		if (name != null && !name.isEmpty() || phoneMobile != null && !phoneMobile.isEmpty()) {
			repo.novo(perfil);
			return JsonUtil.stringify(perfil);
		} else {
			return null;
		}
	}

	@Path("/obter/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String obter(@PathParam("id") String id) {
		Perfil perfil = repo.buscar(id);
		List<Area> lista = ar.buscarTodos();
		perfil.setAreasPossiveis(lista);
		List<Unidade> lista2 = ur.buscarTodos();
		perfil.setUnidadesPossiveis(lista2);
		return JsonUtil.stringify(perfil);
	}

	@Path("/obterPorNome")
	@POST
	@Produces("application/json;charset=UTF-8")
	public String getDadosEquipeNome(@FormParam("name") String name)
			throws EntityNotFoundException {
		List<Perfil> equipe = repo.obterPorNome(name);
		return JsonUtil.stringify(equipe);
	}

	@Path("/obterTodos")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosEquipe(@Context HttpServletRequest re) {
		List<Perfil> equipe = repo.obterTodos();
		return JsonUtil.stringify(equipe);

	}

	@Path("/obterPorInicial/{inicial}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosEquipeInicial(@PathParam("inicial") String inicial) {
		List<Perfil> equipe = repo.obterPorInicial(inicial.charAt(0));
		return JsonUtil.stringify(equipe);

	}
}
