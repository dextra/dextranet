package br.com.dextra.dextranet.usuario;

import java.util.ArrayList;
import java.util.List;

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

    public List<Usuario> lista() {
        EntidadeOrdenacao ordenacaoPorUsername = new EntidadeOrdenacao(UsuarioFields.username.name(),
                SortDirection.ASCENDING);
        List<Usuario> usuarios = new ArrayList<Usuario>();

        Iterable<Entity> entidades = super.lista(Usuario.class, ordenacaoPorUsername);
        for (Entity entidade : entidades) {
            usuarios.add(new Usuario(entidade));
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
