package br.com.dextra.dextranet.microblog;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		entidade.setProperty(MicroBlogFields.TEXTO.getField(), converterTextoEmURL(getTexto()));
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

    public static String converterTextoEmURL(String text) {
    	if (text == null) {
    		return text;
    	}

    	String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
    	Pattern patt = Pattern.compile(str);
    	Matcher matcher = patt.matcher(text);

    	StringBuffer sb = new StringBuffer();

		while (matcher.find()) {
			if (!matcher.group().matches("^(https?)://.*$")) {
				String ch = matcher.group();
				matcher.appendReplacement(sb, "http://" + ch);
			}
		}

		matcher.appendTail(sb);
		matcher = patt.matcher(sb.toString());
		text = matcher.replaceAll("<a href=\"$1\" target='_blank'>$1</a>");

    	return text;

    }

}
