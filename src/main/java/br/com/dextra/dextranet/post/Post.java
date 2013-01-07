package br.com.dextra.dextranet.post;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;
import br.com.dextra.dextranet.entidade.Entidade;
import br.com.dextra.persistencia.PostFields;
import br.com.dextra.utils.Converters;
import br.com.dextra.utils.Data;
import br.com.dextra.utils.IndexKeys;

import com.google.appengine.api.datastore.Entity;


public class Post extends Entidade {


	private String titulo;

	private int comentario;

	private int likes;

	private String dataDeAtualizacao;



	public Post(String titulo, String conteudo,String usuario) {
		this.geraId();
		this.titulo = titulo;
		this.conteudo = conteudo;
		this.dataDeCriacao = new Data().pegaData();
		this.dataDeAtualizacao = this.dataDeCriacao;
		this.usuario = usuario;
		this.comentario = 0;
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
		this.comentario =  ((Long) postEntity.getProperty(PostFields.COMENTARIO.getField())).intValue();
		this.likes = ((Long) postEntity.getProperty(PostFields.LIKES.getField())).intValue();

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
		return comentario;
	}

	public int getLikes() {
		return likes;
	}

	public Entity toEntity() throws EntityNotFoundException
	{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), this.id);
		Entity e = datastore.get(key);
		return e;
	}





}
