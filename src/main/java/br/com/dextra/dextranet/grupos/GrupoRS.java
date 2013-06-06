package br.com.dextra.dextranet.grupos;

import java.util.List;

import javax.ws.rs.DELETE;
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
public class GrupoRS {
	GrupoRepository repositorio = new GrupoRepository();

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Grupo> teams = repositorio.lista();
		return Response.ok().entity(teams).build();
	}

	@Path("/")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response adicionar(@FormParam("nome") String nome, @FormParam("emailGrupo") String emailGrupo) {
		Grupo team = new Grupo(nome, emailGrupo);
		repositorio.persiste(team);
		return Response.ok().entity(team).build();
	}

	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("id") String id, @FormParam("nome") String nome, @FormParam("emailGrupo") String emailGrupo) throws EntityNotFoundException {
		Grupo team = repositorio.obtemPorId(id);
		team = team.preenche(nome, emailGrupo);
		repositorio.persiste(team);
		return Response.ok().entity(team).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletar(@PathParam("id") String id) {
		repositorio.remove(id);
		return Response.ok().build();
	}
}
