package br.com.dextra.dextranet.usuario;

import java.util.List;

import org.junit.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class UsuarioRepositoryTest extends TesteIntegracaoBase {

	private UsuarioRepository repositorio = new UsuarioRepository();

	@After
	public void after() throws EntityNotFoundException {
		this.limpaUsuariosInseridos(repositorio);
	}

	@Test
	public void testaRemocao() {
		Usuario novoUsuario = new Usuario("dextranet");
		Usuario usuarioCriado = repositorio.persiste(novoUsuario);

		String idDoUsuarioCriado = usuarioCriado.getId();
		repositorio.remove(idDoUsuarioCriado);

		try {
			repositorio.obtemPorId(idDoUsuarioCriado);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaListaTodos() throws EntityNotFoundException {
		Usuario usuario01 = new Usuario("reloadDextranet");
		Usuario usuario02 = new Usuario("dextranet");
		Usuario usuario03 = new Usuario("novaDextranet");

		repositorio.persiste(usuario01);
		repositorio.persiste(usuario02);
		repositorio.persiste(usuario03);

		List<Usuario> usuariosEncontrados = repositorio.lista();

		Assert.assertEquals(3, usuariosEncontrados.size());
		Assert.assertEquals(usuario02, usuariosEncontrados.get(0));
		Assert.assertEquals(usuario03, usuariosEncontrados.get(1));
		Assert.assertEquals(usuario01, usuariosEncontrados.get(2));
	}

	@Test
	public void testaListaPorUsuariosAtivos() throws EntityNotFoundException {
		Usuario usuario01 = new Usuario("reloadDextranet");
		usuario01.setAtivo(false);
		Usuario usuario02 = new Usuario("dextranet");
		Usuario usuario03 = new Usuario("novaDextranet");

		repositorio.persiste(usuario01);
		repositorio.persiste(usuario02);
		repositorio.persiste(usuario03);
		Boolean isInfra = false;
		List<Usuario> usuariosEncontrados = repositorio.listaPor(isInfra);

		Assert.assertEquals(2, usuariosEncontrados.size());
	}
	
	@Test
	public void testaListaPorUsuariosAtivosEInativos() throws EntityNotFoundException {
		Usuario usuario01 = new Usuario("reloadDextranet");
		usuario01.setAtivo(false);
		Usuario usuario02 = new Usuario("dextranet");
		Usuario usuario03 = new Usuario("novaDextranet");

		repositorio.persiste(usuario01);
		repositorio.persiste(usuario02);
		repositorio.persiste(usuario03);
		Boolean isInfra = true;
		List<Usuario> usuariosEncontrados = repositorio.listaPor(isInfra);

		Assert.assertEquals(3, usuariosEncontrados.size());
	}
	
	@Test
	public void testaObtemPorUsernameInexistente() {
		try {
			repositorio.obtemPorUsername("dextranet");
			Assert.fail();
		} catch (EntidadeNaoEncontradaException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaObtemPorUsername() {
		Usuario usuario01 = new Usuario("reloadDextranet");
		Usuario usuario02 = new Usuario("dextranet");
		Usuario usuario03 = new Usuario("novaDextranet");

		repositorio.persiste(usuario01);
		repositorio.persiste(usuario02);
		repositorio.persiste(usuario03);

		Assert.assertEquals(usuario02, repositorio.obtemPorUsername(usuario02.getUsername()));
	}

}
