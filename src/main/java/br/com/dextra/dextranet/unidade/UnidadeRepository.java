package br.com.dextra.dextranet.unidade;

import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class UnidadeRepository {
	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public void inserir(Unidade Unidade) {
		this.datastore.put(Unidade.toEntity());
	}

	public Unidade buscar(String id) {
		Entity entity;
		try {
			entity = this.datastore.get(KeyFactory.createKey(
					Unidade.class.getName(), id));
			return new Unidade(entity);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Unidade> buscarTodos() {
		List<Unidade> lista = new ArrayList<Unidade>();
		Query query = new Query(Unidade.class.getName());

		query.addSort("name", SortDirection.ASCENDING);

		PreparedQuery pquery = datastore.prepare(query);
		Iterable<Entity> asIterable = pquery.asIterable();
		for (Entity entity : asIterable) {
			Unidade novaUnidade = new Unidade(entity);
			lista.add(novaUnidade);
		}
		return lista;
	}
}