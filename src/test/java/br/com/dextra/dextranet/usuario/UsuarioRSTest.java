package br.com.dextra.dextranet.usuario;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class UsuarioRSTest extends TesteIntegracaoBase {

	private UsuarioRS rest = new UsuarioRSFake();

	private UsuarioRepository repositorio = new UsuarioRepository();

	@Test
	public void testaAtualizacaoPermitida() throws EntityNotFoundException {
		Usuario usuario = new Usuario("usuarioLogado");
		repositorio.persiste(usuario);

		Usuario usuarioPersistido = repositorio.obtemPorId(usuario.getId());
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getNome()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getApelido()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getArea()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getUnidade()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getRamal()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getTelefoneResidencial()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getTelefoneCelular()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getEmail()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getGtalk()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getGitHub()));
		Assert.assertTrue(StringUtils.isEmpty(usuarioPersistido.getSkype()));

		rest.atualizar(usuario.getId(), "Nome", "Apelido", "&Aacute;rea", "Unidade", "Ramal", "Residencial", "Celular",
				"Email", "Gtalk", "GitHub", "Skype");
		usuarioPersistido = repositorio.obtemPorId(usuario.getId());

		Assert.assertEquals("Nome", usuarioPersistido.getNome());
		Assert.assertEquals("Apelido", usuarioPersistido.getApelido());
		Assert.assertEquals("&Aacute;rea", usuarioPersistido.getArea());
		Assert.assertEquals("Unidade", usuarioPersistido.getUnidade());
		Assert.assertEquals("Ramal", usuarioPersistido.getRamal());
		Assert.assertEquals("Residencial", usuarioPersistido.getTelefoneResidencial());
		Assert.assertEquals("Celular", usuarioPersistido.getTelefoneCelular());
		Assert.assertEquals("Email", usuarioPersistido.getEmail());
		Assert.assertEquals("Gtalk", usuarioPersistido.getGtalk());
		Assert.assertEquals("GitHub", usuarioPersistido.getGitHub());
		Assert.assertEquals("Skype", usuarioPersistido.getSkype());

		rest.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal", "Residencial", "Celular", "Email", "Gtalk",
				"GitHub", "Skype");
		usuarioPersistido = repositorio.obtemPorId(usuario.getId());
		Assert.assertEquals("Area", usuarioPersistido.getArea());
	}

	@Test
	public void testaAtualizacaoNaoPermitida() throws EntityNotFoundException {
		Usuario usuario = new Usuario("dextranet");
		repositorio.persiste(usuario);

		Response resposta = rest.atualizar(usuario.getId(), "Nome", "Apelido", "Area", "Unidade", "Ramal",
				"Residencial", "Celular", "Email", "Gtalk", "GitHub", "Skype");
		Assert.assertEquals(Status.FORBIDDEN.getStatusCode(), resposta.getStatus());
	}

	public class UsuarioRSFake extends UsuarioRS {

		@Override
		protected String obtemUsernameDoUsuarioLogado() {
			return "usuarioLogado";
		}

	}

}
