package br.com.dextra.dextranet.post.curtida;

import br.com.dextra.dextranet.utils.Data;

public class Curtida {

    // FIXME Primitive Obsession or simple a bad name to a variable
    private String dataCurtida;

    private String idConteudo;
    private String usuario;

    public Curtida(String usuario, String idConteudo) {
        this.idConteudo = idConteudo;
        this.usuario = usuario;
        this.dataCurtida = new Data().pegaData();
    }

    public String getData() {
        return this.dataCurtida;
    }

    public String getIdConteudo() {
        return this.idConteudo;
    }

    public String getUsuarioLogado() {
        return this.usuario;
    }

}
