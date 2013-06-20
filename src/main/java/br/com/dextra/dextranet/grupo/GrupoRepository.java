package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class GrupoRepository extends EntidadeRepository {
	public Grupo persiste(Grupo grupo) {
		return super.persiste(grupo);
	}

	public Grupo obtemPorId(String id) throws EntityNotFoundException {
		Entity grupoEntity = this.obtemPorId(id, Grupo.class);
		return new Grupo(grupoEntity);
	}

	public void remove(String id) {
		this.remove(id, Grupo.class);
	}

	public List<Grupo> lista() {
		EntidadeOrdenacao ordenacaoPorNome = new EntidadeOrdenacao(GrupoFields.nome.name(), SortDirection.ASCENDING);
		List<Grupo> grupos = new ArrayList<Grupo>();

		Iterable<Entity> entidades = super.lista(Grupo.class, ordenacaoPorNome);
		for (Entity entidade : entidades) {
			grupos.add(new Grupo(entidade));
		}

		return grupos;
	}
}
