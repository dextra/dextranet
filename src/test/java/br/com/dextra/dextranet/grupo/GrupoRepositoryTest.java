package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.grupos.Grupo;
import br.com.dextra.dextranet.grupos.GrupoRepository;
import br.com.dextra.dextranet.grupos.Membro;
import br.com.dextra.dextranet.grupos.MembroRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoRepositoryTest extends TesteIntegracaoBase {
	private GrupoRepository repositorio = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	private UsuarioRepository usuarioRepositorio = new UsuarioRepository();
	private Usuario usuario = new Usuario("login.google");

	@Before
	public void preparaTestes() {
		usuario = usuarioRepositorio.persiste(usuario);
	}

	@Test
	public void testaRemover() {
		//TODO: implementar
	}

	@Test
	public void testaInserir() throws EntityNotFoundException {
		//TODO: implementar
	}


	@Test
	public void testaBuscarPorId() throws EntityNotFoundException {
		//TODO: implementar
	}

	@Test
	public void testaListar() {
		//TODO: implementar
	}
}
