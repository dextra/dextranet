package br.com.dextra.dextranet.grupo;

import static org.junit.Assert.assertEquals;
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
	private MembroRepository membroRepository = new MembroRepository();
	
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
		Grupo grupoA = criarGrupo("Grupo A", "Grupo teste A", usuario);
		Grupo grupoB = criarGrupo("Grupo B", "Grupo teste B", usuario);
		Grupo grupoC = criarGrupo("Grupo C", "Grupo teste C", usuario);
		criarGrupo("Grupo D", "Grupo teste D", usuario);

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

	private Grupo criarGrupo(String nome, String descricao, Usuario usuario) {
		Grupo grupoA = new Grupo(nome, descricao, usuario.getUsername());
		grupoRepositorio.persiste(grupoA);
		Membro membro = new Membro(usuario.getId(), grupoA.getId(), usuario.getNome(), usuario.getEmail());
		membroRepository.persiste(membro);
		return grupoA;
	}
}