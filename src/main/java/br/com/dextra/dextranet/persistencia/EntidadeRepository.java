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

}