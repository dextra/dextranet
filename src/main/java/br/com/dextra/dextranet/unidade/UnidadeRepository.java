package br.com.dextra.dextranet.unidade;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class UnidadeRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Unidade.class);
	}

	public Unidade obtemPorId(String id) throws EntityNotFoundException {
		Entity unidade = super.obtemPorId(id, Unidade.class);
		return new Unidade(unidade);
	}

	public List<Unidade> lista() {
		EntidadeOrdenacao ordenacaoPorNome = new EntidadeOrdenacao(UnidadeFields.nome.name(), SortDirection.ASCENDING);
		List<Unidade> unidades = new ArrayList<Unidade>();

		Iterable<Entity> entidades = super.lista(Unidade.class, ordenacaoPorNome);
		for (Entity entidade : entidades) {
			unidades.add(new Unidade(entidade));
		}

		return unidades;
	}

}