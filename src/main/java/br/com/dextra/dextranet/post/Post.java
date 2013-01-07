package br.com.dextra.dextranet.post;


import br.com.dextra.dextranet.entidade.Entidade;
import br.com.dextra.repository.document.DocumentRepository;
import br.com.dextra.utils.Converters;
import br.com.dextra.utils.Data;
import br.com.dextra.utils.IndexKeys;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class Post extends Entidade {


	private String titulo;

	private String dataDeAtualizacao;

	public Post(String titulo, String conteudo,String usuario) {
		super(usuario, conteudo);
		this.titulo = titulo;
		this.dataDeAtualizacao = this.dataDeCriacao;
		this.comentarios = 0;
		this.likes=0;
	}

	public Post(Entity postEntity) {
		Converters conversores=new Converters();

		this.id = (String) postEntity.getProperty(PostFields.ID.getField());
		this.titulo = (String) postEntity.getProperty(PostFields.TITULO.getField());
		this.conteudo = conversores.converterGAETextToString(postEntity);
		this.dataDeCriacao = (String) postEntity.getProperty(PostFields.DATA.getField());
		this.dataDeAtualizacao = (String) postEntity.getProperty(PostFields.DATA_DE_ATUALIZACAO.getField());
		this.usuario = (String) postEntity.getProperty(PostFields.USUARIO.getField());
		this.comentarios =  ((Long) postEntity.getProperty(PostFields.COMENTARIO.getField())).intValue();
		this.likes = ((Long) postEntity.getProperty(PostFields.LIKES.getField())).intValue();

	}

	public Post (String id) throws EntityNotFoundException  {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
		new Post(datastore.get(key));
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

	public void comentar(String id) throws EntityNotFoundException
	{

		//FIXME: COMEÇANDO A FAZER O COMENTARIO NO REFACTORING
		DocumentRepository postDoDocumentReository = new DocumentRepository();
		PostRepository postDoRepository = new PostRepository();

		String data = new Data().pegaData();;
		postDoDocumentReository.alteraDatadoDocumento(id,data);
		postDoRepository.alteraDatadaEntity(id, data);

		postDoRepository.incrementaNumeroDeComentariosDaEntityDoPost(id);
	}





}
