package br.com.dextra.dextranet.delorian;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

@Path("/usuarios")
public class TestRestEasy {

	TestRestEasyRepository repository = new TestRestEasyRepository();

	@Path("/obtertodos")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obterTodosUsuarios() {
		return Response.ok().entity(repository.obterTodosUsuarios()).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obterUsuario(@PathParam("id") int index) {
		return Response.ok().entity(repository.obterUsuario(index)).build();
	}

	@Path("/{usuario}")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response adicionarUsuario(@PathParam("usuario") String usuario) {
		return Response.ok().entity(repository.adicionarUsuario(usuario)).build();
	}

	@Path("/{usuario}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletarUsuario(@PathParam("usuario") String usuario) {
		return Response.ok().entity(repository.removerUsuario(usuario)).build();
	}

}