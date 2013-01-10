package br.com.dextra.dextranet.comment;

import java.util.ArrayList;

import br.com.dextra.dextranet.document.DocumentRepository;
import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostFields;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class CommentRepository extends BaseRepository {

	private DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	public Comment criar(Comment comment) {
		this.persist(comment.toEntity());
		DocumentRepository respositoryDocument = new DocumentRepository();
		respositoryDocument.indexar(comment, Comment.class);

		return comment;
	}

	@SuppressWarnings("deprecation")
	public ArrayList<Comment> listarCommentsDeUmPost(int maxResults,
			int offSet, Post post) {

		Query query = new Query(Comment.class.getName());

		query.addSort(PostFields.DATA_DE_ATUALIZACAO.getField(),
				SortDirection.DESCENDING);
		query.addFilter(CommentFields.ID_REFERENCE.getField(),
				FilterOperator.EQUAL, post.getId());
		PreparedQuery prepared = datastore.prepare(query);

		FetchOptions opts = FetchOptions.Builder.withDefaults();
		opts.limit(maxResults);
		opts.offset(offSet);

		return toListaDeComments(prepared.asIterable(opts));
	}

	private ArrayList<Comment> toListaDeComments(Iterable<Entity> asIterable) {

		ArrayList<Comment> listaDeComments = new ArrayList<Comment>();

		for (Entity entity : asIterable) {
			listaDeComments.add(new Comment(entity));
		}

		return listaDeComments;

	}

	public Comment obtemPorId(String id) throws EntityNotFoundException {
		return new Comment(this.obtemPorId(id, Comment.class));
	}

}
