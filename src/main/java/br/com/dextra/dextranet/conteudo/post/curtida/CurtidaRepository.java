package br.com.dextra.dextranet.conteudo.post.curtida;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class CurtidaRepository extends EntidadeRepository {

	public void remove(String id) {
		super.remove(id, Curtida.class);
	}

	public Curtida obtemPorId(String id) throws EntityNotFoundException {
		Entity curtida = super.obtemPorId(id, Curtida.class);
		return new Curtida(curtida);
	}

	public List<Curtida> lista(EntidadeOrdenacao... criterioOrdenacao) {
		List<Curtida> curtidas = new ArrayList<Curtida>();

		Iterable<Entity> entidades = super.lista(Curtida.class, criterioOrdenacao);
		for (Entity entidade : entidades) {
			curtidas.add(new Curtida(entidade));
		}

		return curtidas;
	}

}
