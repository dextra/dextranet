package br.com.dextra.dextranet.usuario;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static junit.framework.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.UsuarioJSON;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class UsuarioRSTest extends TesteIntegracaoBase {
	private static final String USUARIO_LOGADO = "login.google";
	private UsuarioRS rest = new UsuarioRSFake();
	private UsuarioRepository repositorio = new UsuarioRepository();
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	
	@Test
	public void testaAtualizacaoPermitida() throws EntityNotFoundException {
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

		rest.atualizar(usuario.getId(), "Nome", "Apelido", "&Aacute;rea", "Unidade", "Ramal", "Residencial", "Celular",
				"GitHub", "Skype", "blog", true);
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

		rest.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal", "Residencial", "Celular",
				"GitHub", "Skype", "blog", true);
		usuarioPersistido = repositorio.obtemPorId(usuario.getId());
		assertEquals("Area", usuarioPersistido.getArea());
	}

	@Test
	public void testaAtualizacaoNaoPermitida() throws EntityNotFoundException {
		Usuario usuario = new Usuario("dextranet");
		repositorio.persiste(usuario);

		Response resposta = rest.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal",
				"Residencial", "Celular", "GitHub", "Skype", "blog", true);
		assertEquals(Status.FORBIDDEN.getStatusCode(), resposta.getStatus());
	}
	
	@Test
	public void testaDesativacaoUsuario() throws EntityNotFoundException {
		limpaUsuariosInseridos(repositorio);
		Usuario usuarioLogado = criaUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criaUsuario("usuario1", true);
		Usuario usuario2 = criaUsuario("usuario2", true);
		criaGrupoComOsIntegrantes(true, "Infra", usuarioLogado);
		criaGrupoComOsIntegrantes(false, "Grupo1", usuario2, usuario1);
		
		Object entity = rest.atualizar(usuario1.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal",
				"Residencial", "Celular", "GitHub", "Skype", "blog", false).getEntity();
		
		UsuarioJSON json = (UsuarioJSON) entity;
		Usuario retorno = repositorio.obtemPorId(usuario1.getId());
		
		assertFalse(json.isAtivo());
		assertFalse(retorno.isAtivo());
	}
	
	public class UsuarioRSFake extends UsuarioRS {
		@Override
		protected String obtemUsernameDoUsuarioLogado() {
			return USUARIO_LOGADO;
		}
		
	}
	
	private Grupo criaGrupoComOsIntegrantes(Boolean isInfra, String nomeDoGrupo,
			Usuario... integrantes) {
		Grupo novoGrupo = new Grupo(nomeDoGrupo, nomeDoGrupo,
				integrantes[0].getUsername());
		novoGrupo.setInfra(isInfra);
		novoGrupo = repositorioGrupo.persiste(novoGrupo);

		for (Usuario integrante : integrantes) {
			repositorioMembro.persiste(new Membro(integrante.getId(), novoGrupo
					.getId(), integrante.getNome(), "email"));
		}

		return novoGrupo;
	}

	private Usuario criaUsuario(String username, Boolean isAtivo) {
		Usuario novoUsuario = new Usuario(username);
		novoUsuario.setAtivo(isAtivo);
		novoUsuario = repositorio.persiste(novoUsuario);
		return novoUsuario;
	}
}
