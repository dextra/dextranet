package br.com.dextra.dextranet.comment;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.post.PostFields;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;

public class Comment extends Entidade{

	private String idReference;

	private boolean tree;

	public Comment(String text, String autor, String id, boolean arvore) {
		super(autor, text);
		this.idReference=id;
		this.tree=arvore;
	}

	public Comment(Entity commentEntity){
		super((String) commentEntity.getProperty("autor"), (String) commentEntity.getProperty("text"));
	}

	public String getText(){
		return this.conteudo;
	}

	public String getAutor(){
		return this.usuario;
	}

	public String getDate(){
		return this.dataDeCriacao;
	}

	public String toString(){
		return this.conteudo;
	}

	public Document toDocument(){
		return null;
	}

	public Entity toEntity() {

		Entity entidade = new Entity(this.getKey());

		entidade.setProperty(CommentFields.ID.getField(), this.id);
		entidade.setProperty(CommentFields.CONTEUDO.getField(), new Text(this.conteudo));
		entidade.setProperty(CommentFields.USUARIO.getField(), this.usuario);
		entidade.setProperty(CommentFields.DATA_DE_CRIACAO.getField(), this.dataDeCriacao);
		entidade.setProperty(CommentFields.COMENTARIOS.getField(), this.comentarios);
		entidade.setProperty(CommentFields.LIKES.getField(), this.likes);
		entidade.setProperty(CommentFields.ID_REFERENCE.getField(), this.idReference);
		entidade.setProperty(CommentFields.TREE.getField(), this.tree);

		return entidade;
	}
}
