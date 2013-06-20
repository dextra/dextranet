package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class MembroRepository extends EntidadeRepository {
	public Membro persiste(Membro membro) {
		return super.persiste(membro);
	}

	public Membro obtemPorId(String id) throws EntityNotFoundException {
		Entity membroEntity = this.obtemPorId(id, Membro.class);
		return new Membro(membroEntity);
	}

	public List<Membro> obtemPorIdGrupo(String idGrupo) throws EntityNotFoundException {
		Iterable<Entity> entidades = super.obterPor(Membro.class, MembroFields.idGrupo.name(), idGrupo);

		List<Membro> membros = new ArrayList<Membro>();
		Membro membro = null;
		for (Entity entity : entidades) {
			membro = new Membro(entity);
			membros.add(membro);
		}
		return membros;
	}

	public List<Membro> lista() {
		EntidadeOrdenacao entidadeOrdenacao = new EntidadeOrdenacao(MembroFields.id.name(), SortDirection.ASCENDING);
		Iterable<Entity> entidades = super.lista(Membro.class, entidadeOrdenacao);
		Membro membro;
		List<Membro> membros = new ArrayList<Membro>();
		for (Entity entity : entidades) {
			membro = new Membro(entity);
			membros.add(membro);
		}
		return membros;
	}

	public void remove(String id) {
		this.remove(id, Membro.class);
	}
}
