package br.com.dextra.dextranet.time;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.dextra.dextranet.rest.config.Application;

@Path("/team")
public class TeamRS {
	TeamRepository repositorio = new TeamRepository();

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Team> teams = repositorio.lista();
		return Response.ok().entity(teams).build();
	}

	@Path("/")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response adicionar(@FormParam("nome") String nome, @FormParam("emailGrupo") String emailGrupo) {

		return null;
	}

	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("id") String id, @FormParam("nome") String nome, @FormParam("emailGrupo") String emailGrupo) throws EntityNotFoundException {
		Team team = repositorio.obtemPorId(id);
		team = team.preenche(nome, emailGrupo);
		repositorio.persiste(team);
		return Response.ok().entity(team).build();
	}
}
