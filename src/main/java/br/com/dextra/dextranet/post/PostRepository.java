package br.com.dextra.dextranet.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.document.DocumentRepository;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;
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
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;

public class PostRepository extends EntidadeRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Post criar(Post post) {
		this.persiste(post);
		DocumentRepository respositoryDocument = new DocumentRepository();
		respositoryDocument.indexar(post);

		return post;
	}

	public void registrarCurtida(Post post, String usuario) throws EntityNotFoundException {
		alteraDataDoPost(post.getId(), post.getDataDeAtualizacao());
		insereUsuarioQueCurtiuNoPost(usuario, post);
		incrementaNumeroDeCurtidasDoPost(post);
	}

	public Post obtemPorId(String id) throws EntityNotFoundException {
		return new Post(this.obtemPorId(id, Post.class));
	}

	public List<Post> buscarTodosOsPosts(int maxResults, int offSet) {

		Query query = new Query(Post.class.getName());

		query.addSort(PostFields.DATA_DE_ATUALIZACAO.getField(), SortDirection.DESCENDING);
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

	public List<Post> buscarPosts(int maxResults, String q, int offset) throws EntityNotFoundException {

		List<String> listaDeIds = buscaIdsPostsFTS(maxResults, q, offset);

		List<Post> listaResults = buscaEntitiesPost(listaDeIds);

		return listaResults;
	}

	private List<Post> buscaEntitiesPost(List<String> listaDeIds) throws EntityNotFoundException {
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

		List<String> listaDeIds = Converters.toListaDeIds(IndexFacade.getIndex(Post.class.getName()).search(query));

		// FIXME: Set limit dentro da query nao funciona
		return listaDeIdsParaMostrarComOffsetForcado(maxResults, offset, listaDeIds);
	}

	private List<String> listaDeIdsParaMostrarComOffsetForcado(int maxResults, int offset, List<String> listaDeIds) {
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

		SortOptions sortOptions = SortOptions
				.newBuilder()
				.addSortExpression(
						SortExpression.newBuilder().setExpression(PostFields.DATA_DE_ATUALIZACAO.getField())
								.setDirection(SortExpression.SortDirection.ASCENDING).setDefaultValue("0")).build();

		QueryOptions queryOptions = QueryOptions
				.newBuilder()
				.setSortOptions(sortOptions)
				.setFieldsToSnippet(PostFields.USUARIO.getField(), PostFields.TITULO.getField())
				.setFieldsToReturn(PostFields.ID.getField(), PostFields.DATA_DE_ATUALIZACAO.getField(),
						PostFields.TITULO.getField()).setLimit(1000).build();

		com.google.appengine.api.search.Query query = com.google.appengine.api.search.Query.newBuilder()
				.setOptions(queryOptions).build(q);
		return query;
	}

	void alteraDataDoPost(String idPost, String data) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), idPost);
		Entity valueEntity = datastore.get(key);
		valueEntity.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), data);

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}

	void incrementaNumeroDeComentariosDaEntityDoPost(String id) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), id);
		Entity valueEntity = datastore.get(key);
		int comments = Integer.parseInt(valueEntity.getProperty(PostFields.COMENTARIO.getField()).toString());
		valueEntity.setProperty(PostFields.COMENTARIO.getField(), comments + 1);

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}

	public void incrementaNumeroDeCurtidasDoPost(Post post) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Post.class.getName(), post.getId());
		Entity valueEntity = datastore.get(key);
		int likes = Integer.parseInt(valueEntity.getProperty(PostFields.LIKES.getField()).toString());
		valueEntity.setProperty(PostFields.LIKES.getField(), likes + 1);

		// TODO: Persistir a entidade
		// persiste(valueEntity);
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
		alteraDataDoPost(comment.getIdPost(), comment.getDataDeCriacao());
		incrementaNumeroDeComentariosDaEntityDoPost(comment.getIdPost());
	}

	public void insereUsuarioQueCurtiuNoPost(String usuario, Post post) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Post.class.getName(), post.getId());
		Entity valueEntity = datastore.get(key);

		valueEntity.setProperty(PostFields.USER_LIKE.getField(),
				valueEntity.getProperty(PostFields.USER_LIKE.getField()) + " " + usuario);

		// TODO: Persistir a entidade
		// persiste(valueEntity);

	}

	public void registrarDescurtida(Post post, String usuario) throws EntityNotFoundException {
		removeUsuarioQueDescurtiuPost(usuario, post);
		decrementaNumeroDeCurtidasDoPost(post);
	}

	private void decrementaNumeroDeCurtidasDoPost(Post post) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Post.class.getName(), post.getId());
		Entity valueEntity = datastore.get(key);
		int likes = Integer.parseInt(valueEntity.getProperty(PostFields.LIKES.getField()).toString());
		valueEntity.setProperty(PostFields.LIKES.getField(), likes - 1);

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}

	private void removeUsuarioQueDescurtiuPost(String usuario, Post post) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Post.class.getName(), post.getId());
		Entity valueEntity = datastore.get(key);

		String oldUserLikes = (String) valueEntity.getProperty(PostFields.USER_LIKE.getField());
		valueEntity.setProperty(PostFields.USER_LIKE.getField(), oldUserLikes.replaceAll(" " + usuario, ""));

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}
}
