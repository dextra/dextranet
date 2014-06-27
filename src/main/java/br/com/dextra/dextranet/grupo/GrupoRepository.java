package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class GrupoRepository extends EntidadeRepository {
	public Grupo persiste(Grupo grupo) {
		return super.persiste(grupo);
	}

	public Grupo obtemPorId(String id) throws EntityNotFoundException {
		Entity grupoEntity = this.obtemPorId(id, Grupo.class);
		return new Grupo(grupoEntity);
	}

	public List<Grupo> obtemPorNickUsuario(String proprietario) throws EntityNotFoundException {		
        Query query = new Query(Grupo.class.getName());
        query.setFilter(new FilterPredicate(GrupoFields.proprietario.name(), FilterOperator.EQUAL, proprietario));
        PreparedQuery pquery = this.datastore.prepare(query);
        
        List<Grupo> grupos = new ArrayList<Grupo>();
        Iterable<Entity> entidades = pquery.asIterable();
        for (Entity entidade : entidades) {
			grupos.add(new Grupo(entidade));
		}
        
		return grupos;
	}
	
	public void remove(String id) {
		this.remove(id, Grupo.class);
	}

	public List<Grupo> lista() {
		EntidadeOrdenacao ordenacaoPorNome = new EntidadeOrdenacao(GrupoFields.nome.name(), SortDirection.ASCENDING);
		List<Grupo> grupos = new ArrayList<Grupo>();

		Iterable<Entity> entidades = super.lista(Grupo.class, ordenacaoPorNome);
		for (Entity entidade : entidades) {
			grupos.add(new Grupo(entidade));
		}

		return grupos;
	}
}
