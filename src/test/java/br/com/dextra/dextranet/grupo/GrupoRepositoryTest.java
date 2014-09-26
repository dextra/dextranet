package br.com.dextra.dextranet.grupo;

import static br.com.dextra.dextranet.persistencia.TesteUtils.criaGrupoComOsIntegrantes;
import static br.com.dextra.dextranet.persistencia.TesteUtils.criaUsuario;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoRepositoryTest extends TesteIntegracaoBase {
	private GrupoRepository grupoRepositorio = new GrupoRepository();
	private UsuarioRepository usuarioRepositorio = new UsuarioRepository();
	private Usuario usuario = new Usuario("login.google");
	
	@Before
	public void preparaTestes() {
		usuario = usuarioRepositorio.persiste(usuario);
	}

	@Test
	public void testaRemover() {
		Grupo grupo = new Grupo("Grupo A", "Grupo teste", usuario.getNome());
		Grupo grupoObtido = grupoRepositorio.persiste(grupo);
		grupoRepositorio.remove(grupoObtido.getId());

		try {
			grupoRepositorio.obtemPorId(grupo.getId());
		} catch (EntityNotFoundException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testaInserir() throws EntityNotFoundException {
		limpaGrupoInseridos(grupoRepositorio);
		Grupo grupo = new Grupo("Grupo A", "Grupo teste", usuario.getNome());
		grupoRepositorio.persiste(grupo);
		Grupo grupoObtido = grupoRepositorio.obtemPorId(grupo.getId());
		assertEquals(grupo.getNome(), grupoObtido.getNome());
		assertEquals(grupo.getDescricao(), grupoObtido.getDescricao());
		assertEquals(grupo.getProprietario(), grupoObtido.getProprietario());
	}

	@Test
	public void testaListar() {
		limpaGrupoInseridos(grupoRepositorio);
		Grupo grupoA = criaGrupoComOsIntegrantes("grupoa", false, "Grupo A", true, usuario);
		Grupo grupoB = criaGrupoComOsIntegrantes("grupotesteb", false, "Grupo teste B", true, usuario);
		Grupo grupoC = criaGrupoComOsIntegrantes("grupotestec", false, "Grupo teste C", true, usuario);
		criaGrupoComOsIntegrantes("grupotested", false, "Grupo teste D", true, usuario);

		List<Grupo> grupos = grupoRepositorio.lista();

		assertEquals(grupos.get(0).getNome(), grupoA.getNome());
		assertEquals(grupos.get(0).getDescricao(), grupoA.getDescricao());
		assertEquals(grupos.get(0).getProprietario(), grupoA.getProprietario());

		assertEquals(grupos.get(1).getNome(), grupoB.getNome());
		assertEquals(grupos.get(1).getDescricao(), grupoB.getDescricao());
		assertEquals(grupos.get(1).getProprietario(), grupoB.getProprietario());

		assertEquals(grupos.get(2).getNome(), grupoC.getNome());
		assertEquals(grupos.get(2).getDescricao(), grupoC.getDescricao());
		assertEquals(grupos.get(2).getProprietario(), grupoC.getProprietario());
	}

	@Test
	public void testaAjusteProprietarioGrupo() throws EntityNotFoundException {
		Usuario usuario1 = criaUsuario("usuario1", true);
		Usuario usuario2 = criaUsuario("usuario2", true);
		Usuario usuario3 = criaUsuario("usuario3", true);
		Usuario usuario4 = criaUsuario("usuario4", true);
		
		criaGrupoComOsIntegrantes("grupo1", false, "Grupo1", true, usuario1, usuario2, usuario3, usuario4);
		criaGrupoComOsIntegrantes("grupo2", false, "Grupo2", true, usuario1, usuario2, usuario3, usuario4);
		criaGrupoComOsIntegrantes("grupo3", false, "Grupo3", true, usuario1, usuario2, usuario3, usuario4);
		criaGrupoComOsIntegrantes("grupo4", false, "Grupo4", true, usuario1, usuario2, usuario3, usuario4);
		
		List<Grupo> grupos = grupoRepositorio.obtemPorIdIntegrante(usuario1.getId());
		
		grupoRepositorio.ajustarProprietarioGrupo(usuario1);
		
		grupos = grupoRepositorio.obtemPorIdIntegrante(usuario1.getId());
		
		for (Grupo grupo : grupos) {
			assertFalse(grupo.getProprietario().equals(usuario1.getUsername()));
		}
	}

}