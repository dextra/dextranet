package br.com.dextra.dextranet.time;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;
import br.com.dextra.dextranet.usuario.Usuario;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class TeamRepository extends EntidadeRepository {
	public Team persiste(Team team) {
		return super.persiste(team);
	}

	public Team obtemPorId(String id) throws EntityNotFoundException {
		Entity teamEntity = this.obtemPorId(id, Team.class);
		return new Team(teamEntity);
	}

	public void remove(String id) {
		this.remove(id, Team.class);
	}

	public List<Team> lista() {
		EntidadeOrdenacao ordenacaoPorUsername = new EntidadeOrdenacao(TeamFields.nome.name(), SortDirection.ASCENDING);
		List<Team> teams = new ArrayList<Team>();

		Iterable<Entity> entidades = super.lista(Usuario.class, ordenacaoPorUsername);
		for (Entity entidade : entidades) {
			teams.add(new Team(entidade));
		}

		return teams;
	}
}
