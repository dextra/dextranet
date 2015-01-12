package br.com.dextra.dextranet.usuario;

import static br.com.dextra.dextranet.persistencia.TesteUtils.criarGrupoComOsIntegrantes;
import static br.com.dextra.dextranet.persistencia.TesteUtils.criarUsuario;
import static br.com.dextra.dextranet.persistencia.TesteUtils.getAprovisionamento;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.ServicoGrupo;
import br.com.dextra.dextranet.grupo.ServicoGrupoRepository;
import br.com.dextra.dextranet.grupo.UsuarioJSON;
import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.persistencia.TesteUtils;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class UsuarioRSTest extends TesteIntegracaoBase {
	private static final String USUARIO_LOGADO = "login.google";
	private UsuarioRS usuarioRS = new UsuarioRSFake();
	private UsuarioRepository repositorio = new UsuarioRepository();
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	private ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();

	private String emailGrupo = "grupo@dextra-sw.com";
	private String nomeGrupo = "grupo";

	@After
	public void removerDadosInseridos() throws IOException, GeneralSecurityException, URISyntaxException {
		getAprovisionamento().removerGrupo(emailGrupo);
	}

	@Test
	public void testaAtualizacaoPermitida() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuario = new Usuario(USUARIO_LOGADO);
		repositorio.persiste(usuario);

		Usuario usuarioPersistido = repositorio.obtemPorId(usuario.getId());
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getNome()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getApelido()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getArea()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getUnidade()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getRamal()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getTelefoneResidencial()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getTelefoneCelular()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getGitHub()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getSkype()));
		assertTrue(StringUtils.isEmpty(usuarioPersistido.getBlog()));

		usuarioRS.atualizar(usuario.getId(), "Nome", "Apelido", "&Aacute;rea", "Unidade", "Ramal", "Residencial", "Celular",
		        "GitHub", "Skype", "blog", null);
		usuarioPersistido = repositorio.obtemPorId(usuario.getId());

		assertEquals("Nome", usuarioPersistido.getNome());
		assertEquals("Apelido", usuarioPersistido.getApelido());
		assertEquals("&Aacute;rea", usuarioPersistido.getArea());
		assertEquals("Unidade", usuarioPersistido.getUnidade());
		assertEquals("Ramal", usuarioPersistido.getRamal());
		assertEquals("Residencial", usuarioPersistido.getTelefoneResidencial());
		assertEquals("Celular", usuarioPersistido.getTelefoneCelular());
		assertEquals("GitHub", usuarioPersistido.getGitHub());
		assertEquals("Skype", usuarioPersistido.getSkype());
		assertEquals("blog", usuarioPersistido.getBlog());

		usuarioRS.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal", "Residencial", "Celular", "GitHub",
		        "Skype", "blog", null);
		usuarioPersistido = repositorio.obtemPorId(usuario.getId());
		assertEquals("Area", usuarioPersistido.getArea());
	}

	@Test
	public void testaAtualizacaoNaoPermitida() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuario = TesteUtils.criarUsuario("usuario1", true);
		TesteUtils.criarUsuario(USUARIO_LOGADO, true);

		Response resposta = usuarioRS.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal", "Residencial",
		        "Celular", "GitHub", "Skype", "blog", null);
		assertEquals(Status.FORBIDDEN.getStatusCode(), resposta.getStatus());
	}

	@Test
	public void testaDesativacaoUsuarioProprietarioGrupo() throws EntityNotFoundException, GeneralSecurityException,
	        URISyntaxException, IOException {
		limpaUsuariosInseridos(repositorio);
		String username1 = "usuario.1";
		String username2 = "usuario.2";

		Usuario usuarioLogado = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario(username1, true);
		Usuario usuario2 = criarUsuario(username2, true);

		criarGrupoComOsIntegrantes("infra", true, "Infra", true, usuarioLogado);
		Grupo grupo = criarGrupoComOsIntegrantes(nomeGrupo, false, nomeGrupo, true, usuario2, usuario1);

		getAprovisionamento().criarGrupo(nomeGrupo, emailGrupo, "").eAdicionarMembros(
		        Arrays.asList(usuario1.getEmail(), usuario2.getEmail()));
		UsuarioJSON json = (UsuarioJSON) usuarioRS.atualizar(usuario2.getId(), null, null, null, null, null, null, null, null,
		        null, null, false).getEntity();

		Usuario retorno = repositorio.obtemPorId(usuario2.getId());
		List<Grupo> grupos = repositorioGrupo.obtemPorIdIntegrante(usuario2.getId());
		List<Membro> membros = repositorioMembro.obtemPorIdUsuario(usuario2.getId());
		grupo = repositorioGrupo.obtemPorId(grupo.getId());

		assertTrue(!grupo.getProprietario().equals(usuario2.getUsername()));
		assertEquals(grupo.getProprietario(), usuario1.getUsername());
		assertTrue(membros.isEmpty());
		assertTrue(grupos.isEmpty());
		assertFalse(json.isAtivo());
		assertFalse(retorno.isAtivo());
	}

	@Test
	public void testaDesativacaoUsuario() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException,
	        IOException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuarioLogado = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Usuario usuario2 = criarUsuario("usuario2", true);
		criarGrupoComOsIntegrantes("infra", true, "Infra", true, usuarioLogado);
		criarGrupoComOsIntegrantes(nomeGrupo, false, nomeGrupo, true, usuario2, usuario1);

		getAprovisionamento().criarGrupo(nomeGrupo, emailGrupo, "").eAdicionarMembros(
		        Arrays.asList(usuario1.getEmail(), usuario2.getEmail()));
		UsuarioJSON json = (UsuarioJSON) usuarioRS.atualizar(usuario1.getId(), null, null, null, null, null, null, null, null,
		        null, null, false).getEntity();

		Usuario retorno = repositorio.obtemPorId(usuario1.getId());
		List<Grupo> grupos = repositorioGrupo.obtemPorIdIntegrante(usuario1.getId());
		List<Membro> membros = repositorioMembro.obtemPorIdUsuario(usuario1.getId());

		assertTrue(membros.isEmpty());
		assertTrue(grupos.isEmpty());
		assertFalse(json.isAtivo());
		assertFalse(retorno.isAtivo());
	}

	@Test
	public void testaAtivacaoUsuario() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuarioLogado = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Usuario usuario2 = criarUsuario("usuario2", true);
		criarGrupoComOsIntegrantes("infra", true, "Infra", true, usuarioLogado);
		criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, usuario2, usuario1);

		Object entity = usuarioRS.atualizar(usuario1.getId(), null, null, null, null, null, null, null, null, null, null, true)
		        .getEntity();

		UsuarioJSON json = (UsuarioJSON) entity;
		Usuario retorno = repositorio.obtemPorId(usuario1.getId());

		assertTrue(json.isAtivo());
		assertTrue(retorno.isAtivo());
	}

	@Test
	public void testaRemoverUsuarioGrupo() throws GeneralSecurityException, URISyntaxException, EntityNotFoundException,
	        IOException {
		String username1 = "usuario.1";
		String username2 = "usuario.2";
		Usuario usuario1 = criarUsuario(username1, true);
		Usuario usuario2 = criarUsuario(username2, true);
		Grupo grupo = criarGrupoComOsIntegrantes(nomeGrupo, false, nomeGrupo, true, usuario1, usuario2);
		getAprovisionamento().criarGrupo(nomeGrupo, emailGrupo, "").eAdicionarMembros(
		        Arrays.asList(usuario1.getEmail(), usuario2.getEmail()));

		List<ServicoGrupo> servicosGrupo = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		GoogleGrupoJSON googleGrupojson = new GoogleGrupoJSON();
		googleGrupojson.setEmailGrupo(nomeGrupo);
		googleGrupojson.setId(grupo.getId());
		googleGrupojson.setIdServico(servicosGrupo.get(0).getIdServico());
		UsuarioJSON usuariojson = new UsuarioJSON();
		usuariojson.setEmail(usuario2.getEmail());
		usuariojson.setAtivo(true);
		usuariojson.setApelido("usuario.2");
		usuariojson.setUsername(username2);
		usuariojson.setNome("usuario.2");
		googleGrupojson.setUsuarioJSONs(Arrays.asList(usuariojson));
		usuarioRS.removerUsuarioGrupo(Arrays.asList(usuario2.getEmail()), Arrays.asList(googleGrupojson));
		Group group = getAprovisionamento().googleAPI().directory().getGroup(emailGrupo);
		List<Member> members = getAprovisionamento().googleAPI().directory().getMembersGroup(group).getMembers();

		assertTrue(members.size() == 1);
		assertEquals(username1 + "@dextra-sw.com", members.get(0).getEmail());
	}

	@Test
	public void testaAjustarBanco() throws EntityNotFoundException {
		limpaUsuariosInseridos(repositorio);
		criarUsuario("usuario1", null);
		criarUsuario("usuario2", null);
		criarUsuario("usuario3", null);
		criarUsuario("usuario4", null);

		List<Usuario> usuarios = repositorio.lista();
		assertEquals(4, usuarios.size());
		for (Usuario usuario : usuarios) {
			assertNull(usuario.isAtivo());
		}
		Response response = usuarioRS.ajustarFlagAtivoBanco();
		usuarios = repositorio.lista();
		for (Usuario usuario : usuarios) {
			assertNotNull(usuario.isAtivo());
		}

		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	public class UsuarioRSFake extends UsuarioRS {
		@Override
		protected String obtemUsernameDoUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}

}
