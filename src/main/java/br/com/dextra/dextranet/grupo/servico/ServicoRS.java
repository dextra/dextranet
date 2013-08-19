package br.com.dextra.dextranet.grupo.servico;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/servico")
public class ServicoRS {

	private ServicoRepository repo = new ServicoRepository();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("nome") String nome) throws EntityNotFoundException {
		Servico servico = new Servico(nome);
		repo.persiste(servico);

		return Response.ok().entity(servico).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Servico servico = repo.obtemPorId(id);
		return Response.ok().entity(servico).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Servico> servico = repo.lista();
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
