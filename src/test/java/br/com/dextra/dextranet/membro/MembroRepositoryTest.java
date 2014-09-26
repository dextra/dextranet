package br.com.dextra.dextranet.membro;

import java.util.List;

import org.junit.Test;

import static junit.framework.Assert.*;
import static br.com.dextra.dextranet.persistencia.TesteUtils.*;
import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class MembroRepositoryTest extends TesteIntegracaoBase {
	GrupoRepository repositorioGrupo = new GrupoRepository();
	MembroRepository repositorioMembro = new MembroRepository();
	UsuarioRepository repositorioUsuario = new UsuarioRepository();
		
	@Test
	public void testaRemoverUsuarioDosGrupos() throws EntityNotFoundException {
		Usuario usuario1 = criarUsuario("usuario1", true);
		Usuario usuario2 = criarUsuario("usuario2", true);
		
		criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario2);
		criarGrupoComOsIntegrantes("grupo2", false, "Grupo 2", true, usuario1, usuario2);
		criarGrupoComOsIntegrantes("grupo3", false, "Grupo 3", true, usuario1, usuario2);
		
		repositorioMembro.removeMembroDosGruposPor(usuario2);
		List<Membro> membros = repositorioMembro.obtemPorIdUsuario(usuario2.getId());
		List<Grupo> grupos = repositorioGrupo.obtemPorIdIntegrante(usuario2.getId());
		
		assertTrue(membros.isEmpty());
		assertTrue(grupos.isEmpty());
	}
	
	@Test
	public void testaObtemPorUsername() throws EntityNotFoundException {
		Usuario usuario1 = criarUsuario("usuario1", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario1);
		Membro membro = repositorioMembro.obtemPorUsername("usuario1", grupo.getId());
		
		assertEquals(usuario1.getUsername(), membro.getEmail());
		assertEquals(grupo.getId(), membro.getIdGrupo());
	}
}
