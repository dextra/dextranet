package br.com.dextra.comment;

import java.util.Date;

import br.com.dextra.dextranet.entidade.Entidade;

import com.google.appengine.api.datastore.Entity;

public class Comment extends Entidade{

	private String text;
	private String autor;
	private Date date;

	public Comment(String text, String autor, Date date) {
		super.geraId();
		this.text = text;
		this.autor = autor;
		this.date = date;
	}

	public Comment(Entity commentEntity){
		this.text = (String) commentEntity.getProperty("text");
		this.autor = (String) commentEntity.getProperty("autor");
		this.date = (Date) commentEntity.getProperty("date");
	}

	public String getText(){
		return this.text;
	}

	public String getAutor(){
		return this.autor;
	}

	public Date getDate(){
		return this.date;
	}

	public String toString(){
		return this.text;
	}

}
