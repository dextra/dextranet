package br.com.dextra.dextranet.post;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Conteudo;
import br.com.dextra.dextranet.utils.ConteudoHTML;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.gson.JsonObject;

public class Post extends Conteudo {

	private String titulo;

	protected long quantidadeDeComentarios;

	private Date dataDeAtualizacao;

	public Post(String usuario) {
		super(usuario);
		this.dataDeAtualizacao = new TimeMachine().dataAtual();
		this.quantidadeDeComentarios = 0;
	}

	public Post(Entity postEntity) {
		super((String) postEntity.getProperty(PostFields.usuario.toString()));
		this.id = (String) postEntity.getProperty(PostFields.id.toString());
		this.titulo = (String) postEntity.getProperty(PostFields.titulo.toString());
		this.conteudo = ( (Text) postEntity.getProperty(PostFields.conteudo.toString()) ).getValue();
		this.quantidadeDeCurtidas = (Long) postEntity.getProperty(PostFields.quantidadeDeCurtidas.toString());
		this.quantidadeDeComentarios = (Long) postEntity.getProperty(PostFields.quantidadeDeComentarios.toString());
		this.dataDeCriacao = (Date) postEntity.getProperty(PostFields.dataDeCriacao.toString());
		this.dataDeAtualizacao = (Date) postEntity.getProperty(PostFields.dataDeAtualizacao.toString());
	}

	public Post preenche(String titulo, String conteudo) {
		this.conteudo = new ConteudoHTML(conteudo).removeJavaScript();
		this.titulo = new ConteudoHTML(titulo).removeJavaScript();
		this.dataDeAtualizacao = new TimeMachine().dataAtual();
		return this;
	}

	public String getTitulo() {
		return titulo;
	}

	public Date getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	public long getQuantidadeDeComentarios() {
		return quantidadeDeComentarios;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(PostFields.id.toString(), this.id);
		entidade.setProperty(PostFields.titulo.toString(), this.titulo);
		entidade.setProperty(PostFields.conteudo.toString(), new Text(this.conteudo));
		entidade.setProperty(PostFields.quantidadeDeCurtidas.toString(), this.quantidadeDeCurtidas);
		entidade.setProperty(PostFields.quantidadeDeComentarios.toString(), this.quantidadeDeComentarios);
		entidade.setProperty(PostFields.usuario.toString(), this.usuario);
		entidade.setProperty(PostFields.dataDeCriacao.toString(), this.dataDeCriacao);
		entidade.setProperty(PostFields.dataDeAtualizacao.toString(), this.dataDeAtualizacao);

		return entidade;
	}

	@Override
	public JsonObject toJson() {
		throw new UnsupportedOperationException();
	}

}
