package br.com.dextra.dextranet.post;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.utils.Converters;
import br.com.dextra.utils.Data;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class Post extends Entidade {

	private String titulo;

	private String dataDeAtualizacao;

	public Post(String titulo, String conteudo, String usuario) {
		super(usuario, conteudo);
		this.titulo = titulo;
		this.dataDeAtualizacao = this.dataDeCriacao;
		this.comentarios = 0;
		this.likes = 0;
	}

	public Post(Entity postEntity) {
		Converters conversores = new Converters();

		this.id = (String) postEntity.getProperty(PostFields.ID.getField());
		this.titulo = (String) postEntity.getProperty(PostFields.TITULO
				.getField());
		this.conteudo = conversores.converterGAETextToString(postEntity);
		this.dataDeCriacao = (String) postEntity.getProperty(PostFields.DATA
				.getField());
		this.dataDeAtualizacao = (String) postEntity
				.getProperty(PostFields.DATA_DE_ATUALIZACAO.getField());
		this.usuario = (String) postEntity.getProperty(PostFields.USUARIO
				.getField());
		this.comentarios = ((Long) postEntity.getProperty(PostFields.COMENTARIO
				.getField())).intValue();
		this.likes = ((Long) postEntity
				.getProperty(PostFields.LIKES.getField())).intValue();

	}

	public String getTitulo() {
		return titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public String getDataDeCriacao() {
		return dataDeCriacao;
	}

	public String getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	public String getUsuario() {
		return usuario;
	}

	public int getComentarios() {
		return comentarios;
	}

	public int getLikes() {
		return likes;
	}

	public void comentar(String id) throws EntityNotFoundException {

		// FIXME: COMEÇANDO A FAZER O COMENTARIO NO REFACTORING
		DocumentRepository postDoDocumentReository = new DocumentRepository();
		PostRepository postDoRepository = new PostRepository();

		String data = new Data().pegaData();

		postDoDocumentReository.alteraDatadoDocumento(id, data);
		postDoRepository.alteraDatadaEntity(id, data);

		postDoRepository.incrementaNumeroDeComentariosDaEntityDoPost(id);
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey());

		entidade.setProperty(PostFields.ID.getField(), id);
		entidade.setProperty(PostFields.TITULO.getField(), titulo);
		entidade.setProperty(PostFields.CONTEUDO.getField(), new Text(conteudo));
		entidade.setProperty(PostFields.USUARIO.getField(), usuario);
		entidade.setProperty(PostFields.COMENTARIO.getField(), this.comentarios);
		entidade.setProperty(PostFields.LIKES.getField(), this.likes);
		entidade.setProperty(PostFields.DATA.getField(), this.dataDeCriacao);
		entidade.setProperty(PostFields.DATA_DE_ATUALIZACAO.getField(),this.dataDeAtualizacao);

		return entidade;
	}

	public Document toDocument(){

		Document document = Document.newBuilder().setId(id)
			.addField(Field.newBuilder().setName(PostFields.TITULO.getField()).setText(titulo))
			.addField(Field.newBuilder().setName(PostFields.CONTEUDO.getField()).setHTML(conteudo))
			.addField(Field.newBuilder().setName(PostFields.USUARIO.getField()).setText(usuario))
			.addField(Field.newBuilder().setName(PostFields.DATA.getField()).setText(dataDeCriacao))
			.addField(Field.newBuilder().setName(PostFields.DATA_DE_ATUALIZACAO.getField()).setText(dataDeAtualizacao))
			.addField(Field.newBuilder().setName(PostFields.ID.getField()).setText(id))
			.build();

		return document;
	}

}
