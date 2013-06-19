package br.com.dextra.dextranet.grupos;

import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class MembroRepository extends EntidadeRepository {
	public Membro persiste(Membro membro) {
		return super.persiste(membro);
	}

	public Membro obtemPorId(String id) throws EntityNotFoundException {
		Entity membroEntity = this.obtemPorId(id, Membro.class);
		return new Membro(membroEntity);
	}

	public List<Membro> obtemPorIdGrupo(String idGrupo) throws EntityNotFoundException {
		Entity membros = this.obtemPorId(idGrupo, Membro.class);
		return null;
	}

	public List<Membro> lista() {

		return null;
	}

	public void remove(String id) {
		this.remove(id, Membro.class);
	}
}
