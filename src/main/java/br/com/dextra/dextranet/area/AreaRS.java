package br.com.dextra.dextranet.area;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import br.com.dextra.dextranet.utils.JsonUtil;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/area")
public class AreaRS {

	private AreaRepository repo = new AreaRepository();

	@Path("/inserir")
	@POST
	@Produces("application/json;charset=UTF-8")
	public String inserir(@FormParam("name") String name) throws EntityNotFoundException {

		Area Area = new Area(name);

		repo.persiste(Area);
		return JsonUtil.stringify(Area);

	}

	@Path("/obter/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String obter(@PathParam("id") String id) throws EntityNotFoundException {
		Area Area = repo.obtemPorId(id);
		return JsonUtil.stringify(Area);
	}

	@Path("/obterTodos")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosEquipe(@Context HttpServletRequest re) {
		List<Area> equipe = repo.lista();
		return JsonUtil.stringify(equipe);

	}

	@Path("/apagar/{id}")
	@GET
	public void apagar(@PathParam(value = "id") String id) {
		repo.remove(id);
	}

}
