package br.com.dextra.dextranet.grupo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import br.com.dextra.dextranet.grupo.servico.google.Aprovisionamento;
import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/grupo")
public class GrupoRS {
	GrupoRepository repositorio = new GrupoRepository();
	MembroRepository repositorioMembro = new MembroRepository();
	ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Grupo grupo = repositorio.obtemPorId(id);
		List<Membro> membros = repositorioMembro.obtemPorIdGrupo(grupo.getId());
		List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		grupo.setMembros(membros);
		grupo.setServicoGrupos(servicoGrupos);

		return Response.ok().entity(grupo.getGrupoJSON()).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() throws EntityNotFoundException {
		List<Grupo> grupos = repositorio.lista();
		List<GrupoJSON> gruposRetorno = new ArrayList<GrupoJSON>();
		UsuarioRepository repositorioUsuario = new UsuarioRepository();

		for (Grupo grupo : grupos) {
			List<Membro> membros = repositorioMembro.obtemPorIdGrupo(grupo.getId());
			List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());

			// TODO: faria mais sentido esse codigo estar dentro de algum repositorio
			for (Membro membro : membros){
				try {
					Usuario integranteDoGrupo = repositorioUsuario.obtemPorId(membro.getIdUsuario());
					membro.setNomeUsuario(integranteDoGrupo.getNome());
				} catch (EntityNotFoundException e) {
					repositorioMembro.remove(membro.getId());
				}
			}

			grupo.setMembros(membros);
			grupo.setServicoGrupos(servicoGrupos);

			gruposRetorno.add(grupo.getGrupoJSON());
		}

		return Response.ok().entity(gruposRetorno).build();
	}

	@SuppressWarnings("unchecked")
	@Path("/googlegrupos/")
	@PUT
	@Consumes("application/json")
	public Response aprovisionarServicos(List<GoogleGrupoJSON> googleGrupoJSONs) throws IOException, ParseException{
		for (GoogleGrupoJSON googleGrupoJSON : googleGrupoJSONs) {
			List<String> emails = new ArrayList<String>();
			for (UsuarioJSON usuarioJSON : googleGrupoJSON.getUsuarioJSONs()) {
				emails.add(usuarioJSON.getEmail());
			}

			if(googleGrupoJSON.getEmailsExternos() != null){
				JSONParser jParser = new JSONParser();
				Object emailsExternos = jParser.parse(googleGrupoJSON.getEmailsExternos());

				JSONArray arrayEmailsExternos = (JSONArray) emailsExternos;

				Iterator<String> emailExterno = arrayEmailsExternos.iterator();
				while (emailExterno.hasNext()) {
					emails.add(emailExterno.next());
				}
			}

			Aprovisionamento aprovisionamento = new Aprovisionamento();
			aprovisionamento.doPost("adicionarmembro", googleGrupoJSON.getEmailGrupo(),
								    googleGrupoJSON.getEmailGrupo(),
									emails);
		}

		return Response.ok().build();
	}

	@Path("/googlegrupos/removerIntegrantes/")
	@PUT
	@Consumes("application/json")
	public Response removerIntegrantes(List<GoogleGrupoJSON> googleGrupoJSONs) throws IOException, ParseException{
		for (GoogleGrupoJSON googleGrupoJSON : googleGrupoJSONs) {
			List<String> emails = new ArrayList<String>();
			if(googleGrupoJSON.getUsuarioJSONs() != null){
				for (UsuarioJSON usuarioJSON : googleGrupoJSON.getUsuarioJSONs()) {
					emails.add(usuarioJSON.getEmail());
				}
			}

			if(googleGrupoJSON.getEmailsExternos() != null){
				emails.add(googleGrupoJSON.getEmailsExternos());
			}

			Aprovisionamento aprovisionamento = new Aprovisionamento();
			aprovisionamento.doPost("removermembro", googleGrupoJSON.getEmailGrupo(),
								    googleGrupoJSON.getEmailGrupo(),
									emails);
		}

		return Response.ok().build();
	}

	@Path("/googlegrupos/listarGrupos/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listaTodosGrupos() throws IOException{
		return Response.ok(new Aprovisionamento().doGet("listargrupos", "")).build();
	}

	@Path("/googlegrupos/listarMembrosGrupo/{email}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listaMembrosGrupo(@PathParam("email") String email) throws IOException{
		return Response.ok(new Aprovisionamento().doGet("listarMembrosGrupo", email)).build();
	}

	@Path("/googlegrupos/grupo/{idGrupo}/servico/{idServicoGrupo}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response removerServico(@PathParam("idGrupo") String idGrupo, @PathParam("idServicoGrupo") String idServicoGrupo) throws EntityNotFoundException, IOException {
		String usuarioLogado = obtemUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(idGrupo);
		if (usuarioLogado.equals(grupo.getProprietario())) {
			ServicoGrupo servico = servicoGrupoRepository.obtemPorId(idServicoGrupo);

			Aprovisionamento aprovisionamento = new Aprovisionamento();
			aprovisionamento.doPost("removergrupo", "", servico.getEmailGrupo(), null);

			servicoGrupoRepository.remove(idServicoGrupo);

			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	@Path("/")
	@PUT
	@Consumes("application/json")
	public Response adicionar(GrupoJSON grupojson) throws ParseException{
		Grupo grupo = new Grupo(grupojson.getNome(), grupojson.getDescricao(), obtemUsuarioLogado());
		repositorio.persiste(grupo);

		for (UsuarioJSON usuariojson : grupojson.getUsuarios()) {
			Membro membro = new Membro(usuariojson.getId(), grupo.getId(), usuariojson.getNome(), usuariojson.getEmail());
			repositorioMembro.persiste(membro);
		}

		if(grupojson.getServico() != null){
			for(GoogleGrupoJSON servico : grupojson.getServico()){
				ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getIdServico(), grupo.getId(), servico.getEmailGrupo(), servico.getEmailsExternos());
				servicoGrupoRepository.persiste(servicoGrupo);
			}
		}

		return Response.ok().build();
	}

	@Path("/{idGrupo}")
	@PUT
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("idGrupo") String id, GrupoJSON grupojson) throws EntityNotFoundException {
		String usuarioLogado = obtemUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(id);
		if (usuarioLogado.equals(grupo.getProprietario())) {
			grupo = grupo.preenche(grupojson.getNome(), grupojson.getDescricao(), usuarioLogado);
			repositorio.persiste(grupo);
			adicionaNovosMembros(grupojson.getUsuarios(), grupo.getId());

			if(grupojson.getServico() != null){
				for(GoogleGrupoJSON servico : grupojson.getServico()){
					Boolean novoRegistro = true;
					if(servico.getId() != null && servico.getEmailsExternos() != null){
						ServicoGrupo servicoGrupo = servicoGrupoRepository.obtemPorId(servico.getId());
						servicoGrupo = servicoGrupo.preenche(servico.getEmailsExternos());
						servicoGrupoRepository.persiste(servicoGrupo);
						novoRegistro = false;
					}else if(servico.getEmailsExternos() != null && novoRegistro){
						ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getIdServico(), grupo.getId(), servico.getEmailGrupo(), servico.getEmailsExternos());
						servicoGrupoRepository.persiste(servicoGrupo);
					}else{
						ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getIdServico(), grupo.getId(), servico.getEmailGrupo());
						servicoGrupoRepository.persiste(servicoGrupo);
					}
				}
			}

			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletar(@PathParam("id") String id) throws EntityNotFoundException, IOException {
		String usuarioLogado = obtemUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(id);

		if (usuarioLogado.equals(grupo.getProprietario())) {
			List<Membro> obtemPorIdGrupo = repositorioMembro.obtemPorIdGrupo(id);
			for (Membro membro : obtemPorIdGrupo) {
				repositorioMembro.remove(membro.getId());
			}

			List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(id);
			if(!servicoGrupos.isEmpty()){
				Aprovisionamento aprovisionamento = new Aprovisionamento();
				for(ServicoGrupo servico : servicoGrupos){
					aprovisionamento.doPost("removergrupo", "", servico.getEmailGrupo(), null);
					servicoGrupoRepository.remove(servico.getId());
				}
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
		List<Membro> membros = repositorioMembro.obtemPorIdGrupo(idGrupo);
		deletaMembros(usuarios, membros);
		adicionarEAtualizarMembros(usuarios, idGrupo);
	}

	private void adicionarEAtualizarMembros(List<UsuarioJSON> usuarios, String idGrupo) {
		//TODO: Adicionar somente os necessários
		for (UsuarioJSON usuario : usuarios) {
			Membro membroAtualizar = new Membro(usuario.getId(), idGrupo, usuario.getNome(), usuario.getEmail());
			repositorioMembro.persiste(membroAtualizar);
		}
	}

	private void deletaMembros(List<UsuarioJSON> usuarios, List<Membro> membros) {
		//TODO Remover somente os que não existem na tela
		for (Membro membro : membros) {
			repositorioMembro.remove(membro.getId());
		}
	}
}