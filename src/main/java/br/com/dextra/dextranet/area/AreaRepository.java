package br.com.dextra.dextranet.area;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class AreaRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Area.class);
	}

	public Area obtemPorId(String id) throws EntityNotFoundException {
		Entity area = super.obtemPorId(id, Area.class);
		return new Area(area);
	}

	public List<Area> lista() {
		EntidadeOrdenacao ordenacaoPorNome = new EntidadeOrdenacao(AreaFields.nome.toString(), SortDirection.ASCENDING);
		List<Area> areas = new ArrayList<Area>();

		Iterable<Entity> entidades = super.lista(Area.class, ordenacaoPorNome);
		for (Entity entidade : entidades) {
			areas.add(new Area(entidade));
		}

		return areas;
	}

}
