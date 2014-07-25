package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;
import br.com.dextra.dextranet.usuario.Usuario;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class GrupoRepository extends EntidadeRepository {
	MembroRepository membroRepository = new MembroRepository();
	ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();
	
	public Grupo persiste(Grupo grupo) {
		return super.persiste(grupo);
	}

	public Grupo obtemPorId(String id) throws EntityNotFoundException {
		Entity grupoEntity = this.obtemPorId(id, Grupo.class);
		return new Grupo(grupoEntity);
	}

	public List<Grupo> obtemPorNickProprietario(String proprietario) throws EntityNotFoundException {		
        Query query = new Query(Grupo.class.getName());
        query.setFilter(new FilterPredicate(GrupoFields.proprietario.name(), FilterOperator.EQUAL, proprietario));
        PreparedQuery pquery = this.datastore.prepare(query);
        
        List<Grupo> grupos = new ArrayList<Grupo>();
        Iterable<Entity> entidades = pquery.asIterable();
        for (Entity entidade : entidades) {
			grupos.add(new Grupo(entidade));
		}
        
		return grupos;
	}

	public List<Grupo> obtemPorListaId(List<String> idGrupos) throws EntityNotFoundException {
		List<Grupo> grupos = new ArrayList<Grupo>();
		
		if (!idGrupos.isEmpty()) {
			Query query = new Query(Grupo.class.getName());
			query.setFilter(new FilterPredicate(GrupoFields.id.name(), FilterOperator.IN, idGrupos));
			PreparedQuery pquery = this.datastore.prepare(query);
	        Iterable<Entity> entidades = pquery.asIterable();
	
	        for (Entity entidade : entidades) {
	        	Grupo grupo = new Grupo(entidade);
	        	List<Membro> membros = membroRepository.obtemPorIdGrupo(grupo.getId());
	        	List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
	        	grupo.setServicoGrupos(servicoGrupos);
	        	grupo.setMembros(membros);
				grupos.add(grupo);
			}
		}
		return grupos;
	}

	public List<Grupo> obtemPorIdIntegrante(String idUsuario) throws EntityNotFoundException {
		List<Membro> relacoesUsuarioGrupo = membroRepository.obtemPorIdUsuario(idUsuario);
		List<String> idGrupos = new ArrayList<String>();
		for (Membro relUsuarioGrupo : relacoesUsuarioGrupo) {
			idGrupos.add(relUsuarioGrupo.getIdGrupo());
		}
		
		List<Grupo> grupos = obtemPorListaId(idGrupos);
		return grupos;
	}
	
	public void remove(String id) {
		this.remove(id, Grupo.class);
	}

	public List<Grupo> lista() {
		EntidadeOrdenacao ordenacaoPorNome = new EntidadeOrdenacao(GrupoFields.nome.name(), SortDirection.ASCENDING);
		List<Grupo> grupos = new ArrayList<Grupo>();

		Iterable<Entity> entidades = super.lista(Grupo.class, ordenacaoPorNome);
		for (Entity entidade : entidades) {
			grupos.add(new Grupo(entidade));
		}

		return grupos;
	}

	public void ajustarProprietarioGrupo(Usuario usuario) throws EntityNotFoundException {
		List<Grupo> grupos = obtemPorIdIntegrante(usuario.getId());
		
		for (Grupo grupo : grupos) {
			if (grupo.getProprietario().equals(usuario.getUsername())) {
				List<Membro> membros = grupo.getMembros();
				String usernamedeOutroMembro = getUsernameOutroMembro(usuario, membros);
				if (!StringUtils.isEmpty(usernamedeOutroMembro)) {
					grupo.comProprietario(usernamedeOutroMembro);
					persiste(grupo);
				}
			}
		}
	}

	private String getUsernameOutroMembro(Usuario usuario, List<Membro> membros) {
		for (Membro membro : membros) {
			String username = membro.getEmail();
			if (!username.equals(usuario.getUsername())) {
				return username;
			}
		}
		return null;
	}
}
