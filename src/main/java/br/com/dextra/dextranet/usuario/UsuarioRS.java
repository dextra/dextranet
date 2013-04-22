package br.com.dextra.dextranet.usuario;

import java.util.List;

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

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/usuario")
public class UsuarioRS {

	private UsuarioRepository repositorio = new UsuarioRepository();

	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("id") String id, @FormParam("nome") String nome,
			@FormParam("apelido") String apelido, @FormParam("area") String area, @FormParam("unidade") String unidade,
			@FormParam("ramal") String ramal, @FormParam("telefoneResidencial") String telefoneResidencial,
			@FormParam("telefoneCelular") String telefoneCelular, @FormParam("gitHub") String gitHub,
			@FormParam("skype") String skype) throws EntityNotFoundException {

		Usuario usuario = repositorio.obtemPorId(id);

		String usuarioLogado = this.obtemUsernameDoUsuarioLogado();
		if (!usuario.getUsername().equals(usuarioLogado)) {
			return Response.status(Status.FORBIDDEN).build();
		}

		usuario.preenchePerfil(nome, apelido, area, unidade, ramal, telefoneResidencial, telefoneCelular, gitHub, skype);
		repositorio.persiste(usuario);

		return Response.ok().entity(usuario).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Usuario usuario = repositorio.obtemPorId(id);
		return Response.ok().entity(usuario).build();
	}

	@Path("/logado")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obterUsuarioLogado() {
		Usuario usuario = repositorio.obtemPorUsername(this.obtemUsernameDoUsuarioLogado());
		return Response.ok().entity(usuario).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Usuario> usuarios = repositorio.lista();
		return Response.ok().entity(usuarios).build();
	}

	protected String obtemUsernameDoUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}