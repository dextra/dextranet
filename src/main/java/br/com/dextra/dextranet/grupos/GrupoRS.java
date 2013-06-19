package br.com.dextra.dextranet.grupos;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;
import br.com.dextra.dextranet.usuario.Usuario;

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
	public Response adicionar(@FormParam("nome") String nome, @FormParam("descricao") String descricao, @FormParam("proprietario") String proprietario) {
		//TODO: Ajustar os usuarios - Rodrigo
		Grupo grupo = new Grupo(nome, descricao, proprietario, new ArrayList<Usuario>());
		repositorio.persiste(grupo);
		return Response.ok().entity(grupo).build();
	}

	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("id") String id, @FormParam("nome") String nome, @FormParam("descricao") String descricao, @FormParam("proprietario") String proprietario) throws EntityNotFoundException {
		Grupo grupo = repositorio.obtemPorId(id);
		//TODO: Ajustar os usuarios - Rodrigo
		grupo = grupo.preenche(nome, descricao, proprietario, new ArrayList<Usuario>());
		repositorio.persiste(grupo);
		return Response.ok().entity(grupo).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletar(@PathParam("id") String id) throws EntityNotFoundException {
		String usuarioLogado = obtemUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(id);

		if (usuarioLogado.equals(grupo.getProprietario())) {
			repositorio.remove(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}
}