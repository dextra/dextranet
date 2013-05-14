package br.com.dextra.dextranet.conteudo.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.conteudo.ConteudoIndexavel;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.utils.ConteudoHTML;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;

public class Post extends Conteudo implements ConteudoIndexavel {

	private String titulo;

	protected long quantidadeDeComentarios;

	private Date dataDeAtualizacao;
	
	public Post(String usuario, String titulo, String conteudo) {
		super(usuario);
		this.dataDeAtualizacao = new TimeMachine().dataAtual();
		this.quantidadeDeComentarios = 0;
		this.preenche(titulo, conteudo);
	}

	@SuppressWarnings("unchecked")
	public Post(Entity postEntity) {
		super((String) postEntity.getProperty(PostFields.usuario.name()));
		this.id = (String) postEntity.getProperty(PostFields.id.name());
		this.titulo = (String) postEntity.getProperty(PostFields.titulo.name());
		this.conteudo = ((Text) postEntity.getProperty(PostFields.conteudo.name())).getValue();
		this.quantidadeDeCurtidas = (Long) postEntity.getProperty(PostFields.quantidadeDeCurtidas.name());
		this.usuariosQueCurtiram = (List<String>) postEntity.getProperty(PostFields.usuariosQueCurtiram.name());
		this.quantidadeDeComentarios = (Long) postEntity.getProperty(PostFields.quantidadeDeComentarios.name());
		this.dataDeCriacao = (Date) postEntity.getProperty(PostFields.dataDeCriacao.name());
		this.dataDeAtualizacao = (Date) postEntity.getProperty(PostFields.dataDeAtualizacao.name());

		if (this.usuariosQueCurtiram == null) {
			this.usuariosQueCurtiram = new ArrayList<String>();
		}
	}

	public Post preenche(String titulo, String conteudo) {
		ConteudoHTML conteudoHTML = new ConteudoHTML(conteudo);
		this.conteudo = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(titulo);
		this.titulo = conteudoHTML.removeJavaScript();
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

	public Comentario comentar(String username, String conteudo) {
		this.quantidadeDeComentarios++;
		return new Comentario(this.id, username, new ConteudoHTML(conteudo).removeJavaScript());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(PostFields.id.name(), this.id);
		entidade.setProperty(PostFields.titulo.name(), this.titulo);
		entidade.setProperty(PostFields.conteudo.name(), new Text(this.conteudo));
		entidade.setProperty(PostFields.quantidadeDeCurtidas.name(), this.quantidadeDeCurtidas);
		entidade.setProperty(PostFields.usuariosQueCurtiram.name(), this.usuariosQueCurtiram);
		entidade.setProperty(PostFields.quantidadeDeComentarios.name(), this.quantidadeDeComentarios);
		entidade.setProperty(PostFields.usuario.name(), this.usuario);
		entidade.setProperty(PostFields.usuarioMD5.name(), this.usuarioMD5);
		entidade.setProperty(PostFields.dataDeCriacao.name(), this.dataDeCriacao);
		entidade.setProperty(PostFields.dataDeAtualizacao.name(), this.dataDeAtualizacao);

		return entidade;
	}

	@Override
	public String toString() {
		return "Post [titulo=" + titulo + ", quantidadeDeComentarios=" + quantidadeDeComentarios
				+ ", dataDeAtualizacao=" + dataDeAtualizacao + ", usuario=" + usuario + ", dataDeCriacao="
				+ dataDeCriacao + ", quantidadeDeCurtidas=" + quantidadeDeCurtidas + ", id=" + id + "]";
	}

	@Override
	public Document toDocument() {
		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(PostFields.id.name()).setText(id))
				.addField(Field.newBuilder().setName(PostFields.titulo.name()).setText(titulo))
				.addField(Field.newBuilder().setName(PostFields.conteudo.name()).setHTML(conteudo))
				.addField(Field.newBuilder().setName(PostFields.usuario.name()).setText(usuario)).build();
		return document;
	}

}
