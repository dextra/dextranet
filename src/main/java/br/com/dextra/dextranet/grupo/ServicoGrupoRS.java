package br.com.dextra.dextranet.grupo;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/servicoGrupo")
public class ServicoGrupoRS {

	private ServicoGrupoRepository repo = new ServicoGrupoRepository();

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		ServicoGrupo servico = repo.obtemPorId(id);
		return Response.ok().entity(servico).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<ServicoGrupo> servico = repo.lista();
		return Response.ok().entity(servico).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response apagar(@PathParam(value = "id") String id) {
		repo.remove(id);
		return Response.ok().build();
	}

}
