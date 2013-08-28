package br.com.dextra.dextranet.usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoJSON;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.persistencia.EntidadeRepository;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class UsuarioRepository extends EntidadeRepository {
	GrupoRepository repositorio = new GrupoRepository();
	MembroRepository repositorioMembro = new MembroRepository();

    public Usuario persiste(Usuario usuario) {
        return super.persiste(usuario);
    }

    public Usuario obtemPorId(String id) throws EntityNotFoundException {
        Entity usuarioEntity = this.obtemPorId(id, Usuario.class);
        return new Usuario(usuarioEntity);
    }

    public void remove(String id) {
        this.remove(id, Usuario.class);
    }

    public List<Usuario> lista() throws EntityNotFoundException {
        EntidadeOrdenacao ordenacaoPorUsername = new EntidadeOrdenacao(UsuarioFields.username.name(),
                SortDirection.ASCENDING);
        List<Usuario> usuarios = new ArrayList<Usuario>();

        Iterable<Entity> entidades = super.lista(Usuario.class, ordenacaoPorUsername);
        for (Entity entidade : entidades) {
        	Usuario usuario = new Usuario(entidade);
        	Grupo grupo = new Grupo();
        	List<GrupoJSON> grupoJSONs = new ArrayList<GrupoJSON>();
        	List<Membro> grupos = repositorioMembro.obtemPorIdUsuario(usuario.getId());

        	Iterator<Membro> membro = grupos.iterator();
            while ( membro.hasNext() ){
            	GrupoJSON grupoJSON = new GrupoJSON();

            	grupo = repositorio.obtemPorId(membro.next().getIdGrupo());
            	grupoJSON.setId(grupo.getId());
            	grupoJSON.setNome(grupo.getNome());
            	grupoJSON.setProprietario(grupo.getProprietario());

            	grupoJSONs.add(grupoJSON);
            }

            usuario.setGrupos(grupoJSONs);
            usuarios.add(usuario);
        }
        return usuarios;
    }

    public Usuario obtemPorUsername(String username) {
        Query query = new Query(Usuario.class.getName());
        query.setFilter(new FilterPredicate(UsuarioFields.username.name(), FilterOperator.EQUAL, username));

        PreparedQuery pquery = this.datastore.prepare(query);

        Entity entityEncontrada = pquery.asSingleEntity();
        if (entityEncontrada == null) {
            throw new EntidadeNaoEncontradaException(Usuario.class.getName(), "username", username);
        }

        return new Usuario(entityEncontrada);
    }

    public Usuario obtemUsuarioLogado() {
        return obtemPorUsername(AutenticacaoService.identificacaoDoUsuarioLogado());
    }

}
