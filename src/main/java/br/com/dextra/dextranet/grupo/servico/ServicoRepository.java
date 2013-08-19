package br.com.dextra.dextranet.grupo.servico;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ServicoRepository extends EntidadeRepository {

	public Servico persiste(Servico servico) {
		return super.persiste(servico);
	}

	public void remove(String id) {
		super.remove(id, Servico.class);
	}

	public Servico obtemPorId(String id) throws EntityNotFoundException {
		Entity servico = super.obtemPorId(id, Servico.class);
		return new Servico(servico);
	}

	public List<Servico> lista() {
		EntidadeOrdenacao ordenacaoPorNome = new EntidadeOrdenacao(ServicoFields.nome.name(), SortDirection.ASCENDING);
		List<Servico> servicos = new ArrayList<Servico>();

		Iterable<Entity> entidades = super.lista(Servico.class, ordenacaoPorNome);
		for (Entity entidade : entidades) {
			servicos.add(new Servico(entidade));
		}

		return servicos;
	}

}
