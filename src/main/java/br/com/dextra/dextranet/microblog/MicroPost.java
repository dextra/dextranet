package br.com.dextra.dextranet.microblog;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;

public class MicroPost extends Entidade {

    private String texto;
    private Date data;
    private Usuario autor;

    public MicroPost(String texto, Usuario autor) {
        this(texto, autor, new TimeMachine().dataAtual());
    }

    public MicroPost(Entity microPostEntity) {
        super((String) microPostEntity.getProperty(MicroBlogFields.ID.getField()));
        this.texto = (String) microPostEntity.getProperty(MicroBlogFields.TEXTO.getField());
        this.data = (Date) microPostEntity.getProperty(MicroBlogFields.DATA.getField());
        atribuiUsuarioAoPost(microPostEntity);
    }

    private void atribuiUsuarioAoPost(Entity microPostEntity) {
        String autor = (String) microPostEntity.getProperty(MicroBlogFields.AUTOR.getField());
        this.autor = getUsuariorepository().obtemPorUsername(autor);
    }

    private UsuarioRepository getUsuariorepository() {
        return new UsuarioRepository();
    }

    public MicroPost(String texto, Usuario autor, Date data) {
        this.texto = texto;
        this.autor = autor;
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public Date getData() {
        return data;
    }

    @Override
	public Entity toEntity() {
		Entity entidade = new Entity(getKey(MicroPost.class));
		entidade.setProperty(MicroBlogFields.ID.getField(), getId());
		entidade.setProperty(MicroBlogFields.TEXTO.getField(), getTexto());
		entidade.setProperty(MicroBlogFields.DATA.getField(), getData());
		entidade.setProperty(MicroBlogFields.AUTOR.getField(), getAutor().getUsername());
		return entidade;
	}

    @Override
    public String toString() {
        return "MicroPost [texto=" + texto + ", id=" + id + "]";
    }

    public Usuario getAutor() {
        return autor;
    }

}
