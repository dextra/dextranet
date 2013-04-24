package br.com.dextra.dextranet.old.post;

import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.conteudo.ConteudoIndexavel;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.search.Document;
import com.google.gson.JsonObject;

public class Post extends Conteudo implements ConteudoIndexavel {

	protected Post(String usuario) {
		super(usuario);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Document toDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity toEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}

//	private String titulo;
//
//	public Post(String titulo, String conteudo, String usuario) {
//		this(titulo, conteudo, usuario, new Data().pegaData());
//	}
//
//	public Post(String titulo, String conteudo, String usuario, String dataDeAtualizacao) {
//		super(usuario, conteudo);
//		this.titulo = titulo;
//		this.dataDeAtualizacao = dataDeAtualizacao;
//		this.quantidadeDeComentarios = 0;
//		this.quantidadeDeCurtidas = 0;
//		this.postRepository = new PostRepository();
//	}
//
//	public Post(Entity postEntity) {
//		this.id = (String) postEntity.getProperty(PostFields.ID.getField());
//		this.titulo = (String) postEntity.getProperty(PostFields.TITULO.getField());
//		this.conteudo = Converters.converterGAETextToString(postEntity);
//		this.dataDeCriacao = (String) postEntity.getProperty(PostFields.DATA.getField());
//		this.dataDeAtualizacao = (String) postEntity.getProperty(PostFields.DATA_DE_ATUALIZACAO.getField());
//		this.usuario = (String) postEntity.getProperty(PostFields.USUARIO.getField());
//		this.quantidadeDeComentarios = ((Long) postEntity.getProperty(PostFields.COMENTARIO.getField())).intValue();
//		this.quantidadeDeCurtidas = ((Long) postEntity.getProperty(PostFields.LIKES.getField())).intValue();
//		this.userLikes = (String) postEntity.getProperty(PostFields.USER_LIKE.getField());
//		this.postRepository = new PostRepository();
//	}
//
//	public String getTitulo() {
//		return this.titulo;
//	}
//
//	public String getConteudo() {
//		return this.conteudo;
//	}
//
//	public String getDataDeCriacao() {
//		return this.dataDeCriacao;
//	}
//
//	public String getDataDeAtualizacao() {
//		return this.dataDeAtualizacao;
//	}
//
//	public String getUsuario() {
//		return this.usuario;
//	}
//
//	public int getComentarios() {
//		return this.quantidadeDeComentarios;
//	}
//
//	public int getLikes() {
//		return this.quantidadeDeCurtidas;
//	}
//
//	public String getUserLikes() {
//		return this.userLikes;
//	}
//
//	public void comentar(Comment comment) throws EntityNotFoundException, ParseException {
//
//		postRepository.alteraEntity(comment);
//
//		this.quantidadeDeComentarios++;
//		this.dataDeAtualizacao = comment.getDataDeCriacao();
//
//	}
//
//	protected void atualizaConteudoDepoisDaCurtida(String usuario) throws EntityNotFoundException {
//		this.dataDeAtualizacao = new Data().pegaData();
//		new PostRepository().registrarCurtida(this, usuario);
//	}
//
//	@Override
//	public Entity toEntity() {
//		Entity entidade = new Entity(this.getKey(this.getClass()));
//
//		entidade.setProperty(PostFields.ID.getField(), this.id);
//		entidade.setProperty(PostFields.TITULO.getField(), this.titulo);
//		entidade.setProperty(PostFields.CONTEUDO.getField(), new Text(this.conteudo));
//		entidade.setProperty(PostFields.USUARIO.getField(), this.usuario);
//		entidade.setProperty(PostFields.COMENTARIO.getField(), this.quantidadeDeComentarios);
//		entidade.setProperty(PostFields.LIKES.getField(), this.quantidadeDeCurtidas);
//		entidade.setProperty(PostFields.DATA.getField(), this.dataDeCriacao);
//		entidade.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), this.dataDeAtualizacao);
//		entidade.setProperty(PostFields.USER_LIKE.getField(), this.userLikes);
//
//		return entidade;
//	}
//
//	@Override
//	public JsonObject toJson() {
//		JsonObject json = new JsonObject();
//		json.addProperty(PostFields.ID.getField(), this.id);
//		json.addProperty(PostFields.TITULO.getField(), this.titulo);
//		json.addProperty(PostFields.CONTEUDO.getField(), this.conteudo);
//		json.addProperty(PostFields.USUARIO.getField(), this.usuario);
//		json.addProperty(PostFields.COMENTARIO.getField(), this.quantidadeDeComentarios);
//		json.addProperty(PostFields.LIKES.getField(), this.quantidadeDeCurtidas);
//		json.addProperty(PostFields.DATA.getField(), this.dataDeCriacao);
//		json.addProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), this.dataDeAtualizacao);
//		json.addProperty(PostFields.USER_LIKE.getField(), this.userLikes);
//
//		return json;
//	}
//
//	public Document toDocument() {
//		Document document = Document
//				.newBuilder()
//				.setId(id)
//				.addField(Field.newBuilder().setName(PostFields.TITULO.getField()).setText(titulo))
//				.addField(Field.newBuilder().setName(PostFields.CONTEUDO.getField()).setHTML(conteudo))
//				.addField(Field.newBuilder().setName(PostFields.USUARIO.getField()).setText(usuario))
//				.addField(
//						Field.newBuilder().setName(PostFields.DATA_DE_ATUALIZACAO.getField())
//								.setText(dataDeAtualizacao))
//				.addField(Field.newBuilder().setName(PostFields.ID.getField()).setText(id)).build();
//
//		return document;
//	}
//
//	@Override
//	protected void atualizaConteudoDepoisDaDescurtida(String login) throws EntityNotFoundException {
//		postRepository.registrarDescurtida(this, login);
//	}

}
