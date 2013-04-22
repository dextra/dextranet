package br.com.dextra.dextranet.post.comment;

import java.util.Calendar;
import java.util.Date;

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

	private String idPost;

	private boolean arvore;

	private CommentRepository commentRepository;

	public Comment(String text, String autor, String id, boolean arvore) {
		super(autor, text);
		this.idPost = id;
		this.arvore = arvore;
		this.commentRepository = new CommentRepository();
	}

	public Comment(Entity commentEntity) {
		this.usuario = (String) commentEntity.getProperty(CommentFields.USUARIO.getField());
		this.conteudo = Converters.converterGAETextToString(commentEntity);
		this.id = (String) commentEntity.getProperty(CommentFields.ID.getField());
		this.dataDeCriacao = (String) commentEntity.getProperty(CommentFields.DATA_DE_CRIACAO.getField());
		this.comentarios = ((Long) commentEntity.getProperty(CommentFields.COMENTARIOS.getField())).intValue();
		this.likes = ((Long) commentEntity.getProperty(CommentFields.LIKES.getField())).intValue();
		this.idPost = (String) commentEntity.getProperty(CommentFields.ID_REFERENCE.getField());
		this.userLikes = (String) commentEntity.getProperty(CommentFields.USER_LIKE.getField());
		this.arvore = new Converters()
				.toBoolean(String.valueOf(commentEntity.getProperty(CommentFields.TREE.getField())));
		this.commentRepository = new CommentRepository();
	}

	public String getText() {
		return this.conteudo;
	}

	public String getIdPost() {
		return this.idPost;
	}

	public boolean isArvore() {
		return this.arvore;
	}

	public String getAutor() {
		return this.usuario;
	}

	public int getLikes() {
		return this.likes;
	}

	public String getDataDeCriacao() {
		return this.dataDeCriacao;
	}

	public String getUserLikes() {
		return this.userLikes;
	}

	public void setSegundoDaDataDeCriacao(int segundo) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, segundo);
		Date data = calendar.getTime();
		this.dataDeCriacao = new Data().formataDataPelaBiblioteca(data);
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(CommentFields.ID.getField(), this.id);
		entidade.setProperty(CommentFields.ID_REFERENCE.getField(), this.idPost);
		entidade.setProperty(CommentFields.CONTEUDO.getField(), new Text(this.conteudo));
		entidade.setProperty(CommentFields.USUARIO.getField(), this.usuario);
		entidade.setProperty(CommentFields.COMENTARIOS.getField(), this.comentarios);
		entidade.setProperty(CommentFields.LIKES.getField(), this.likes);
		entidade.setProperty(CommentFields.DATA_DE_CRIACAO.getField(), this.dataDeCriacao);
		entidade.setProperty(CommentFields.TREE.getField(), this.arvore);
		entidade.setProperty(CommentFields.USER_LIKE.getField(), this.userLikes);

		return entidade;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty(CommentFields.ID.getField(), this.id);
		json.addProperty(CommentFields.ID_REFERENCE.getField(), this.idPost);
		json.addProperty(CommentFields.CONTEUDO.getField(), this.conteudo);
		json.addProperty(CommentFields.USUARIO.getField(), this.usuario);
		json.addProperty(CommentFields.COMENTARIOS.getField(), this.comentarios);
		json.addProperty(CommentFields.LIKES.getField(), this.likes);
		json.addProperty(CommentFields.DATA_DE_CRIACAO.getField(), this.dataDeCriacao);
		json.addProperty(CommentFields.TREE.getField(), this.arvore);
		json.addProperty(CommentFields.USER_LIKE.getField(), this.userLikes);
		return json;
	}

	public Document toDocument() {
		Document document = Document
				.newBuilder()
				.setId(id)
				.addField(Field.newBuilder().setName(CommentFields.CONTEUDO.getField()).setText(this.conteudo))
				.addField(Field.newBuilder().setName(CommentFields.ID_REFERENCE.getField()).setText(this.idPost))
				.addField(
						Field.newBuilder().setName(CommentFields.TREE.getField()).setText(String.valueOf(this.arvore)))
				.addField(Field.newBuilder().setName(CommentFields.ID.getField()).setText(this.id)).build();

		IndexFacade.getIndex(Comment.class.getName()).put(document);
		return document;
	}

	// FIXME: Juntar operacoes
	protected void atualizaConteudoDepoisDaCurtida(String usuario) throws EntityNotFoundException {
		commentRepository.insereUsuarioQueCurtiuNoComment(this, usuario);
		commentRepository.incrementaNumeroDeLikesDaEntityDoComment(this.getId());
	}

	@Override
	protected void atualizaConteudoDepoisDaDescurtida(String login) throws EntityNotFoundException {
		commentRepository.removeUsuarioQueCurtiuNoComment(this, login);
		commentRepository.decrementaNumeroDeLikesDaEntityDoComment(this);
	}

}
