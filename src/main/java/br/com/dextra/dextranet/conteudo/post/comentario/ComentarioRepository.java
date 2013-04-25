package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class ComentarioRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Comentario.class);
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

}
