package br.com.dextra.dextranet.post;

import java.util.ArrayList;
import java.util.Collections;

import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.utils.Converters;
import br.com.dextra.utils.IndexFacade;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;

public class PostRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public Post criar(Post post) {
		this.persist(post.toEntity());
		DocumentRepository respositoryDocument = new DocumentRepository();
		respositoryDocument.indexar(post, Post.class);

		return post;
	}

	public Post obtemPorId(String id) throws EntityNotFoundException {
		return new Post(this.obtemPorId(id, Post.class));
	}

	public ArrayList<Post> buscarTodosOsPosts(int maxResults, int offSet) {

		Query query = new Query(Post.class.getName());

		query.addSort(PostFields.DATA_DE_ATUALIZACAO.getField(),
				SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		return toListaDePost(prepared.asIterable(opts));
	}

	private ArrayList<Post> toListaDePost(Iterable<Entity> asIterable) {

		ArrayList<Post> listaDePost = new ArrayList<Post>();

		for (Entity entity : asIterable) {
			listaDePost.add(new Post(entity));
		}

		return listaDePost;

	}

	public ArrayList<Post> buscarPosts(int maxResults, String q, int offset)
			throws EntityNotFoundException {

		ArrayList<String> listaDeIds = buscaIdsPostsFTS(maxResults, q, offset);

		ArrayList<Post> listaResults = buscaEntitiesPost(listaDeIds);

		return listaResults;
	}

	private ArrayList<Post> buscaEntitiesPost(ArrayList<String> listaDeIds)
			throws EntityNotFoundException {
		ArrayList<Post> listaResults = new ArrayList<Post>();

		for (String id : listaDeIds) {
			Key key = KeyFactory.createKey(Post.class.getName(), id);
			Entity e = datastore.get(key);
			listaResults.add(new Post(e));
		}
		return listaResults;
	}

	public ArrayList<String> buscaIdsPostsFTS(int maxResults, String q,
			int offset) {

		com.google.appengine.api.search.Query query = preparaQuery(q);

		ArrayList<String> listaDeIds = Converters.toListaDeIds(IndexFacade
				.getIndex(Post.class.getName()).search(query));

		// FIXME: Set limit dentro da query nao funciona
		return listaDeIdsParaMostrarComOffsetForcado(maxResults, offset,
				listaDeIds);
	}

	private ArrayList<String> listaDeIdsParaMostrarComOffsetForcado(
			int maxResults, int offset, ArrayList<String> listaDeIds) {
		Collections.reverse(listaDeIds);
		ArrayList<String> arrayTemp = new ArrayList<String>();
		int f = maxResults + offset;

		if (maxResults + offset > listaDeIds.size())
			f = listaDeIds.size();

		if (offset > listaDeIds.size())
			offset = listaDeIds.size();
		for (String string : listaDeIds.subList(offset, f)) {
			arrayTemp.add(string);
		}

		listaDeIds = arrayTemp;

		return listaDeIds;
	}

	private com.google.appengine.api.search.Query preparaQuery(String q) {

		SortOptions sortOptions = SortOptions.newBuilder().addSortExpression(
				SortExpression.newBuilder().setExpression(
						PostFields.DATA_DE_ATUALIZACAO.getField())
						.setDirection(SortExpression.SortDirection.ASCENDING)
						.setDefaultValue("0")).build();

		QueryOptions queryOptions = QueryOptions.newBuilder().setSortOptions(
				sortOptions).setFieldsToReturn(PostFields.ID.getField(),
				PostFields.DATA_DE_ATUALIZACAO.getField(),
				PostFields.TITULO.getField()).setLimit(1000).build();

		com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query
				.newBuilder().setOptions(queryOptions).build(q);
		return query;
	}

	public void alteraDatadaEntity(String id, String data)
			throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), id);
		Entity valueEntity = datastore.get(key);
		valueEntity
				.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), data);

		persist(valueEntity);
	}

	public void incrementaNumeroDeComentariosDaEntityDoPost(String id)
			throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), id);
		Entity valueEntity = datastore.get(key);
		int comments = Integer.parseInt(valueEntity.getProperty(
				PostFields.COMENTARIO.getField()).toString());
		valueEntity.setProperty(PostFields.COMENTARIO.getField(), comments + 1);
		persist(valueEntity);
	}

	public void remove(String id) {
		Key key = KeyFactory.createKey(Post.class.getName(), id);

		try {
			datastore.delete(key);
		} finally {
			DocumentRepository indexacao = new DocumentRepository();
			indexacao.removeIndex(Post.class.getName(), id);
		}
	}

}
