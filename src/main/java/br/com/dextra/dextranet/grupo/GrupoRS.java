package br.com.dextra.dextranet.grupo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
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

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/grupo")
public class GrupoRS {

	GrupoRepository repositorio = new GrupoRepository();
	MembroRepository repositorioMembro = new MembroRepository();
	ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();
	UsuarioRepository usuarioRepository = new UsuarioRepository();

	private Aprovisionamento aprovisionamento = new Aprovisionamento();

	@GET
	@Path("/{id}")
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Grupo grupo = repositorio.obtemPorId(id);
		List<Membro> membros = repositorioMembro.obtemPorIdGrupo(grupo.getId());
		List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		grupo.setMembros(membros);
		grupo.setServicoGrupos(servicoGrupos);

		return Response.ok().entity(grupo.getGrupoJSON()).build();
	}

	@GET
	@Path("/")
	@Produces(Application.JSON_UTF8)
	public Response listar() throws EntityNotFoundException {
		List<Grupo> grupos = repositorio.lista();
		List<GrupoJSON> gruposRetorno = new ArrayList<GrupoJSON>();
		UsuarioRepository repositorioUsuario = new UsuarioRepository();

		for (Grupo grupo : grupos) {
			List<Membro> membros = repositorioMembro.obtemPorIdGrupo(grupo.getId());
			List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());

			// TODO: faria mais sentido esse codigo estar dentro de algum
			// repositorio
			for (Membro membro : membros) {
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

	@PUT
	@Path("/googlegrupos/")
	@Consumes("application/json")
	@SuppressWarnings("unchecked")
	public Response aprovisionarServicos(List<GoogleGrupoJSON> googleGrupoJSONs) throws IOException, ParseException,
			GeneralSecurityException, URISyntaxException {
		for (GoogleGrupoJSON googleGrupoJSON : googleGrupoJSONs) {
			List<String> emails = new ArrayList<String>();
			for (UsuarioJSON usuarioJSON : googleGrupoJSON.getUsuarioJSONs()) {
				emails.add(usuarioJSON.getEmail());				
			}

			if (googleGrupoJSON.getEmailsExternos() != null) {
				JSONParser jParser = new JSONParser();
				JSONArray emailsExternos =  (JSONArray) jParser.parse(googleGrupoJSON.getEmailsExternos());

				Iterator<String> emailExterno = emailsExternos.iterator();
				while (emailExterno.hasNext()) {
					emails.add(emailExterno.next());
				}
			}

			Group grupo = aprovisionamento.obterGrupo(googleGrupoJSON.getEmailDomainGrupo());
			
			if (grupo == null) {
				grupo = aprovisionamento.criarGrupo(googleGrupoJSON.getEmailGrupo(),
						googleGrupoJSON.getEmailDomainGrupo(), "");
			}

			aprovisionamento.adicionarMembros(emails, grupo);
		}

		return Response.ok().build();
	}

	@PUT
	@Consumes("application/json")
	@Path("/googlegrupos/removerIntegrantes/")
	public Response removerIntegrantes(List<GoogleGrupoJSON> googleGrupoJSONs) throws IOException, ParseException,
			GeneralSecurityException, URISyntaxException {

		for (GoogleGrupoJSON googleGrupoJSON : googleGrupoJSONs) {
			List<String> emails = new ArrayList<String>();
			if (googleGrupoJSON.getUsuarioJSONs() != null) {
				for (UsuarioJSON usuarioJSON : googleGrupoJSON.getUsuarioJSONs()) {
					emails.add(usuarioJSON.getEmail());
				}
			}

			if (googleGrupoJSON.getEmailsExternos() != null) {
				emails.add(googleGrupoJSON.getEmailsExternos());
			}
			Group grupo = aprovisionamento.obterGrupo(googleGrupoJSON.getEmailDomainGrupo());

			aprovisionamento.removerMembros(emails, grupo);
		}

		return Response.ok().build();
	}

	@GET
	@Path("/googlegrupos/listarGrupos")
	@Produces(Application.JSON_UTF8)
	public Response listaTodosGrupos() throws IOException, GeneralSecurityException, URISyntaxException {
		List<Group> groups = aprovisionamento.obterGrupos("dextra-sw.com");

		List<String> emailGrupos = new ArrayList<String>();
		for (Group group : groups) {
			emailGrupos.add(group.getEmail());
		}
		return Response.ok(emailGrupos).build();
	}

	@GET
	@Produces(Application.JSON_UTF8)
	@Path("/googlegrupos/listarMembrosGrupo/{email}")
	public Response listaMembrosGrupo(@PathParam("email") String email) throws IOException, GeneralSecurityException,
			URISyntaxException {
		Group grupo = aprovisionamento.obterGrupo(email);
		List<Member> members = aprovisionamento.obterMembros(grupo);

		List<String> emailsMembros = new ArrayList<String>();

		for (Member member : members) {
			emailsMembros.add(member.getEmail());
		}

		return Response.ok().entity(emailsMembros).build();
	}

	@DELETE
	@Produces(Application.JSON_UTF8)
	@Path("/googlegrupos/grupo/{idGrupo}/servico/{idServicoGrupo}")
	public Response removerServico(@PathParam("idGrupo") String idGrupo,
			@PathParam("idServicoGrupo") String idServicoGrupo) throws EntityNotFoundException, IOException,
			GeneralSecurityException, URISyntaxException {

		String usuarioLogado = obtemUsernameUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(idGrupo);

		if (podeRemoverServico(usuarioLogado, grupo)) {
			ServicoGrupo servico = servicoGrupoRepository.obtemPorId(idServicoGrupo);
			aprovisionamento.removerGrupo(servico.getEmailGrupoDomain());
			servicoGrupoRepository.remove(idServicoGrupo);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	@PUT
	@Path("/")
	@Consumes("application/json")
	public Response adicionar(GrupoJSON grupojson) throws ParseException {
		Grupo grupo = new Grupo(grupojson.getNome(), grupojson.getDescricao(), obtemUsernameUsuarioLogado());
		repositorio.persiste(grupo);

		for (UsuarioJSON usuariojson : grupojson.getUsuarios()) {
			Membro membro = new Membro(usuariojson.getId(), grupo.getId(), usuariojson.getNome(),
					usuariojson.getEmail());
			repositorioMembro.persiste(membro);
		}

		if (grupojson.getServico() != null) {
			for (GoogleGrupoJSON servico : grupojson.getServico()) {
				ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getIdServico(), grupo.getId(),
						servico.getEmailGrupo(),
						servico.getEmailsExternos());
				servicoGrupoRepository.persiste(servicoGrupo);
			}
		}

		return Response.ok().build();
	}

	@PUT
	@Path("/{idGrupo}")
	@Produces(Application.JSON_UTF8)
	public Response atualizar(@PathParam("idGrupo") String id, GrupoJSON grupojson) throws EntityNotFoundException {
		grupojson.setId(id);
		String usernameLogado = obtemUsernameUsuarioLogado();

		String proprietario = usernameLogado;
		Grupo grupo = repositorio.obtemPorId(id);
		if (podeAlterarGrupo(usernameLogado, grupo)) {
			if (proprietarioExcluido(grupojson)) {
				grupo.comProprietario(obterNovoProprietarioGrupo(grupo));
			}

			if (!grupo.getProprietario().equals(usernameLogado)) {
				proprietario = grupo.getProprietario();
			}

			grupo = grupo.preenche(grupojson.getNome(), grupojson.getDescricao(), proprietario);

			repositorio.persiste(grupo);
			adicionaNovosMembros(grupojson.getUsuarios(), grupo.getId());
			persisteServicoGrupo(grupojson, grupo);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(Application.JSON_UTF8)
	public Response remover(@PathParam("id") String id) throws EntityNotFoundException, IOException,
			GeneralSecurityException, URISyntaxException {
		String usuarioLogado = obtemUsernameUsuarioLogado();
		Grupo grupo = repositorio.obtemPorId(id);

		if (podeDeletarGrupo(usuarioLogado, grupo)) {
			List<Membro> obtemPorIdGrupo = repositorioMembro.obtemPorIdGrupo(id);
			for (Membro membro : obtemPorIdGrupo) {
				repositorioMembro.remove(membro.getId());
			}

			List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(id);
			if (!servicoGrupos.isEmpty()) {
				for (ServicoGrupo servico : servicoGrupos) {
					aprovisionamento.removerGrupo(servico.getEmailGrupoDomain());
					servicoGrupoRepository.remove(servico.getId());
				}
			}

			repositorio.remove(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}
	}

	protected String obtemUsernameUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

	protected Usuario obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado(obtemUsernameUsuarioLogado());
	}

	private String obterNovoProprietarioGrupo(Grupo grupo) throws EntityNotFoundException {
		List<Membro> membros = repositorioMembro.obtemPorIdGrupo(grupo.getId());
		for (Membro membro : membros) {
			if (!membro.getEmail().equals(grupo.getProprietario())) {
				return membro.getEmail();
			}
		}
		// TODO: VERIFICAR ESSE PONTO QUANDO NÃO EXISTIR NENHUM MEMBRO
		return null;
	}

	private Boolean proprietarioExcluido(GrupoJSON grupoJson) {
		String proprietarioAtualGrupo = grupoJson.getProprietario();
		Usuario usuario = usuarioRepository.obtemPorUsername(proprietarioAtualGrupo);
		Membro membroProprietario = new Membro(null, grupoJson.getId(), usuario.getNome(), usuario.getUsername());

		if (proprietarioEstavaNoGrupo(grupoJson, membroProprietario)) {
			List<UsuarioJSON> membros = grupoJson.getUsuarios();
			for (UsuarioJSON membro : membros) {
				if (membro.getEmail().equals(membroProprietario.getEmail())) {
					return Boolean.FALSE;
				}
			}

			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	private Boolean proprietarioEstavaNoGrupo(GrupoJSON grupoJson, Membro membroProprietario) {
		Membro proprietario = repositorioMembro.obtemPorUsername(membroProprietario.getEmail(), grupoJson.getId());
		if (proprietario != null) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void persisteServicoGrupo(GrupoJSON grupojson, Grupo grupo) throws EntityNotFoundException {
		if (grupojson.getServico() != null) {
			for (GoogleGrupoJSON servico : grupojson.getServico()) {
				Boolean novoRegistro = true;
				if (servico.getId() != null && servico.getEmailsExternos() != null) {
					ServicoGrupo servicoGrupo = servicoGrupoRepository.obtemPorId(servico.getId());
					servicoGrupo = servicoGrupo.preenche(servico.getEmailsExternos());
					servicoGrupoRepository.persiste(servicoGrupo);
					novoRegistro = false;
				} else if (servico.getEmailsExternos() != null && novoRegistro) {
					ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getIdServico(), grupo.getId(),
							servico.getEmailGrupo(),
							servico.getEmailsExternos());
					servicoGrupoRepository.persiste(servicoGrupo);
				} else {
					ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getIdServico(), grupo.getId(),
							servico.getEmailGrupo());
					servicoGrupoRepository.persiste(servicoGrupo);
				}
			}
		}
	}

	private void adicionaNovosMembros(List<UsuarioJSON> usuarios, String idGrupo) throws EntityNotFoundException {
		List<Membro> membros = repositorioMembro.obtemPorIdGrupo(idGrupo);
		deletaMembros(usuarios, membros);
		adicionarEAtualizarMembros(usuarios, idGrupo);
	}

	private void adicionarEAtualizarMembros(List<UsuarioJSON> usuarios, String idGrupo) {
		// TODO: Verificar uma forma de adicionar somente os necessários
		for (UsuarioJSON usuario : usuarios) {
			Membro membroAtualizar = new Membro(usuario.getId(), idGrupo, usuario.getNome(), usuario.getEmail());
			repositorioMembro.persiste(membroAtualizar);
		}
	}

	private void deletaMembros(List<UsuarioJSON> usuarios, List<Membro> membros) {
		// TODO Verificar uma forma de somente remover os que não existem na
		// tela
		for (Membro membro : membros) {
			repositorioMembro.remove(membro.getId());
		}
	}

	private boolean podeDeletarGrupo(String usuarioLogado, Grupo grupo) {
		return AutenticacaoService.isUsuarioGrupoInfra() || usuarioLogado.equals(grupo.getProprietario());
	}

	private boolean podeRemoverServico(String usuarioLogado, Grupo grupo) {
		return podeDeletarGrupo(usuarioLogado, grupo);
	}

	private boolean podeAlterarGrupo(String usuarioLogado, Grupo grupo) {
		Boolean isMembroGrupo = repositorioMembro.obtemPorUsername(usuarioLogado, grupo.getId()) != null;
		return podeDeletarGrupo(usuarioLogado, grupo) || isMembroGrupo;
	}

}