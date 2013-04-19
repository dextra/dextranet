package br.com.dextra.dextranet.post.comment;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeRepository;
import br.com.dextra.dextranet.post.PostFields;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class CommentRepository extends EntidadeRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Comment criar(Comment comment) {
		this.persiste(comment);

		// TODO: indexacao do post que foi comentado com conteudo do comment

		return comment;
	}

	public List<Comment> listarCommentsDeUmPost(String idReference) {

		Query query = new Query(Comment.class.getName());
		query.setFilter(FilterOperator.EQUAL.of(CommentFields.ID_REFERENCE.getField(), idReference));
		query.addSort(CommentFields.DATA_DE_CRIACAO.getField(), SortDirection.ASCENDING);

		PreparedQuery prepared = datastore.prepare(query);
		return toListaDeComments(prepared.asIterable());
	}

	private List<Comment> toListaDeComments(Iterable<Entity> asIterable) {
		List<Comment> listaDeComments = new ArrayList<Comment>();

		for (Entity entity : asIterable) {
			listaDeComments.add(new Comment(entity));
		}

		return listaDeComments;

	}

	public Comment obtemPorId(String id) throws EntityNotFoundException {
		return new Comment(this.obtemPorId(id, Comment.class));
	}

	public void insereUsuarioQueCurtiuNoComment(Comment comment, String usuario) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Comment.class.getName(), comment.getId());
		Entity valueEntity = datastore.get(key);

		valueEntity.setProperty(CommentFields.USER_LIKE.getField(),
				valueEntity.getProperty(CommentFields.USER_LIKE.getField()) + " " + usuario);

		// TODO: Persistir a entidade
		// persiste(valueEntity);

	}

	public void incrementaNumeroDeLikesDaEntityDoComment(String idConteudo) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Comment.class.getName(), idConteudo);
		Entity valueEntity = datastore.get(key);
		int likes = Integer.parseInt(valueEntity.getProperty(CommentFields.LIKES.getField()).toString());
		valueEntity.setProperty(CommentFields.LIKES.getField(), likes + 1);

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}

	public List<Comment> pegaCommentPorId(String idComment) {
		Query query = new Query(Comment.class.getName());
		query.setFilter(FilterOperator.EQUAL.of(CommentFields.ID.getField(), idComment));

		PreparedQuery prepared = datastore.prepare(query);
		return toListaDeComments(prepared.asIterable());
	}

	public void removeUsuarioQueCurtiuNoComment(Comment comment, String usuario) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Comment.class.getName(), comment.getId());
		Entity valueEntity = datastore.get(key);

		String oldUserLikes = (String) valueEntity.getProperty(CommentFields.USER_LIKE.getField());
		valueEntity.setProperty(CommentFields.USER_LIKE.getField(), oldUserLikes.replaceAll(" " + usuario, ""));

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}

	public void decrementaNumeroDeLikesDaEntityDoComment(Comment comment) throws EntityNotFoundException {
		Key key = KeyFactory.createKey(Comment.class.getName(), comment.getId());
		Entity valueEntity = datastore.get(key);
		int likes = Integer.parseInt(valueEntity.getProperty(PostFields.LIKES.getField()).toString());
		valueEntity.setProperty(PostFields.LIKES.getField(), likes - 1);

		// TODO: Persistir a entidade
		// persiste(valueEntity);
	}
}
