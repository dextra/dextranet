package br.com.dextra.dextranet.team;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.grupos.Grupo;
import br.com.dextra.dextranet.grupos.GrupoRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class TeamRepositoryTest extends TesteIntegracaoBase {
	private GrupoRepository repositorio = new GrupoRepository();

	@Test
	public void testaRemover() {
		Grupo team = new Grupo("Time", "grupoemailtime@dextra-sw.com");
		Grupo teamPersistido = repositorio.persiste(team);
		repositorio.remove(teamPersistido.getId());

		try {
			repositorio.obtemPorId(teamPersistido.getId());
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaInserir() throws EntityNotFoundException {
		Grupo team = new Grupo("Time", "grupoemailtime@dextra-sw.com");
		Grupo teamPersistido = repositorio.persiste(team);
		Assert.assertEquals(team.getNome(), teamPersistido.getNome());
		Assert.assertEquals(team.getEmailGrupo(), teamPersistido.getEmailGrupo());
		limparTime(team.getId());
	}


	@Test
	public void testaBuscarPorId() throws EntityNotFoundException {
		Grupo team = new Grupo("Time", "grupoemailtime@dextra-sw.com");
		repositorio.persiste(team);
		Grupo teamEncontrado = repositorio.obtemPorId(team.getId());
		Assert.assertEquals(team.getNome(), teamEncontrado.getNome());
		Assert.assertEquals(team.getEmailGrupo(), teamEncontrado.getEmailGrupo());
		limparTime(team.getId());
	}

	@Test
	public void testaListar() {
		Grupo teamA = new Grupo("Time A", "grupoemailtimea@dextra-sw.com");
		repositorio.persiste(teamA);
		Grupo teamB = new Grupo("Time B", "grupoemailtimeb@dextra-sw.com");
		repositorio.persiste(teamB);
		Grupo teamC = new Grupo("Time C", "grupoemailtimec@dextra-sw.com");
		repositorio.persiste(teamC);

		List<Grupo> teams = repositorio.lista();
		Assert.assertEquals(3, teams.size());
		Assert.assertEquals(teams.get(0).getId(), teamA.getId());
		Assert.assertEquals(teams.get(1).getId(), teamB.getId());
		Assert.assertEquals(teams.get(2).getId(), teamC.getId());
		limparTimes(teams);
	}

	private void limparTime(String id) {
        repositorio.remove(id);
	}

	private void limparTimes(List<Grupo> teams) {
		for (Grupo team : teams) {
			repositorio.remove(team.getId());
		}
	}
}
