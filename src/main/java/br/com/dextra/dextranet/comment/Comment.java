package br.com.dextra.dextranet.comment;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.search.Document;

public class Comment extends Entidade{

	private Date date;

	public Comment(String text, String autor, Date date) {
		super(autor, text);
		this.date = date;
	}

	public Comment(Entity commentEntity){
		super((String) commentEntity.getProperty("autor"), (String) commentEntity.getProperty("text"));
		this.date = (Date) commentEntity.getProperty("date");
	}

	public String getText(){
		return this.conteudo;
	}

	public String getAutor(){
		return this.usuario;
	}

	public Date getDate(){
		return this.date;
	}

	public String toString(){
		return this.conteudo;
	}

	public Document toDocument(){
		return null;
	}
}
