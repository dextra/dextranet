package br.com.dextra.dextranet.usuario;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class UsuarioRepository extends EntidadeRepository {

	public Usuario obtemPorId(String id) throws EntityNotFoundException {
		Entity usuarioEntity = this.obtemPorId(id, Usuario.class);
		return new Usuario(usuarioEntity);
	}

	public void remove(String id) {
		this.remove(id, Usuario.class);
	}

	public List<Usuario> lista() {
		EntidadeOrdenacao ordenacaoPorUsername = new EntidadeOrdenacao(UsuarioFields.username.toString(),
				SortDirection.ASCENDING);
		List<Usuario> usuarios = new ArrayList<Usuario>();

		Iterable<Entity> entidades = super.lista(Usuario.class, ordenacaoPorUsername);
		for (Entity entidade : entidades) {
			usuarios.add(new Usuario(entidade));
		}

		return usuarios;
	}

}
