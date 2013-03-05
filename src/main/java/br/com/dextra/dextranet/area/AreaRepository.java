package br.com.dextra.dextranet.area;

import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class AreaRepository {
	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public void inserir(Area area) {
		this.datastore.put(area.toEntity());
	}

	public Area buscar(String id) {
		Entity entity;
		try {
			entity = this.datastore.get(KeyFactory.createKey(
					Area.class.getName(), id));
			return new Area(entity);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Area> buscarTodos() {
		List<Area> lista = new ArrayList<Area>();
		Query query = new Query(Area.class.getName());

		query.addSort("name", SortDirection.ASCENDING);

		PreparedQuery pquery = datastore.prepare(query);
		Iterable<Entity> asIterable = pquery.asIterable();
		for (Entity entity : asIterable) {
			Area novaArea = new Area(entity);
			lista.add(novaArea);
		}
		return lista;
	}


	public void excluir(String id) throws EntityNotFoundException {
			this.datastore
					.delete(KeyFactory.createKey(Area.class.getName(), id));
		}
}
