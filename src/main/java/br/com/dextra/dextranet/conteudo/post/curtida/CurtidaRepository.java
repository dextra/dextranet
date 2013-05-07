package br.com.dextra.dextranet.conteudo.post.curtida;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class CurtidaRepository extends EntidadeRepository {

	public Curtida persiste(Curtida curtida) {
		return super.persiste(curtida);
	}

	public void remove(String id) {
		super.remove(id, Curtida.class);
	}

	public void remove(String conteudoId, String username) {
		Curtida curtida = obtemPorConteudoEUsusuario(conteudoId, username);
		this.remove(curtida.getId());
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

	public List<Curtida> listaPorConteudo(String conteudoId) {
		Filter filtroPorConteudoId = new Query.FilterPredicate(CurtidaFields.conteudoId.name(), FilterOperator.EQUAL,
				conteudoId);

		Query query = new Query(Curtida.class.getName());
		query.setFilter(filtroPorConteudoId);
		query.addSort(CurtidaFields.data.name(), SortDirection.DESCENDING);

		PreparedQuery pquery = this.datastore.prepare(query);

		List<Curtida> curtidas = new ArrayList<Curtida>();
		Iterable<Entity> entidades = pquery.asIterable();
		for (Entity entidade : entidades) {
			curtidas.add(new Curtida(entidade));
		}

		return curtidas;
	}

	protected Curtida obtemPorConteudoEUsusuario(String conteudoId, String usuario) {
		Filter filtroPorConteudoId = new Query.FilterPredicate(CurtidaFields.conteudoId.name(), FilterOperator.EQUAL,
				conteudoId);
		Filter filtroPorUsername = new Query.FilterPredicate(CurtidaFields.usuario.name(), FilterOperator.EQUAL,
				usuario);

		List<Filter> filtros = new ArrayList<Filter>();
		filtros.add(filtroPorConteudoId);
		filtros.add(filtroPorUsername);

		Filter filtro = new Query.CompositeFilter(CompositeFilterOperator.AND, filtros);

		Query query = new Query(Curtida.class.getName());
		query.setFilter(filtro);

		PreparedQuery pquery = this.datastore.prepare(query);
		Entity entidadeEncontrada = pquery.asSingleEntity();

		if (entidadeEncontrada == null) {
			throw new EntidadeNaoEncontradaException(Curtida.class.getName(), "conteudoId | usuario", conteudoId
					+ " | " + usuario);
		}

		return new Curtida(entidadeEncontrada);
	}

}
