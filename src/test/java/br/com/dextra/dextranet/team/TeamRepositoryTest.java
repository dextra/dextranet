package br.com.dextra.dextranet.team;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.time.Team;
import br.com.dextra.dextranet.time.TeamRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class TeamRepositoryTest extends TesteIntegracaoBase {
	private TeamRepository repositorio = new TeamRepository();

	@Test
	public void testaRemover() {
		Team team = new Team("Time", "grupoemailtime@dextra-sw.com");
		Team teamPersistido = repositorio.persiste(team);
		repositorio.remove(teamPersistido.getId());

		try {
			repositorio.obtemPorId(teamPersistido.getId());
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaInserir() throws EntityNotFoundException {
		Team team = new Team("Time", "grupoemailtime@dextra-sw.com");
		Team teamPersistido = repositorio.persiste(team);
		Assert.assertEquals(team.getNome(), teamPersistido.getNome());
		Assert.assertEquals(team.getEmailGrupo(), teamPersistido.getEmailGrupo());
		limparTime(team.getId());
	}


	@Test
	public void testaBuscarPorId() throws EntityNotFoundException {
		Team team = new Team("Time", "grupoemailtime@dextra-sw.com");
		repositorio.persiste(team);
		Team teamEncontrado = repositorio.obtemPorId(team.getId());
		Assert.assertEquals(team.getNome(), teamEncontrado.getNome());
		Assert.assertEquals(team.getEmailGrupo(), teamEncontrado.getEmailGrupo());
		limparTime(team.getId());
	}

	@Test
	public void testaListar() {
		Team teamA = new Team("Time A", "grupoemailtimea@dextra-sw.com");
		repositorio.persiste(teamA);
		Team teamB = new Team("Time B", "grupoemailtimeb@dextra-sw.com");
		repositorio.persiste(teamB);
		Team teamC = new Team("Time C", "grupoemailtimec@dextra-sw.com");
		repositorio.persiste(teamC);

		List<Team> teams = repositorio.lista();
		Assert.assertEquals(3, teams.size());
		Assert.assertEquals(teams.get(0).getId(), teamA.getId());
		Assert.assertEquals(teams.get(1).getId(), teamB.getId());
		Assert.assertEquals(teams.get(2).getId(), teamC.getId());
		limparTimes(teams);
	}

	private void limparTime(String id) {
        repositorio.remove(id);
	}

	private void limparTimes(List<Team> teams) {
		for (Team team : teams) {
			repositorio.remove(team.getId());
		}
	}
}
