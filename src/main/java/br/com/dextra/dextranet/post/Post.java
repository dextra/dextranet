package br.com.dextra.dextranet.post;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.document.DocumentRepository;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.Converters;
import br.com.dextra.dextranet.utils.Data;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.gson.JsonObject;

public class Post extends Entidade {

	private String titulo;

	private String dataDeAtualizacao;

	public Post() {

	}

	public Post(String titulo, String conteudo, String usuario) {
		this(titulo, conteudo, usuario, "");
	}

	public Post(String titulo, String conteudo, String usuario, String dataDeAtualizacaoParametro) {
		super(usuario, conteudo);
		this.titulo = titulo;
		if (dataDeAtualizacaoParametro.isEmpty())
		{
			dataDeAtualizacaoParametro = new Data().pegaDataDeAtualizacao();
		}
		this.dataDeAtualizacao = dataDeAtualizacaoParametro;
		this.comentarios = 0;
		this.likes = 0;
	}

	public Post(Entity postEntity) {
		Converters conversores = new Converters();

		this.id = (String) postEntity.getProperty(PostFields.ID.getField());
		this.titulo = (String) postEntity.getProperty(PostFields.TITULO.getField());
		this.conteudo = conversores.converterGAETextToString(postEntity);
		this.dataDeCriacao = (String) postEntity.getProperty(PostFields.DATA.getField());
		this.dataDeAtualizacao = (String) postEntity.getProperty(PostFields.DATA_DE_ATUALIZACAO.getField());
		this.usuario = (String) postEntity.getProperty(PostFields.USUARIO.getField());
		this.comentarios = ((Long) postEntity.getProperty(PostFields.COMENTARIO.getField())).intValue();
		this.likes = ((Long) postEntity.getProperty(PostFields.LIKES.getField())).intValue();
	}

	public String getTitulo() {
		return this.titulo;
	}

	public String getConteudo() {
		return this.conteudo;
	}

	public String getDataDeCriacao() {
		return this.dataDeCriacao;
	}

	public String getDataDeAtualizacao() {
		return this.dataDeAtualizacao;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public int getComentarios() {
		return this.comentarios;
	}

	public int getLikes() {
		return this.likes;
	}


	public void comentar(Comment comment) throws EntityNotFoundException {
		new DocumentRepository().alteraDocumento(comment);
		new PostRepository().alteraEntity(comment);

		this.comentarios=this.comentarios+1;
	}

	public void curtir(String user, String id) throws EntityNotFoundException {

		DocumentRepository postDoDocumentReository = new DocumentRepository();
		PostRepository postDoRepository = new PostRepository();

		String data = new Data().pegaDataDeAtualizacao();


		postDoDocumentReository.alteraDocumento(id, data);
		postDoRepository.alteraDataDaEntity(id,data);
		postDoRepository.incrementaNumeroDeComentariosDaEntityDoPost(id);
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(PostFields.ID.getField(), this.id);
		entidade.setProperty(PostFields.TITULO.getField(), this.titulo);
		entidade.setProperty(PostFields.CONTEUDO.getField(), new Text(this.conteudo));
		entidade.setProperty(PostFields.USUARIO.getField(), this.usuario);
		entidade.setProperty(PostFields.COMENTARIO.getField(), this.comentarios);
		entidade.setProperty(PostFields.LIKES.getField(), this.likes);
		entidade.setProperty(PostFields.DATA.getField(), this.dataDeCriacao);
		entidade.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), this.dataDeAtualizacao);

		return entidade;
	}

	@Override
	public JsonObject toJson(){
		JsonObject json =new JsonObject();
		json.addProperty(PostFields.ID.getField(), this.id);
		json.addProperty(PostFields.TITULO.getField(), this.titulo);
		json.addProperty(PostFields.CONTEUDO.getField(), this.conteudo);
		json.addProperty(PostFields.USUARIO.getField(), this.usuario);
		json.addProperty(PostFields.COMENTARIO.getField(), this.comentarios);
		json.addProperty(PostFields.LIKES.getField(), this.likes);
		json.addProperty(PostFields.DATA.getField(), this.dataDeCriacao);
		json.addProperty(PostFields.DATA_DE_ATUALIZACAO.getField(), this.dataDeAtualizacao);

		return json;
	}

	@Override
	public Document toDocument() {
		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(PostFields.TITULO.getField()).setText(titulo))
				.addField(Field.newBuilder().setName(PostFields.CONTEUDO.getField()).setHTML(conteudo))
				.addField(Field.newBuilder().setName(PostFields.USUARIO.getField()).setText(usuario))
				.addField(Field.newBuilder().setName(PostFields.DATA_DE_ATUALIZACAO.getField()).setText(dataDeAtualizacao))
				.addField(Field.newBuilder().setName(PostFields.ID.getField()).setText(id))
				.build();

		return document;
	}

}
