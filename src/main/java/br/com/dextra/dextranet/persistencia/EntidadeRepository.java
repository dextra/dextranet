package br.com.dextra.dextranet.persistencia;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.adapter.ParametrosAdapter;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class EntidadeRepository {

	protected DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	protected <T extends Entidade> T persiste(T entidade) {
		datastore.put(entidade.toEntity());
		return entidade;
	}

	protected <T extends Entidade> void remove(String id, Class<T> clazz) {
		Key key = KeyFactory.createKey(clazz.getName(), id);
		datastore.delete(key);
	}

	protected <T extends Entidade> Entity obtemPorId(String id, Class<T> clazz) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(clazz.getName(), id);
		return datastore.get(key);
	}

	protected <T extends Entidade> Iterable<Entity> lista(Class<T> clazz, EntidadeOrdenacao... ordenacao) {
		return this.lista(clazz, null, null, ordenacao);
	}

	protected <T extends Entidade> Iterable<Entity> lista(ParametrosAdapter<T> params) {
		Query query = new Query(params.getEntidade().getName());
		if (params.getOrdenacao() != null) {
			for (EntidadeOrdenacao o : params.getOrdenacao()) {
				query.addSort(o.getAtributo(), o.getOrdenacao());
			}
		}
		if (params.getFiltroCampos() != null && params.getFiltroValores() != null && params.getFiltroCampos().size() == params.getFiltroValores().size()) {
			setFilters(params.getFiltroCampos(), params.getFiltroValores(), query);
		}
		PreparedQuery pquery = this.datastore.prepare(query);
		if (params.getRegistrosPorPagina() != null && params.getNumeroDaPagina() != null) {
			FetchOptions opcoesFetch = FetchOptions.Builder.withDefaults();
			opcoesFetch.limit(params.getRegistrosPorPagina());
			opcoesFetch.offset(params.getRegistrosPorPagina() * (params.getNumeroDaPagina() - 1));
			return pquery.asIterable(opcoesFetch);
		}
		
		return pquery.asIterable();
	}
	
	protected <T extends Entidade> Iterable<Entity> lista(Class<T> clazz, Integer registrosPorPagina,
			Integer numeroDaPagina, EntidadeOrdenacao... ordenacao) {
		Query query = new Query(clazz.getName());
		if (ordenacao!=null) {
			for (EntidadeOrdenacao o : ordenacao) {
				query.addSort(o.getAtributo(), o.getOrdenacao());
			}
		}

		PreparedQuery pquery = this.datastore.prepare(query);
		if (registrosPorPagina != null && numeroDaPagina != null) {
			FetchOptions opcoesFetch = FetchOptions.Builder.withDefaults();
			opcoesFetch.limit(registrosPorPagina);
			opcoesFetch.offset(registrosPorPagina * (numeroDaPagina - 1));
			return pquery.asIterable(opcoesFetch);
		}

		return pquery.asIterable();
	}

	protected <T extends Entidade> Iterable<Entity> obterPor(Class<T> clazz, List<String> campos, List<Object> valores) {
		if (valores != null && campos != null && valores.size() == campos.size()) {
			Query query = new Query(clazz.getName());
			setFilters(campos, valores, query);
			PreparedQuery pquery = this.datastore.prepare(query);
	
			return pquery.asIterable();
		}
		
		return null;
	}

	public Iterable<Entity> paginar(EntidadeBusca entidadeBusca) {
		Query query = new Query(entidadeBusca.clazz);

		Filter filter = new FilterPredicate(entidadeBusca.campo, entidadeBusca.filtro, entidadeBusca.data);
		query.setFilter(filter);
		query.addSort(entidadeBusca.campo, entidadeBusca.direcaoOrdenacao);
		PreparedQuery pquery = this.datastore.prepare(query);

		FetchOptions opcoesFetch = FetchOptions.Builder.withDefaults();
		if (entidadeBusca.isLimite()) {
			opcoesFetch.limit(entidadeBusca.limite);
		}
		return pquery.asIterable(opcoesFetch);
	}

	public <T extends Entidade> Iterable<Entity> obterKeyGoogleGrupos() {
		Query query = new Query("br.com.dextra.dextranet.area.Area");
		PreparedQuery pquery = this.datastore.prepare(query);
		return pquery.asIterable();
	}

	private void setFilters(List<String> campos, List<Object> valores, Query query) {
		List<Filter> filters = new ArrayList<Query.Filter>();
		for (int cont = 0; cont < campos.size(); cont++) {
			Filter filter = new FilterPredicate(campos.get(cont), FilterOperator.EQUAL, valores.get(cont));
			if (valores.size() > 1) {
				filters.add(filter);
			} else {
				query.setFilter(filter);
			}
		}
		
		if (valores.size() > 1) {
			CompositeFilter compositeFilter = new Query.CompositeFilter(CompositeFilterOperator.AND, filters);
			query.setFilter(compositeFilter);
		}
	}

}