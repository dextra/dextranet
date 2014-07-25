package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;
import br.com.dextra.dextranet.usuario.Usuario;

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
		List<String> campos = new ArrayList<String>();
		campos.add(MembroFields.idGrupo.name());
	
		List<Object> valores = new ArrayList<Object>(); 
		valores.add(idGrupo);
		
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
		List<String> campos = new ArrayList<String>();
		campos.add(MembroFields.idGrupo.name());
		campos.add(MembroFields.idUsuario.name());
		
		List<Object> valores = new ArrayList<Object>(); 
		valores.add(idGrupo);
		valores.add(idUsuario);
		Iterable<Entity> entidades = super.obterPor(Membro.class, campos, valores);
		if (entidades !=  null && entidades.iterator().hasNext()) {
			return new Membro(entidades.iterator().next());
		}
		return null;
	}

	public List<Membro> obtemPorIdUsuario(String idUsuario) throws EntityNotFoundException {
		List<String> campos = new ArrayList<String>();
		campos.add(MembroFields.idUsuario.name());
		
		List<Object> valores = new ArrayList<Object>(); 
		valores.add(idUsuario);
		
		Iterable<Entity> entidades = super.obterPor(Membro.class, campos, valores);

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
	
	public void removeMembroDosGruposPor(Usuario usuario) throws EntityNotFoundException {
		List<Membro> membros = obtemPorIdUsuario(usuario.getId());
		for (Membro membro : membros) {
			remove(membro.getId());
		}
	}
}
