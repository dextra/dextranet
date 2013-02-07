package br.com.dextra.dextranet.perfil;

import java.util.ArrayList;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class PerfilRepository {
	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public void novo(Perfil Perfil) {
		if (Perfil.isValido())
			this.datastore.put(Perfil.toEntity());
	}

	public Perfil buscar(String id) {
		Entity entity;
		try {
			entity = this.datastore.get(KeyFactory.createKey(
					Perfil.class.getName(), id));
			return new Perfil(entity);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Perfil> obterTodos() {
		List<Perfil> buscados = new ArrayList<Perfil>();
		Query query = new Query(Perfil.class.getName());

		query.addSort("nickName", SortDirection.ASCENDING);

		PreparedQuery pquery = datastore.prepare(query);
		Iterable<Entity> asIterable = pquery.asIterable();
		for (Entity entity : asIterable) {
			Perfil novoPerfil = new Perfil(entity);
			buscados.add(novoPerfil);
		}
		return buscados;

	}

	public List<Perfil> obterPorInicial(char inicial) {
		List<Perfil> buscados = new ArrayList<Perfil>();
		char Inicial = Character.toUpperCase(inicial);
		Query query = new Query(Perfil.class.getName());
		query.addSort("name", SortDirection.ASCENDING);
		PreparedQuery pquery = datastore.prepare(query);
		Iterable<Entity> asIterable = pquery.asIterable();
		for (Entity entity : asIterable) {
			Perfil novoPerfil = new Perfil(entity);
			if (novoPerfil.getName().charAt(0) == Inicial
					|| novoPerfil.getName().charAt(0) == inicial) {
				buscados.add(novoPerfil);
			}
		}
		return buscados;
	}

	public List<Perfil> obterPorNome(String name) {
		List<Perfil> buscados = new ArrayList<Perfil>();
		Query query = new Query(Perfil.class.getName());
		query.addSort("name", SortDirection.ASCENDING);
		List<Filter> filters = new ArrayList<Query.Filter>();
		filters.add(new FilterPredicate("name",
				FilterOperator.GREATER_THAN_OR_EQUAL, name));
		filters.add(new FilterPredicate("name", FilterOperator.LESS_THAN, name
				+ "/ufffd"));
		Filter filter = CompositeFilterOperator.and(filters);
		query = query.setFilter(filter);
		// Filter filter = new FilterPredicate("name", FilterOperator.EQUAL,
		// name);
		// query.setFilter(filter);
		PreparedQuery pquery = datastore.prepare(query);
		Iterable<Entity> asIterable = pquery.asIterable();
		for (Entity entity : asIterable) {
			Perfil novoPerfil = new Perfil(entity);
			buscados.add(novoPerfil);
		}
		return buscados;
	}

}