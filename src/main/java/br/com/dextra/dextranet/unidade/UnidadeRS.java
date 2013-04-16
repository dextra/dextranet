package br.com.dextra.dextranet.unidade;

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

@Path("/unidade")
public class UnidadeRS {

	private UnidadeRepository repo = new UnidadeRepository();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("nome") String nome) {
		Unidade unidade = new Unidade(nome);
		repo.persiste(unidade);

		return Response.ok().entity(unidade).build();

	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Unidade unidade = repo.obtemPorId(id);
		return Response.ok().entity(unidade).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Unidade> unidades = repo.lista();
		return Response.ok().entity(unidades).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response apagar(@PathParam(value = "id") String id) {
		repo.remove(id);
		return Response.ok().build();
	}

}
