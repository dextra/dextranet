package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.grupos.Grupo;
import br.com.dextra.dextranet.grupos.GrupoRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoRepositoryTest extends TesteIntegracaoBase {
	private GrupoRepository repositorio = new GrupoRepository();

	@Test
	public void testaRemover() {
		Grupo grupo = new Grupo("Grupo 1", "Descrição do Grupo 1", "login.google", new ArrayList<Usuario>());
		Grupo grupoPersistido = repositorio.persiste(grupo);
		repositorio.remove(grupoPersistido.getId());

		try {
			repositorio.obtemPorId(grupoPersistido.getId());
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaInserir() throws EntityNotFoundException {
		Grupo grupo = new Grupo("Grupo");
		Grupo grupoPersistido = repositorio.persiste(grupo);
		Assert.assertEquals(grupo.getNome(), grupoPersistido.getNome());
		limparGrupo(grupo.getId());
	}


	@Test
	public void testaBuscarPorId() throws EntityNotFoundException {
		Grupo grupo = new Grupo("Grupo");
		repositorio.persiste(grupo);
		Grupo grupoEncontrado = repositorio.obtemPorId(grupo.getId());
		Assert.assertEquals(grupo.getNome(), grupoEncontrado.getNome());
		limparGrupo(grupo.getId());
	}

	@Test
	public void testaListar() {
		Grupo grupoA = new Grupo("Grupo A");
		repositorio.persiste(grupoA);
		Grupo grupoB = new Grupo("Grupo B");
		repositorio.persiste(grupoB);
		Grupo grupoC = new Grupo("Grupo C");
		repositorio.persiste(grupoC);

		List<Grupo> grupos = repositorio.lista();
		Assert.assertEquals(3, grupos.size());
		Assert.assertEquals(grupos.get(0).getId(), grupoA.getId());
		Assert.assertEquals(grupos.get(1).getId(), grupoB.getId());
		Assert.assertEquals(grupos.get(2).getId(), grupoC.getId());
		limparGrupos(grupos);
	}

	private void limparGrupo(String id) {
        repositorio.remove(id);
	}

	private void limparGrupos(List<Grupo> grupos) {
		for (Grupo grupo : grupos) {
			repositorio.remove(grupo.getId());
		}
	}
}
