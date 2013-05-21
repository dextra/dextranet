package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.indexacao.IndexacaoRepository;
import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ComentarioRepository extends EntidadeRepository {

	private IndexacaoRepository indexacao = new IndexacaoRepository();

	public Comentario persiste(Comentario comentario) {
		Comentario comentarioPersistido = super.persiste(comentario);
		indexacao.indexar(comentarioPersistido);

		return comentarioPersistido;
	}

	public void remove(String id) {
		super.remove(id, Comentario.class);
		indexacao.removeIndexacao(Comentario.class.getName(), id);
	}

	public void removeComentariosPost(String postId) {
		List<Comentario> comentarios = listaPorPost(postId);
		for (Comentario comentario : comentarios) {
			remove(comentario.getId());
		}
	}

	public Comentario obtemPorId(String id) throws EntityNotFoundException {
		Entity comentario = super.obtemPorId(id, Comentario.class);
		return new Comentario(comentario);
	}

	public List<Comentario> lista(EntidadeOrdenacao... criterioOrdenacao) {
		List<Comentario> comentarios = new ArrayList<Comentario>();

		Iterable<Entity> entidades = super.lista(Comentario.class, criterioOrdenacao);
		for (Entity entidade : entidades) {
			comentarios.add(new Comentario(entidade));
		}

		return comentarios;
	}

	public List<Comentario> listaPorPost(String postId) {
		Filter filtroPorPostId = new Query.FilterPredicate(ComentarioFields.postId.name(), FilterOperator.EQUAL, postId);

		Query query = new Query(Comentario.class.getName());
		query.setFilter(filtroPorPostId);
		query.addSort(ComentarioFields.dataDeCriacao.name(), SortDirection.ASCENDING);

		PreparedQuery pquery = this.datastore.prepare(query);

		List<Comentario> comentarios = new ArrayList<Comentario>();
		Iterable<Entity> entidades = pquery.asIterable();
		for (Entity entidade : entidades) {
			comentarios.add(new Comentario(entidade));
		}

		return comentarios;
	}

}
