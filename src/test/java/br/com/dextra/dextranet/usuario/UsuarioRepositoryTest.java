package br.com.dextra.dextranet.usuario;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class UsuarioRepositoryTest extends TesteIntegracaoBase {

	private UsuarioRepository repositorio = new UsuarioRepository();

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
	public void testaListaTodos() {
		Usuario usuario01 = new Usuario("reloadDextranet");
		Usuario usuario02 = new Usuario("dextranet");
		Usuario usuario03 = new Usuario("novaDextranet");
		
		repositorio.persiste(usuario01);
		repositorio.persiste(usuario02);
		repositorio.persiste(usuario03);

		List<Usuario> usuariosEncontrados = repositorio.lista();

		// verifica se encontrou todas
		Assert.assertEquals(3, usuariosEncontrados.size());
		Assert.assertEquals(usuario02, usuariosEncontrados.get(0));
		Assert.assertEquals(usuario03, usuariosEncontrados.get(1));
		Assert.assertEquals(usuario01, usuariosEncontrados.get(2));
	}

}
