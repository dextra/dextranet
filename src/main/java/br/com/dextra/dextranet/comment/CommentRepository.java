package br.com.dextra.dextranet.comment;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.curtida.Curtida;
import br.com.dextra.dextranet.persistencia.BaseRepository;

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

public class CommentRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Comment criar(Comment comment) {
		this.persist(comment.toEntity());

		// TODO: indexação do post que foi comentado com conteudo do comment
		// FIXME: arrumar o set do document ja existente
		// DocumentRepository respositoryDocument = new DocumentRepository();
		// respositoryDocument.indexar(comment, Comment.class);

		return comment;
	}

	@SuppressWarnings("deprecation")
	public List<Comment> listarCommentsDeUmPost(String IdReference) {

		Query query = new Query(Comment.class.getName());

		query.addFilter(CommentFields.ID_REFERENCE.getField(), FilterOperator.EQUAL, IdReference);

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

	public void insereUsuarioQueCurtiuNoComment(Curtida curtida, Comment comment) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Comment.class.getName(), curtida.getIdConteudo());
		Entity valueEntity = datastore.get(key);

		valueEntity.setProperty(CommentFields.USER_LIKE.getField(),
				valueEntity.getProperty(CommentFields.USER_LIKE.getField()) + " " + curtida.getUsuarioLogado());

		persist(valueEntity);

	}

	public void incrementaNumeroDeLikesDaEntityDoComment(Curtida curtida) throws EntityNotFoundException {

		Key key = KeyFactory.createKey(Comment.class.getName(), curtida.getIdConteudo());
		Entity valueEntity = datastore.get(key);
		int likes = Integer.parseInt(valueEntity.getProperty(CommentFields.LIKES.getField()).toString());
		valueEntity.setProperty(CommentFields.LIKES.getField(), likes + 1);
		persist(valueEntity);

	}

	@SuppressWarnings("deprecation")
	public List<Comment> pegaCommentPorId(String idComment) {

		Query query = new Query(Comment.class.getName());

		query.addFilter(CommentFields.ID.getField(), FilterOperator.EQUAL, idComment);

		PreparedQuery prepared = datastore.prepare(query);

		return toListaDeComments(prepared.asIterable());

	}

}
