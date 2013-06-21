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
		String[] valores = {idGrupo};
		String[] campos = {MembroFields.idGrupo.name()};
		Iterable<Entity> entidades = super.obterPor(Membro.class, campos, valores);

		List<Membro> membros = new ArrayList<Membro>();
		Membro membro = null;
		for (Entity entity : entidades) {
			membro = new Membro(entity);
			membros.add(membro);
		}
		return membros;
	}

	public Membro obtemPor(String idUsuario, String idGrupo) throws EntityNotFoundException {
		String[] campos = {MembroFields.idGrupo.name(), MembroFields.idUsuario.name()};
		String[] valores = {idGrupo, idUsuario};
		Iterable<Entity> entidades = super.obterPor(Membro.class, campos, valores);
		if (entidades !=  null && entidades.iterator().hasNext()) {
			return new Membro(entidades.iterator().next());
		}
		return null;
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
