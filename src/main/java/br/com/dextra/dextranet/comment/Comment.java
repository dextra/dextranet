package br.com.dextra.dextranet.comment;

import java.util.Date;

import br.com.dextra.dextranet.curtida.Curtida;
import br.com.dextra.dextranet.persistencia.Conteudo;
import br.com.dextra.dextranet.persistencia.ConteudoIndexavel;
import br.com.dextra.dextranet.utils.Converters;
import br.com.dextra.dextranet.utils.Data;
import br.com.dextra.dextranet.utils.IndexFacade;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.gson.JsonObject;

public class Comment extends Conteudo implements ConteudoIndexavel {

	private String idReference;

	private boolean tree;

	public Comment(String text, String autor, String id, boolean arvore) {
		super(autor, text);
		this.idReference = id;
		this.tree = arvore;
	}

	public Comment(Entity commentEntity) {
		this.usuario = (String) commentEntity.getProperty(CommentFields.USUARIO.getField());
		this.conteudo = new Converters().converterGAETextToString(commentEntity);
		this.id = (String) commentEntity.getProperty(CommentFields.ID.getField());
		this.dataDeCriacao=(String) commentEntity.getProperty(CommentFields.DATA_DE_CRIACAO.getField());
		this.comentarios=((Long)  commentEntity.getProperty(CommentFields.COMENTARIOS.getField())).intValue();
		this.likes=((Long)  commentEntity.getProperty(CommentFields.LIKES.getField())).intValue();
		this.idReference = (String) commentEntity.getProperty(CommentFields.ID_REFERENCE.getField());
		this.userLikes = (String) commentEntity.getProperty(CommentFields.USER_LIKE.getField());
		this.tree = new Converters().toBoolean(String.valueOf(commentEntity.getProperty(CommentFields.TREE.getField())));
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

	public String getUserLikes() {
		return this.userLikes;
	}


	@SuppressWarnings("deprecation")
	public void setSgundoDaDataDeCriacao (int segundo){
		Date data = new Date();
		// FIXME: metodo depreciado
		data.setSeconds(segundo);
		this.dataDeCriacao = new Data().formataDataPelaBiblioteca(data);
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(CommentFields.ID.getField(), this.id);
		entidade.setProperty(CommentFields.ID_REFERENCE.getField(),	this.idReference);
		entidade.setProperty(CommentFields.CONTEUDO.getField(), new Text(this.conteudo));
		entidade.setProperty(CommentFields.USUARIO.getField(), this.usuario);
		entidade.setProperty(CommentFields.COMENTARIOS.getField(), this.comentarios);
		entidade.setProperty(CommentFields.LIKES.getField(), this.likes);
		entidade.setProperty(CommentFields.DATA_DE_CRIACAO.getField(), this.dataDeCriacao);
		entidade.setProperty(CommentFields.TREE.getField(), this.tree);
		entidade.setProperty(CommentFields.USER_LIKE.getField(), this.userLikes);



		return entidade;
	}

	@Override
	public JsonObject toJson(){
		JsonObject json =new JsonObject();
		json.addProperty(CommentFields.ID.getField(), this.id);
		json.addProperty(CommentFields.ID_REFERENCE.getField(), this.idReference);
		json.addProperty(CommentFields.CONTEUDO.getField(), this.conteudo);
		json.addProperty(CommentFields.USUARIO.getField(), this.usuario);
		json.addProperty(CommentFields.COMENTARIOS.getField(), this.comentarios);
		json.addProperty(CommentFields.LIKES.getField(), this.likes);
		json.addProperty(CommentFields.DATA_DE_CRIACAO.getField(), this.dataDeCriacao);
		json.addProperty(CommentFields.TREE.getField(), this.tree);
		json.addProperty(CommentFields.USER_LIKE.getField(), this.userLikes);

		return json;
	}

	public Document toDocument() {
		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(CommentFields.CONTEUDO.getField()).setText(this.conteudo))
				.addField(Field.newBuilder().setName(CommentFields.ID_REFERENCE.getField()).setText(this.idReference))
				.addField(Field.newBuilder().setName(CommentFields.TREE.getField()).setText(String.valueOf(this.tree)))
				.addField(Field.newBuilder().setName(CommentFields.ID.getField()).setText(this.id)).build();

		IndexFacade.getIndex(Comment.class.getName()).add(document);
		return document;
	}

	public void curtir(String usuario) throws EntityNotFoundException {
		CommentRepository commentDoRepository = new CommentRepository();

		if (!this.jaCurtiu(usuario)) {

			Curtida curtida = new Curtida(usuario, this.id);

			commentDoRepository.insereUsuarioQueCurtiuNoComment(curtida, this);
			commentDoRepository.incrementaNumeroDeLikesDaEntityDoComment(curtida);
			this.likes++;
			this.userLikes = this.userLikes + " " + usuario;
		}

	}

	private boolean jaCurtiu(String usuario) {

		return !(this.userLikes == null || this.userLikes.indexOf(usuario) == -1);
	}

}