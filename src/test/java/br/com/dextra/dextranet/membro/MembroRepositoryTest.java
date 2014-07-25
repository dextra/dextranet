package br.com.dextra.dextranet.membro;

import java.util.List;

import org.junit.Test;

import static junit.framework.Assert.*;
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
		Usuario usuario1 = criaUsuario("usuario1", true);
		Usuario usuario2 = criaUsuario("usuario2", true);
		
		criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario2);
		criaGrupoComOsIntegrantes(false, "Grupo 2", usuario1, usuario2);
		criaGrupoComOsIntegrantes(false, "Grupo 3", usuario1, usuario2);
		
		repositorioMembro.removeMembroDosGruposPor(usuario2);
		List<Membro> membros = repositorioMembro.obtemPorIdUsuario(usuario2.getId());
		List<Grupo> grupos = repositorioGrupo.obtemPorIdIntegrante(usuario2.getId());
		
		assertTrue(membros.isEmpty());
		assertTrue(grupos.isEmpty());
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
		novoUsuario = repositorioUsuario.persiste(novoUsuario);
		return novoUsuario;
	}
}
