package br.com.dextra.dextranet.persistencia;

import br.com.dextra.dextranet.curtida.Curtida;
import br.com.dextra.dextranet.utils.DadosHelper;
import br.com.dextra.dextranet.utils.Data;

import com.google.appengine.api.datastore.EntityNotFoundException;

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

    protected boolean foiCurtidoPor(String usuario) {
        return this.userLikes != null && this.userLikes.indexOf(usuario) != -1;
    }

    public void receberCurtida(String usuario) throws EntityNotFoundException {
        if (!foiCurtidoPor(usuario)) {
            curtir(usuario);
        } else {
            descurtir(usuario);
        }
    }

    private void descurtir(String usuario) throws EntityNotFoundException {
        atualizaConteudoDepoisDaDescurtida(usuario);
        this.userLikes = this.userLikes.replaceAll(" " + usuario, "");
        this.likes--;
    }

    private void curtir(String usuario) throws EntityNotFoundException {
        Curtida curtida = new Curtida(usuario, this.id);
        atualizaConteudoDepoisDaCurtida(curtida.getUsuarioLogado());
        this.likes++;
        this.userLikes = this.userLikes + " " + usuario;
    }

    protected abstract void atualizaConteudoDepoisDaDescurtida(String login) throws EntityNotFoundException;

    //FIXME bad method name
    protected abstract void atualizaConteudoDepoisDaCurtida(String usuario) throws EntityNotFoundException;

}
