package br.com.dextra.dextranet.area;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/area")
public class AreaRS {

	private AreaRepository repo = new AreaRepository();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("name") String name) throws EntityNotFoundException {
		Area area = new Area(name);
		repo.persiste(area);

		return Response.ok().entity(area).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Area area = repo.obtemPorId(id);
		return Response.ok().entity(area).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar(@Context HttpServletRequest re) {
		List<Area> areas = repo.lista();
		return Response.ok().entity(areas).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response apagar(@PathParam(value = "id") String id) {
		repo.remove(id);
		return Response.ok().build();
	}

}
