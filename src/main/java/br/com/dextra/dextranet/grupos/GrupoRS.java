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

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/grupo")
public class GrupoRS {
	GrupoRepository repositorio = new GrupoRepository();

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Grupo> grupos = repositorio.lista();
		return Response.ok().entity(grupos).build();
	}

	@Path("/")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response adicionar(@FormParam("nome") String nome) {
		Grupo grupo = new Grupo(nome);
		repositorio.persiste(grupo);
		return Response.ok().entity(grupo).build();
	}

	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("id") String id, @FormParam("nome") String nome) throws EntityNotFoundException {
		Grupo grupo = repositorio.obtemPorId(id);
		grupo = grupo.preenche(nome);
		repositorio.persiste(grupo);
		return Response.ok().entity(grupo).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletar(@PathParam("id") String id) {
		repositorio.remove(id);
		return Response.ok().build();
	}
}
