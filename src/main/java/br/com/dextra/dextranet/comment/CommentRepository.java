package br.com.dextra.dextranet.comment;

import br.com.dextra.dextranet.persistencia.BaseRepository;
import br.com.dextra.dextranet.post.Post;
import br.com.dextra.repository.document.DocumentRepository;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class CommentRepository extends BaseRepository  {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Comment criar(Comment comment) {
		this.persist(comment.toEntity());
		DocumentRepository repositoryDocument = new DocumentRepository();
		repositoryDocument.indexar(comment, Comment.class);
		return comment;

	}

	public Comment obtemPorId(String id) throws EntityNotFoundException {
		return new Comment(this.obtemPorId(id, Post.class));
	}

	public Iterable<Entity> buscar(){


		Query query = new Query(Comment.class.getName());
		PreparedQuery prepared = datastore.prepare(query);

		return prepared.asIterable();
	}



}
