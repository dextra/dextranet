package br.com.dextra.dextranet.usuario;

import java.util.ArrayList;

import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;

public class UsuarioRepository extends EntidadeRepository {

	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public Usuario criar(Usuario usuario) {
		this.persiste(usuario);
		return usuario;
	}

	public Usuario novoUsuario() {
		User usuario = UserServiceFactory.getUserService().getCurrentUser();
		// FIXME: Badsmell
		BlobKey blobKey = new BlobKey(
				"AMIfv95w7LRpXBdAGFKNj7zq_SWye9-sG9N6dHEQqUMFwZ6Vz8vA85YvxkpqP5qH4hkTg8tmvMaFIMNpiGrTojZUFD9YURbGqJZ6kjPEEh4uUOtasFYJfKXPvfE1jxlsq2XC0Pnu3xCwpPhCXdmPGSYB5T87SyIHT63E_duDDs6m0dtS8gfv3eU3G2MqykoDhn8jyxvhv5IX");
		return criar(new Usuario(usuario.getUserId(), usuario.getNickname(), usuario.getEmail(), blobKey));
	}

	public Usuario obterUsuarioPorId(String id) {
		Query query = new Query(Usuario.class.getName());

		query.setFilter(FilterOperator.EQUAL.of(UsuarioFields.ID.getField(), id));

		PreparedQuery prepared = datastore.prepare(query);

		return new Usuario(prepared.asSingleEntity());
	}

	public ArrayList<Usuario> getTodosUsuarios() {
		Query query = new Query(Usuario.class.getName());

		PreparedQuery prepared = datastore.prepare(query);

		Iterable<Entity> asIterable = prepared.asIterable();
		ArrayList<Usuario> listaDeUsuarios = new ArrayList<Usuario>();
		if (asIterable != null) {
			for (Entity entity : asIterable) {
				listaDeUsuarios.add(new Usuario(entity));
			}

			return listaDeUsuarios;
		} else
			return null;
	}
}
