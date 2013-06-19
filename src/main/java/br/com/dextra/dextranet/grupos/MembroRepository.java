package br.com.dextra.dextranet.grupos;

import br.com.dextra.dextranet.persistencia.EntidadeRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class MembroRepository extends EntidadeRepository {
	public Membro persiste(Membro membro) {
		return super.persiste(membro);
	}

	public Membro obtemPorId(String id) throws EntityNotFoundException {
		Entity grupoEntity = this.obtemPorId(id, Grupo.class);
		return new Membro(grupoEntity);
	}

	public void remove(String id) {
		this.remove(id, Membro.class);
	}
}
