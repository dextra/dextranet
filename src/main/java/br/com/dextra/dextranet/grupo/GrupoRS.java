package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

@Path("/grupo")
public class GrupoRS {
	GrupoRepository repositorio = new GrupoRepository();
	MembroRepository repositorioMembro = new MembroRepository();

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() throws EntityNotFoundException {
		List<Grupo> grupos = repositorio.lista();
		List<GrupoJSON> gruposRetorno = new ArrayList<GrupoJSON>();
		for (Grupo grupo : grupos) {
			List<Membro> membros = repositorioMembro.obtemPorIdGrupo(grupo.getId());
			List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
			for (Membro membro : membros) {
				UsuarioJSON usuariojson = new UsuarioJSON(membro.getId(), membro.getNomeUsuario());
				usuariosjson.add(usuariojson);
			}
			GrupoJSON grupojson = new GrupoJSON(grupo.getNome(), grupo.getDescricao(), usuariosjson);
			gruposRetorno.add(grupojson);
		}

		return Response.ok().entity(gruposRetorno).build();
	}

	@Path("/")
	@PUT
	@Consumes(Application.JSON_UTF8)
	public Response adicionar(GrupoJSON grupojson) {
		Grupo grupo = new Grupo(grupojson.getNome(), grupojson.getDescricao(), obtemUsuarioLogado());
		repositorio.persiste(grupo);

		for (UsuarioJSON usuariojson : grupojson.getUsuarios()) {
			Membro membro = new Membro(usuariojson.getId(), grupo.getId());
			repositorioMembro.persiste(membro);
		}

		return Response.ok().build();
	}

	@Path("/{id}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(GrupoJSON grupojson) throws EntityNotFoundException {
		String usuarioLogado = obtemUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(grupojson.getId());
		if (usuarioLogado.equals(grupo.getProprietario())) {
			grupo = grupo.preenche(grupojson.getNome(), grupojson.getDescricao(), usuarioLogado);
			repositorio.persiste(grupo);
			adicionaNovosMembros(grupojson.getUsuarios(), grupo.getId());
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletar(@PathParam("id") String id) throws EntityNotFoundException {
		String usuarioLogado = obtemUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(id);

		if (usuarioLogado.equals(grupo.getProprietario())) {
			List<Membro> obtemPorIdGrupo = repositorioMembro.obtemPorIdGrupo(id);
			for (Membro membro : obtemPorIdGrupo) {
				repositorioMembro.remove(membro.getId());
			}

			repositorio.remove(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

	private void adicionaNovosMembros(List<UsuarioJSON> usuarios, String idGrupo) throws EntityNotFoundException {
		for (UsuarioJSON usuario : usuarios) {
			Membro membroNovo;
			List<Membro> membros = repositorioMembro.obtemPorIdGrupo(idGrupo);
			for (Membro membro : membros) {
				if (!membro.getId().equals(usuario)) {
					membroNovo = new Membro(usuario.getId(), idGrupo);
					repositorioMembro.persiste(membroNovo);
				}
			}
		}
	}
}