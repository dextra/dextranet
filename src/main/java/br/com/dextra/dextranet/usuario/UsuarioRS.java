package br.com.dextra.dextranet.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoJSON;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.servico.google.Aprovisionamento;
import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;

@Path("/usuario")
public class UsuarioRS {
	private UsuarioRepository repositorio = new UsuarioRepository();
	private GrupoRepository grupoRepositorio = new GrupoRepository();
	private MembroRepository MembroRepository = new MembroRepository();
	
	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("id") String id, @FormParam("nome") String nome,
			@FormParam("apelido") String apelido, @FormParam("area") String area, @FormParam("unidade") String unidade,
			@FormParam("ramal") String ramal, @FormParam("telefoneResidencial") String telefoneResidencial,
			@FormParam("telefoneCelular") String telefoneCelular, @FormParam("gitHub") String gitHub,
			@FormParam("skype") String skype, @FormParam("blog") String blog, @FormParam("ativo") Boolean ativo) throws EntityNotFoundException {

		Usuario usuario = repositorio.obtemPorId(id);

		if (!possuiAcesso(usuario)) {
			return Response.status(Status.FORBIDDEN).build();
		}
		
		if (ativo == null) {
			usuario.preenchePerfil(nome, apelido, area, unidade, ramal, telefoneResidencial, telefoneCelular, gitHub, skype, blog);
			usuario.setAtivo(Boolean.TRUE);
		} else {
			usuario.setAtivo(ativo);
		}
		
		repositorio.persiste(usuario);
		isDesativacaoUsuario(usuario);
		return Response.ok().entity(usuario.getUsuarioJSON()).build();
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
		Boolean isInfra = this.usuarioLogadoIsInfra();
		Usuario usuario = repositorio.obtemPorUsername(this.obtemUsernameDoUsuarioLogado());
		JsonObject usuarioJson = new JsonObject();
		usuarioJson.addProperty("id", usuario.getId());
		usuarioJson.addProperty("apelido", usuario.getApelido());
		usuarioJson.addProperty("username", usuario.getUsername());
		usuarioJson.addProperty("isInfra", isInfra);
		
		return Response.ok().entity(usuarioJson.toString()).build();
	}

	@Path("/ajustarbanco")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response ajustarFlagAtivoBanco() throws EntityNotFoundException {
		List<Usuario> usuarios = repositorio.lista();
		for (Usuario usuario : usuarios) {
			if (usuario.isAtivo() == null) {
				usuario.setAtivo(true);
				repositorio.persiste(usuario);
			}
		}
		
		return Response.status(Status.OK).build();
	}
	
	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() throws EntityNotFoundException {
		List<Usuario> usuarios = repositorio.listaPor(usuarioLogadoIsInfra());
		return Response.ok().entity(usuarios).build();
	}

	@Path("/url-logout")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response urlDeLogout() {
		UserService userService = UserServiceFactory.getUserService();

		JsonObject json = new JsonObject();
		json.addProperty("url", userService.createLogoutURL("/index.html"));

		return Response.ok().entity(json.toString()).build();
	}

	@Path("/{id}/{idGrupo}/adicionar-usuario-grupo")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response adicionarUsuarioGrupo(@PathParam("id") String id, @PathParam("idGrupo") String idGrupo) throws EntityNotFoundException {
		Usuario usuario = repositorio.obtemPorId(id);
		repositorio.persiste(usuario);
		return Response.ok().entity(usuario).build();
	}

	protected String obtemUsernameDoUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

	protected Boolean usuarioLogadoIsInfra() {
		return AutenticacaoService.isUsuarioGrupoInfra();
	}
	
	private boolean possuiAcesso(Usuario usuario) {
		return usuarioLogadoIsInfra() || usuario.getUsername().equals(this.obtemUsernameDoUsuarioLogado());
	}

	private void isDesativacaoUsuario(Usuario usuario) throws EntityNotFoundException {
		if (usuario.isAtivo().equals(Boolean.FALSE)) {
			List<String> emails = new ArrayList<String>();
			emails.add(usuario.getUsername() + Usuario.DEFAULT_DOMAIN);
			List<Grupo> grupos = grupoRepositorio.obtemPorIdIntegrante(usuario.getId());
			List<GoogleGrupoJSON> servicos = new ArrayList<GoogleGrupoJSON>();
			
			for (Grupo grupo : grupos) {
				GrupoJSON grupoJSON = grupo.getGrupoJSON();
				servicos = grupoJSON.getServico();
			}
			
			desativarUsuario(emails, servicos);
			
			grupoRepositorio.ajustarProprietarioGrupo(usuario);
			MembroRepository.removeMembroDosGruposPor(usuario);
		}
	}

	private void desativarUsuario(List<String> emails, List<GoogleGrupoJSON> servicos) {
		for (GoogleGrupoJSON servico : servicos) {
			Aprovisionamento aprovisionamento = new Aprovisionamento();
			try {
				aprovisionamento.doPost("removermembro", servico.getNomeEmailGrupo(), servico.getEmailGrupo(), emails);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}