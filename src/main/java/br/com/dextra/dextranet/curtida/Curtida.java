package br.com.dextra.dextranet.curtida;

import br.com.dextra.dextranet.utils.Data;

public class Curtida {

    // FIXME Primitive Obsession or simple a bad name to a variable
    private String data;

    private String idConteudo;
    private String usuario;

    public Curtida(String usuario, String idConteudo) {
        this.idConteudo = idConteudo;
        this.usuario = usuario;
        this.data = new Data().pegaData();
    }

    public Curtida(String usuario, String idConteudo, String data) {
        this.idConteudo = idConteudo;
        this.usuario = usuario;
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public String getIdConteudo() {
        return this.idConteudo;
    }

    public String getUsuarioLogado() {
        return this.usuario;
    }

}
