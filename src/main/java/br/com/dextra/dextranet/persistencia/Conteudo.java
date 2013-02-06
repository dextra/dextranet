package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.dextra.dextranet.curtida.Curtida;
import br.com.dextra.dextranet.utils.DadosHelper;
import br.com.dextra.dextranet.utils.Data;

public abstract class Conteudo extends Entidade {

    protected String usuario;

    protected String conteudo;

    protected String dataDeCriacao;

    protected int comentarios;

    protected int likes;

    protected String userLikes;

    public Conteudo() {
        super();
    }

    public Conteudo(String usuario, String conteudo) {
        super();
        this.conteudo = new DadosHelper().removeConteudoJS(conteudo);
        this.usuario = usuario;
        this.dataDeCriacao = new Data().pegaData();
        this.comentarios = 0;
        this.likes = 0;
        this.userLikes = "";
    }

    protected boolean jaCurtiu(String usuario) {

        return !(this.userLikes == null || this.userLikes.indexOf(usuario) == -1);
    }

    public Curtida curtir(String usuario) throws EntityNotFoundException {

        Curtida curtida = null;
        // FIXME: Conteudo curtiu usuario ou usuario curtiu o conteudo?
        if (!this.jaCurtiu(usuario)) {

            curtida = new Curtida(usuario, this.id);

            setaAtributos(curtida);

            this.likes++;
            this.userLikes = this.userLikes + " " + usuario;
        }
        return curtida;

    }

    protected abstract void setaAtributos(Curtida curtida) throws EntityNotFoundException;

}
