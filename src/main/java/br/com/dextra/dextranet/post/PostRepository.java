package br.com.dextra.dextranet.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.comment.CommentFields;
import br.com.dextra.dextranet.curtida.Curtida;
import br.com.dextra.dextranet.document.DocumentRepository;
import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.utils.Converters;
import br.com.dextra.dextranet.utils.IndexFacade;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
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

	public List<Post> buscarTodosOsPosts(int maxResults, int offSet) {

		Query query = new Query(Post.class.getName());

		query.addSort(PostFields.DATA_DE_ATUALIZACAO.getField(),
				SortDirection.DESCENDING);
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		return toListaDePost(prepared.asIterable(opts));
	}

	private List<Post> toListaDePost(Iterable<Entity> asIterable) {

		List<Post> listaDePost = new ArrayList<Post>();

		for (Entity entity : asIterable) {
			listaDePost.add(new Post(entity));
		}

		return listaDePost;

	}

	public List<Post> buscarPosts(int maxResults, String q, int offset)
			throws EntityNotFoundException {

		List<String> listaDeIds = buscaIdsPostsFTS(maxResults, q, offset);

		List<Post> listaResults = buscaEntitiesPost(listaDeIds);

		return listaResults;
	}

	private List<Post> buscaEntitiesPost(List<String> listaDeIds)
			throws EntityNotFoundException {
		List<Post> listaResults = new ArrayList<Post>();

		List<Key> listaDeKeys = (geraIterableDeKeys(listaDeIds));

		Map<Key, Entity> mapa = datastore.get(listaDeKeys);

		for (Key key : listaDeKeys) {
			listaResults.add(new Post(mapa.get(key)));
		}

		return listaResults;
	}

	private List<Key> geraIterableDeKeys(List<String> listaDeIds) {
		List<Key> listaDeKeys = new ArrayList<Key>();

		for (String id : listaDeIds) {
			listaDeKeys.add(KeyFactory.createKey(Post.class.getName(), id));
		}

		return listaDeKeys;
	}

	public List<String> buscaIdsPostsFTS(int maxResults, String q, int offset) {

		com.google.appengine.api.search.Query query = preparaQuery(q);

		List<String> listaDeIds = new Converters().toListaDeIds(IndexFacade
				.getIndex(Post.class.getName()).search(query));

		// FIXME: Set limit dentro da query nao funciona
		return listaDeIdsParaMostrarComOffsetForcado(maxResults, offset,
				listaDeIds);
	}

	private List<String> listaDeIdsParaMostrarComOffsetForcado(int maxResults,
			int offset, List<String> listaDeIds) {
		Collections.reverse(listaDeIds);
		List<String> arrayTemp = new ArrayList<String>();
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
				sortOptions).setFieldsToSnippet(PostFields.USUARIO.getField(),
				PostFields.TITULO.getField()).setFieldsToReturn(
				PostFields.ID.getField(),
				PostFields.DATA_DE_ATUALIZACAO.getField(),
				PostFields.TITULO.getField()).setLimit(1000).build();

		com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query
				.newBuilder().setOptions(queryOptions).build(q);
		return query;
	}

	void alteraDataDaEntity(Comment comment) throws EntityNotFoundException {
		alteraDataDaEntity(comment.getIdReference(), comment.getDataDeCriacao());
	}

	void alteraDataDaEntity(Curtida curtida) throws EntityNotFoundException {
		alteraDataDaEntity(curtida.getIdPost(), curtida.getData());
	}

	void alteraDataDaEntity(String id, String data)
			throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), id);
		Entity valueEntity = datastore.get(key);
		valueEntity
				.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), data);

		persist(valueEntity);
	}

	void incrementaNumeroDeComentariosDaEntityDoPost(String id)
			throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), id);
		Entity valueEntity = datastore.get(key);
		int comments = Integer.parseInt(valueEntity.getProperty(
				PostFields.COMENTARIO.getField()).toString());
		valueEntity.setProperty(PostFields.COMENTARIO.getField(), comments + 1);
		persist(valueEntity);
	}

	public void incrementaNumeroDeLikesDaEntityDoPost(Curtida curtida)
			throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), curtida
				.getIdPost());
		Entity valueEntity = datastore.get(key);
		int likes = Integer.parseInt(valueEntity.getProperty(
				PostFields.LIKES.getField()).toString());
		valueEntity.setProperty(PostFields.LIKES.getField(), likes + 1);
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

	public void alteraEntity(Comment comment) throws EntityNotFoundException {
		alteraDataDaEntity(comment.getIdReference(), comment.getDataDeCriacao());
		incrementaNumeroDeComentariosDaEntityDoPost(comment.getIdReference());
	}

	@SuppressWarnings("unchecked")
	public void insereUsuarioQueCurtiuNoPost(Curtida curtida, Post post)
			throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), curtida
				.getIdPost());
		Entity valueEntity = datastore.get(key);
		List<String> listaDeUserDoLike = (List<String>) valueEntity
				.getProperty(PostFields.USER_LIKE.getField());
		listaDeUserDoLike.add(curtida.getUsuarioLogado());
		valueEntity.setProperty(PostFields.USER_LIKE.getField(),
				listaDeUserDoLike);

		persist(valueEntity);

	}

	@SuppressWarnings("deprecation")
	public boolean verificaSeOUsuarioJaCurtiuOPost(String id, String user) {

		Query query = new Query(Post.class.getName());
		query.addFilter(PostFields.ID.getField(), FilterOperator.EQUAL, id);
		query.addFilter(PostFields.USER_LIKE.getField(), FilterOperator.IN,
				user);
		query.addSort(CommentFields.DATA_DE_CRIACAO.getField(),
				SortDirection.ASCENDING);

		PreparedQuery prepared = datastore.prepare(query);

		return !toListaDePost(prepared.asIterable()).isEmpty();

	}

}
