package br.com.dextra.dextranet.comment;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.Converters;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;

public class Comment extends Entidade {

	private String idReference;

	private boolean tree;

	public Comment(String text, String autor, String id, boolean arvore) {
		super(autor, text);
		this.idReference = id;
		this.tree = arvore;
	}

	public Comment(Entity commentEntity) {
		super((String) commentEntity.getProperty(CommentFields.USUARIO
				.getField()), (String) commentEntity.getProperty(CommentFields.CONTEUDO.getField()));
		this.idReference = CommentFields.ID_REFERENCE.getField();
		this.tree =  new Converters().toBoolean(CommentFields.TREE.getField());
	}

	public String getText() {
		return this.conteudo;
	}

	public String getIdReference() {
		return this.idReference;
	}

	public boolean isTree() {
		return this.tree;
	}

	public String getAutor() {
		return this.usuario;
	}

	public String getDataDeCriacao() {
		return this.dataDeCriacao;
	}

	@Override
	public Document toDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey());

		entidade.setProperty(CommentFields.ID.getField(), this.id);
		entidade.setProperty(CommentFields.ID_REFERENCE.getField(), this.idReference);
		entidade.setProperty(CommentFields.CONTEUDO.getField(), new Text(
				this.conteudo));
		entidade.setProperty(CommentFields.USUARIO.getField(), this.usuario);
		entidade
				.setProperty(CommentFields.COMENTARIOS.getField(), this.comentarios);
		entidade.setProperty(CommentFields.LIKES.getField(), this.likes);
		entidade.setProperty(CommentFields.DATA_DE_CRIACAO.getField(), this.dataDeCriacao);
		entidade.setProperty(CommentFields.TREE.getField(),
				this.tree);

		return entidade;
	}

}
