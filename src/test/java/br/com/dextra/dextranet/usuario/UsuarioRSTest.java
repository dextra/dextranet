package br.com.dextra.dextranet.usuario;

import static br.com.dextra.dextranet.persistencia.TesteUtils.criarGrupoComOsIntegrantes;
import static br.com.dextra.dextranet.persistencia.TesteUtils.criarUsuario;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.UsuarioJSON;
import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.persistencia.TesteUtils;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class UsuarioRSTest extends TesteIntegracaoBase {
	private static final String USUARIO_LOGADO = "login.google";
	private UsuarioRS usuarioRS = new UsuarioRSFake();
	private UsuarioRepository repositorio = new UsuarioRepository();
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	
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

		usuarioRS.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal", "Residencial", "Celular",
				"GitHub", "Skype", "blog", null);
		usuarioPersistido = repositorio.obtemPorId(usuario.getId());
		assertEquals("Area", usuarioPersistido.getArea());
	}

	@Test
	public void testaAtualizacaoNaoPermitida() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuario = TesteUtils.criarUsuario("usuario1", true);
		TesteUtils.criarUsuario(USUARIO_LOGADO, true);

		Response resposta = usuarioRS.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal",
				"Residencial", "Celular", "GitHub", "Skype", "blog", null);
		assertEquals(Status.FORBIDDEN.getStatusCode(), resposta.getStatus());
	}
	
	@Test
	public void testaDesativacaoUsuarioProprietarioGrupo() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuarioLogado = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Usuario usuario2 = criarUsuario("usuario2", true);
		
		criarGrupoComOsIntegrantes("infra", true, "Infra", true, usuarioLogado);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, usuario2, usuario1);
		
		UsuarioJSON json = (UsuarioJSON) usuarioRS.atualizar(usuario2.getId(), null, null, null, null, null,
				null, null, null, null, null, false).getEntity();
		
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
	public void testaDesativacaoUsuario() throws EntityNotFoundException, GeneralSecurityException, URISyntaxException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuarioLogado = criarUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criarUsuario("usuario1", true);
		Usuario usuario2 = criarUsuario("usuario2", true);
		criarGrupoComOsIntegrantes("infra", true, "Infra", true, usuarioLogado);
		criarGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, usuario2, usuario1);
		
		UsuarioJSON json = (UsuarioJSON) usuarioRS.atualizar(usuario1.getId(), null, null, null, null, null,
				null, null, null, null, null, false).getEntity();
		
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
		
		Object entity = usuarioRS.atualizar(usuario1.getId(), null, null, null, null, null,
				null, null, null, null, null, true).getEntity();
		
		UsuarioJSON json = (UsuarioJSON) entity;
		Usuario retorno = repositorio.obtemPorId(usuario1.getId());
		
		assertTrue(json.isAtivo());
		assertTrue(retorno.isAtivo());
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
		
		@Override
		protected void removerUsuarioGrupo(List<String> emails,
				List<GoogleGrupoJSON> servicos)
				throws GeneralSecurityException, URISyntaxException {
		}
		
	}

}
