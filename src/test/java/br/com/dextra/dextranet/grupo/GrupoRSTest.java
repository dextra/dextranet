package br.com.dextra.dextranet.grupo;

import static br.com.dextra.dextranet.persistencia.TesteUtils.adicionarMembroGrupoGoogle;
import static br.com.dextra.dextranet.persistencia.TesteUtils.buscarGrupoGoogle;
import static br.com.dextra.dextranet.persistencia.TesteUtils.buscarUsuario;
import static br.com.dextra.dextranet.persistencia.TesteUtils.criarGrupoComOsIntegrantes;
import static br.com.dextra.dextranet.persistencia.TesteUtils.criarGrupoGoogle;
import static br.com.dextra.dextranet.persistencia.TesteUtils.criarUsuario;
import static br.com.dextra.dextranet.persistencia.TesteUtils.getAprovisionamento;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.grupo.servico.ServicoRepository;
import br.com.dextra.dextranet.grupo.servico.google.Aprovisionamento;
import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.persistencia.TesteUtils;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoRSTest extends TesteIntegracaoBase {
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	private UsuarioRepository usuarioRepository = new UsuarioRepository();
	private GrupoRS grupoRS = new GrupoRSFake();
	private ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();
	private ServicoRepository servicoRepository = new ServicoRepository();
	private Aprovisionamento aprovisionamento = getAprovisionamento();

	private static String USUARIO_LOGADO = "login.google";
	private String emailGrupo = "grupo@dextra-sw.com";
	private String nomeEmailGrupo = "grupo";

	@After
	public void removerDadosInseridos() throws IOException, GeneralSecurityException, URISyntaxException {
		this.limpaGrupoInseridos(repositorioGrupo);
		this.limpaMembroInseridos(repositorioMembro);
		this.limpaServicoGrupo(servicoGrupoRepository);
		this.limpaServico(servicoRepository);
		getAprovisionamento().removerGrupo(emailGrupo);
	}

	@Test
	public void testaAdicionarGrupo() throws EntityNotFoundException, ParseException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");

		usuario = usuarioRepository.persiste(usuario);
		UsuarioJSON uMembro = new UsuarioJSON(usuario.getId(), usuario.getNome(), email);
		List<GoogleGrupoJSON> googleGrupoJSON = null;

		List<UsuarioJSON> uMembros = new ArrayList<UsuarioJSON>();
		uMembros.add(uMembro);
		GrupoJSON grupojson = new GrupoJSON(null, nome, descricao, uMembros, googleGrupoJSON);

		Response response = grupoRS.adicionar(grupojson);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	public void testaUsuarioInfraAdicionarNovoMembroAoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criarUsuario(USUARIO_LOGADO, true);
		criarGrupoComOsIntegrantes("grupoinfra", true, "Grupo Infra", true, usuario);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1);

		GrupoJSON grupoJSON = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));

		grupoRS.atualizar(grupo.getId(), grupoJSON);

		Response grupoResponse = grupoRS.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(2, grupoAtualizado.getUsuarios().size());
		assertEquals(usuario1.getUsername(), grupoAtualizado.getProprietario());
	}

	@Test
	public void testaMembroDoGrupoAdicionarNovoMembroAoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario);

		GrupoJSON grupoJSON = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario2", "usuario2@dextra-sw.com"));
		grupoRS.atualizar(grupo.getId(), grupoJSON);
		Response grupoResponse = grupoRS.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(3, grupoAtualizado.getUsuarios().size());
		assertEquals(usuario1.getUsername(), grupoAtualizado.getProprietario());
	}

	@Test
	public void testaListaTodosGrupos() throws IOException, GeneralSecurityException, URISyntaxException {
		Response response = grupoRS.listaTodosGrupos();
		@SuppressWarnings("unchecked")
        List<String> emailsGrupos = (List<String>) response.getEntity();
		assertTrue(emailsGrupos.size() > 0);
		assertFalse(emailsContains(emailsGrupos, emailGrupo));
	}

	@Test
	public void testaListaMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		String email1 = "username1@dextra-sw.com";
		String email2 = "username2@dextra-sw.com";
		String email3 = "username3@dextra-sw.com";
		TesteUtils.criarGrupoGoogle(emailGrupo);
		getAprovisionamento().obterGrupo(emailGrupo).eAdicionarMembros(Arrays.asList(email1, email2, email3));
		Response response = grupoRS.listaMembrosGrupo(emailGrupo);
		@SuppressWarnings("unchecked")
        List<String> membros = (List<String>) response.getEntity();
		
		assertTrue(emailsContains(membros, email1));
		assertTrue(emailsContains(membros, email2));
		assertTrue(emailsContains(membros, email3));
		assertFalse(emailsContains(membros, "teste@dextra-sw.com.br"));
	}

	@Test
	public void testaRemoverMembroProprietarioPeloUsuarioInfra() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criarUsuario("Usuario 1", true);
		Usuario usuario2 = criarUsuario("Usuario 2", true);
		Usuario proprietario = criarUsuario("Usuario 3", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, proprietario, usuario2, usuario1);

		Usuario usuarioInfra = criarUsuario(USUARIO_LOGADO, true);
		criarGrupoComOsIntegrantes("grupoinfra", true, "Grupo Infra", true, usuarioInfra);

		GrupoJSON grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(3, grupojson.getUsuarios().size());

		grupojson = removerMembrodoGrupo(proprietario, grupo);

		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();

		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());
		assertFalse(grupojson.getProprietario().equals(proprietario.getUsername()));
	}

	@Test
	public void testaNaoPodeAlterarGrupoInfra() throws EntityNotFoundException {
		Usuario usuario = criarUsuario("usuarioX", true);
		criarGrupoComOsIntegrantes("grupox", true, "Grupo X", true, usuario);

		Usuario usuario1 = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1);
		GrupoJSON grupoJSON = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));

		grupoRS.atualizar(grupo.getId(), grupoJSON);

		Response grupoResponse = grupoRS.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(1, grupoAtualizado.getUsuarios().size());
	}

	@Test
	public void testaAlterarGrupo() throws EntityNotFoundException {
		if (usuarioRepository.obtemPorUsername(USUARIO_LOGADO) == null) {
			criarUsuario(USUARIO_LOGADO, true);
		}
		
		String nome = "Grupo A";
		String nomeAlterado = "Grupo alterado";
		String descricao = "Grupo teste";
		String descricaoAlterada = "Grupo teste alterado";
		String nomeMembro = "JoaoDextrano";
		String nomeMembroAlterado = "JoseDextrano";
		String email = "teste@dextra-sw.com";
		
		Usuario usuario = new Usuario(nomeMembro);
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRS.obtemUsernameUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(), usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado, email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		grupoRS.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = grupoRS.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nomeAlterado);
		assertEquals(grupojson.getDescricao(), descricaoAlterada);
		assertEquals(grupojson.getUsuarios().get(0).getNome(), nomeMembroAlterado);
	}

	@Test
	public void testaNaoPodeRemoverMembroGrupoPorMembrodeOutroGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criarUsuario("Usuario 1", true);
		Usuario usuario2 = criarUsuario("Usuario 2", true);
		Usuario proprietario = criarUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, proprietario, usuario2);
		criarGrupoComOsIntegrantes("outrogrupo", false, "OutroGrupo", true, usuario1);

		USUARIO_LOGADO = usuario1.getUsername();
		GrupoJSON grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(2, grupojson.getUsuarios().size());

		List<UsuarioJSON> usuarios = grupojson.getUsuarios();
		UsuarioJSON usuarioExcluir = null;
		for (UsuarioJSON usuarioJSON : usuarios) {
			if (usuarioJSON.getId().equals(usuario2.getId())) {
				usuarioExcluir = usuarioJSON;
			}
		}
		usuarios.remove(usuarioExcluir);
		grupojson.setUsuarios(usuarios);

		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());

		USUARIO_LOGADO = "login.google";
	}

	@Test
	public void testaRemoverProprietarioGrupoPorOutroMembrodoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario proprietario = criarUsuario("Usuario 1", true);
		Usuario usuario2 = criarUsuario("Usuario 2", true);
		Usuario usuario1 = criarUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, proprietario, usuario1, usuario2);
		int qtdeMembrosAntesDaAtualizacao = ((GrupoJSON) grupoRS.obter(grupo.getId()).getEntity()).getUsuarios().size();

		GrupoJSON grupojson = removerMembrodoGrupo(proprietario, grupo);
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();

		assertEquals(3, qtdeMembrosAntesDaAtualizacao);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());
		assertFalse(grupojson.getProprietario().equals(proprietario.getUsername()));
	}

	@Test
	public void testaRemoverMembroGrupoPorOutroMembrodoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criarUsuario("Usuario 1", true);
		Usuario usuario2 = criarUsuario("Usuario 2", true);
		Usuario proprietario = criarUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", false, proprietario, usuario1, usuario2);

		USUARIO_LOGADO = usuario1.getUsername();
		GrupoJSON grupojson = removerMembrodoGrupo(usuario2, grupo);
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);

		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(1, grupojson.getUsuarios().size());
		assertEquals(proprietario.getUsername(), grupojson.getProprietario());

		USUARIO_LOGADO = "login.google";
	}

	@Test
	public void testaRemoverMembroGrupoPorUsuarioInfra() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criarUsuario("Usuario 1", true);
		Usuario usuario2 = criarUsuario("Usuario 2", true);
		Usuario proprietario = criarUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, usuario1, usuario2);
		criarGrupoComOsIntegrantes("grupo2", true, "Grupo2", true, proprietario);

		GrupoJSON grupojson = removerMembrodoGrupo(usuario2, grupo);
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(1, grupojson.getUsuarios().size());
	}

	@Test
	public void testaObterGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRS.obtemUsernameUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(), usuario.getNome(), email));
		Response response = grupoRS.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nome);
		assertEquals(grupojson.getDescricao(), descricao);
		assertEquals(grupojson.getUsuarios().get(0).getNome(), usuario.getNome());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testaListar() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		String nome = "Grupo A";
		Usuario usuario = criarUsuario(USUARIO_LOGADO, true);
		criarGrupoComOsIntegrantes("grupoa", false, "Grupo A", true, usuario);

		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		for (GrupoJSON grupojson : gruposjson) {
			assertEquals(grupojson.getNome(), nome);
			assertEquals(grupojson.getUsuarios().get(0).getNome(), usuario.getNome());
		}
	}

	@Test
	public void testaNaoPodeRemoverGrupoPorMembrodoGrupoNaoProprietario() throws EntityNotFoundException, IOException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario membro = criarUsuario(USUARIO_LOGADO, true);
		Usuario proprietario = criarUsuario("proprietario", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, proprietario, membro);

		Response response = grupoRS.remover(grupo.getId());

		assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
		
	}

	@Test
	public void testaRemoverGrupoInexistente() throws EntityNotFoundException, IOException, GeneralSecurityException, URISyntaxException {
		String nomeGrupo = "Grupo A";
		Usuario usuario = buscarUsuario(USUARIO_LOGADO);
		Grupo grupo = criarGrupoComOsIntegrantes("grupoinexistente", false, nomeGrupo, true, usuario);
		grupoRS.remover(grupo.getId());
		try {
			repositorioGrupo.obtemPorId(grupo.getId());
		} catch (EntityNotFoundException e) {
			assertTrue(true);
		}
		
		assertTrue(buscarGrupoGoogle(emailGrupo) == null);
	}
	
	@Test
	public void testaRemoverGrupo() throws EntityNotFoundException, IOException, GeneralSecurityException, URISyntaxException {
		String nomeGrupo = "Grupo A";
		Usuario usuario = buscarUsuario(USUARIO_LOGADO);
		Grupo grupo = criarGrupoComOsIntegrantes(nomeEmailGrupo, false, nomeGrupo, true, usuario);
		criarGrupoGoogle(emailGrupo);
		grupoRS.remover(grupo.getId());
		
		try {
			repositorioGrupo.obtemPorId(grupo.getId());
		} catch (EntityNotFoundException e) {
			assertTrue(true);
		}
		
		assertTrue(buscarGrupoGoogle(emailGrupo) == null);
	}

	@Test
	public void testaRemoverServico() throws EntityNotFoundException, IOException, GeneralSecurityException, URISyntaxException {
		Usuario usuario = buscarUsuario(USUARIO_LOGADO);
		Grupo grupo = criarGrupoComOsIntegrantes(nomeEmailGrupo, false, "Grupo 1", true, usuario);
		List<ServicoGrupo> servicosGrupo = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		getAprovisionamento().criarGrupo("Grupo 1", emailGrupo, "")
							.eAdicionarMembros(Arrays.asList(USUARIO_LOGADO + "@dextra-sw.com"));
		grupoRS.removerServico(grupo.getId(), servicosGrupo.get(0).getId());
		
		List<ServicoGrupo> servicosGrupo1 = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		Group group = buscarGrupoGoogle(emailGrupo);
		
		assertTrue(servicosGrupo1.size() == 0);
		assertTrue(group == null);
	}
	
	@Test
	public void testaListagemDeGrupoComIntegranteRemovido() throws EntityNotFoundException {
		Usuario lulao = criarUsuario("lulao", true);
		Usuario dudi = criarUsuario(USUARIO_LOGADO, true);
		criarGrupoComOsIntegrantes("vamobrugrao", false, "VamoBugrao", true, dudi, lulao);

		this.removeUmDosUsuariosDoGrupo(lulao);

		assertEquals(200, grupoRS.listar().getStatus());
	}

	@Test
	public void testaUsuarioLogadoNaoPodeEditarGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario1);

		Response response = grupoRS.obter(grupo.getId());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(false, grupojson.isEditarGrupo().booleanValue());
	}

	@Test
	public void testaUsuarioLogadoPodeEditarGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuarioMembro = criarUsuario("membroInfra", true);
		criarGrupoComOsIntegrantes("grupoinfra", true, "Grupo Infra", true, usuarioMembro, usuario);

		Usuario usuario1 = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes(""
				+ ""
				+ "grupo1", false, "Grupo 1", true, usuario1, usuario1);
		
		Response response = grupoRS.obter(grupo.getId());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();

		assertEquals(true, grupojson.isEditarGrupo().booleanValue());
	}

	@Test
	public void testaMembrodoGrupoTemAcessoParaEditarGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario proprietario = criarUsuario("usuario", true);
		Usuario membro = criarUsuario(USUARIO_LOGADO, true);

		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, proprietario, membro);

		Response response = grupoRS.obter(grupo.getId());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(Boolean.TRUE, grupojson.isEditarGrupo());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testaMembroNaoPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario membro = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, membro);

		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		assertEquals(false, gruposjson.get(0).isExcluirGrupo().booleanValue());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoNaoPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		criarGrupoComOsIntegrantes("Grupo1", false, "Grupo 1", true, usuario1, usuario1);

		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		assertEquals(false, gruposjson.get(0).isExcluirGrupo().booleanValue());
	}

	@Test
	public void testaUsuarioLogadoInfraPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuarioMembroInfra = criarUsuario("membroInfra", true);
		criarGrupoComOsIntegrantes("GrupoInfra", true, "Grupo Infra", true, usuarioMembroInfra, usuario);

		Usuario usuario1 = criarUsuario("usuario1", true);
		criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario1);

		Response response = grupoRS.listar();
		@SuppressWarnings("unchecked")
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();

		assertEquals(true, gruposjson.get(0).isExcluirGrupo().booleanValue());
	}

	@Test
	public void testaAprovisionarServicos() throws IOException, ParseException, GeneralSecurityException, URISyntaxException,
	        EntityNotFoundException {
		Usuario usuario = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes(nomeEmailGrupo, false, "Grupo", true, usuario);
		List<ServicoGrupo> servicos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());

		GoogleGrupoJSON googleGrupojson = new GoogleGrupoJSON();
		googleGrupojson.setEmailGrupo(nomeEmailGrupo);
		googleGrupojson.setId(grupo.getId());
		googleGrupojson.setIdServico(servicos.get(0).getIdServico());
		UsuarioJSON usuariojson = new UsuarioJSON();
		usuariojson.setEmail("usuario.1@dextra-sw.com");
		usuariojson.setAtivo(true);
		usuariojson.setApelido("Usuario1");
		usuariojson.setUsername("usuario.1");
		usuariojson.setNome("Usuario1");
		googleGrupojson.setUsuarioJSONs(Arrays.asList(usuariojson));

		Response response = grupoRS.aprovisionarServicos(Arrays.asList(googleGrupojson));
		assertEquals(200, response.getStatus());
		Group group = aprovisionamento.googleAPI().directory().getGroup(emailGrupo);
		List<Member> members = aprovisionamento.googleAPI().directory().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 1);
	}

	@Test
	public void testaRemoverIntegrantes() throws IOException, ParseException, GeneralSecurityException, URISyntaxException,
	        EntityNotFoundException {
		String emailUsuario1 = "usuario.1@dextra-sw.com";
		String emailUsuario2 = "usuario.2@dextra-sw.com";
		Group group = criarGrupoGoogle(emailGrupo);
		List<String> emailMembros = Arrays.asList(emailUsuario1, emailUsuario2);
		adicionarMembroGrupoGoogle(emailMembros, emailGrupo);
		aprovisionamento.googleAPI().directory().getMembersGroup(group).getMembers();

		Usuario usuario = criarUsuario("Usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes(nomeEmailGrupo, false, "Grupo", true, usuario);
		List<ServicoGrupo> servicos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());

		GoogleGrupoJSON googleGrupojson = new GoogleGrupoJSON();
		googleGrupojson.setEmailGrupo(nomeEmailGrupo);
		googleGrupojson.setId(grupo.getId());
		googleGrupojson.setIdServico(servicos.get(0).getIdServico());
		UsuarioJSON usuariojson = new UsuarioJSON();
		usuariojson.setEmail("usuario.1@dextra-sw.com");
		usuariojson.setAtivo(true);
		usuariojson.setApelido("Usuario1");
		usuariojson.setUsername("usuario.1");
		usuariojson.setNome("Usuario1");
		googleGrupojson.setUsuarioJSONs(Arrays.asList(usuariojson));

		grupoRS.removerIntegrantes(Arrays.asList(googleGrupojson));

		List<Member> members = aprovisionamento.googleAPI().directory().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 1);
		assertTrue(membrosContains(members, emailUsuario2));
	}

	private GrupoJSON removerMembrodoGrupo(Usuario membro, Grupo grupo) throws EntityNotFoundException {
		GrupoJSON grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();

		List<UsuarioJSON> usuarios = grupojson.getUsuarios();
		UsuarioJSON usuarioExcluir = null;
		for (UsuarioJSON usuarioJSON : usuarios) {
			if (usuarioJSON.getId().equals(membro.getId())) {
				usuarioExcluir = usuarioJSON;
			}
		}
		usuarios.remove(usuarioExcluir);
		grupojson.setUsuarios(usuarios);
		return grupojson;
	}

	private void removeUmDosUsuariosDoGrupo(Usuario usuario) {
		usuarioRepository.remove(usuario.getId());
	}

	private Boolean membrosContains(List<Member> membros, String email) {
		for (Member member : membros) {
			if (member.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	private Boolean emailsContains(List<String> emails, String email) {
		for (String e : emails) {
			if (e.equals(email)) {
				return true;
			}
		}
		return false;
	}
	
	public class GrupoRSFake extends GrupoRS {
		@Override
		protected String obtemUsernameUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}
}
