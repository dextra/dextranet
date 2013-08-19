package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ServicoGrupoRepository extends EntidadeRepository {
	public ServicoGrupo persiste(ServicoGrupo servicoGrupo) {
		return super.persiste(servicoGrupo);
	}

	public ServicoGrupo obtemPorId(String id) throws EntityNotFoundException {
		Entity servicoGrupoEntity = this.obtemPorId(id, ServicoGrupo.class);
		return new ServicoGrupo(servicoGrupoEntity);
	}

	public List<ServicoGrupo> obtemPorIdGrupo(String idGrupo) throws EntityNotFoundException {
		String[] valores = {idGrupo};
		String[] campos = {ServicoGrupoFields.idGrupo.name()};
		Iterable<Entity> entidades = super.obterPor(ServicoGrupo.class, campos, valores);

		List<ServicoGrupo> servicoGrupos= new ArrayList<ServicoGrupo>();
		ServicoGrupo servico = null;
		for (Entity entity : entidades) {
			servico = new ServicoGrupo(entity);
			servicoGrupos.add(servico);
		}
		return servicoGrupos;
	}

	public ServicoGrupo obtemPor(String idServico, String idGrupo) throws EntityNotFoundException {
		String[] campos = {ServicoGrupoFields.idGrupo.name(), ServicoGrupoFields.idServico.name()};
		String[] valores = {idGrupo, idServico};
		Iterable<Entity> entidades = super.obterPor(ServicoGrupo.class, campos, valores);
		if (entidades !=  null && entidades.iterator().hasNext()) {
			return new ServicoGrupo(entidades.iterator().next());
		}
		return null;
	}

	public List<ServicoGrupo> lista() {
		EntidadeOrdenacao entidadeOrdenacao = new EntidadeOrdenacao(ServicoGrupoFields.id.name(), SortDirection.ASCENDING);
		Iterable<Entity> entidades = super.lista(ServicoGrupo.class, entidadeOrdenacao);
		ServicoGrupo servicoGrupo;
		List<ServicoGrupo> servicos = new ArrayList<ServicoGrupo>();
		for (Entity entity : entidades) {
			servicoGrupo = new ServicoGrupo(entity);
			servicos.add(servicoGrupo);
		}
		return servicos;
	}

	public void remove(String id) {
		this.remove(id, ServicoGrupo.class);
	}
}
