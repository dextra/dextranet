package br.com.dextra.dextranet.conteudo.post.curtida;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class CurtidaRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Curtida.class);
	}

	public void remove(String conteudoId, String username) {

		Filter filtroPorConteudoId = new Query.FilterPredicate(CurtidaFields.conteudoId.name(), FilterOperator.EQUAL,
				conteudoId);
		Filter filtroPorUsername = new Query.FilterPredicate(CurtidaFields.usuario.name(), FilterOperator.EQUAL,
				conteudoId);

		List<Filter> filtros = new ArrayList<Filter>();
		filtros.add(filtroPorConteudoId);
		filtros.add(filtroPorUsername);

		Filter filtro = new Query.CompositeFilter(CompositeFilterOperator.AND, filtros);

		Query query = new Query(Curtida.class.getName());
		query.setFilter(filtro);

		PreparedQuery pquery = this.datastore.prepare(query);
		pquery.asSingleEntity();
	}

	public Curtida obtemPorId(String id) throws EntityNotFoundException {
		Entity curtida = super.obtemPorId(id, Curtida.class);
		return new Curtida(curtida);
	}

	public List<Curtida> lista(EntidadeOrdenacao... criterioOrdenacao) {
		List<Curtida> curtidas = new ArrayList<Curtida>();

		Iterable<Entity> entidades = super.lista(Curtida.class, criterioOrdenacao);
		for (Entity entidade : entidades) {
			curtidas.add(new Curtida(entidade));
		}

		return curtidas;
	}

}
