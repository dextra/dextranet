package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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

	protected <T extends Entidade> Iterable<Entity> obterPor(Class<T> clazz, String[] campos, String[] valores) {
		Query query = new Query(clazz.getName());
		for (int cont = 0; cont < campos.length; cont++) {
			Filter filter = new FilterPredicate(campos[cont], FilterOperator.EQUAL, valores[cont]);
			query.setFilter(filter);
		}
		PreparedQuery pquery = this.datastore.prepare(query);

		return pquery.asIterable();
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
//		for (int cont = 0; cont < campos.length; cont++) {
//			Filter filter = new FilterPredicate(campos[cont], FilterOperator.EQUAL, valores[cont]);
//			query.setFilter(filter);
//		}
		PreparedQuery pquery = this.datastore.prepare(query);

		return pquery.asIterable();
	}

}