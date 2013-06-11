package br.com.dextra.dextranet.conteudo.post;

import java.util.ArrayList;
import java.util.Calendar;
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

	private Date dataDeAtualizacao;

	private List<Comentario> comentarios;

	public Post(String usuario, String titulo, String conteudo) {
		super(usuario);
		this.preenche(titulo, conteudo);
		this.dataDeAtualizacao = this.dataDeCriacao;
		comentarios = new ArrayList<Comentario>();
	}

	@SuppressWarnings("unchecked")
	public Post(Entity postEntity) {
		super((String) postEntity.getProperty(PostFields.usuario.name()));
		this.comentarios = new ArrayList<Comentario>();
		this.id = (String) postEntity.getProperty(PostFields.id.name());
		this.titulo = (String) postEntity.getProperty(PostFields.titulo.name());
		this.conteudo = ((Text) postEntity.getProperty(PostFields.conteudo.name())).getValue();
		this.quantidadeDeCurtidas = (Long) postEntity.getProperty(PostFields.quantidadeDeCurtidas.name());
		this.usuariosQueCurtiram = (List<String>) postEntity.getProperty(PostFields.usuariosQueCurtiram.name());
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

	public long getQuantidadeDeComentarios() {
		return comentarios.size();
	}

	public Date getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	@Deprecated
	public Comentario comentarParaMigracao(String username, String conteudo, Date data, String timestamp) {
		Comentario comentario = new Comentario(this.id, username, new ConteudoHTML(conteudo).removeJavaScript());

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(timestamp));
		data = calendar.getTime();

		comentario.registraDataDeMigracao(data);
		this.dataDeAtualizacao = data;

		comentarios.add(comentario);
		return comentario;
	}

	public Comentario comentar(String username, String conteudo) {
		Comentario comentario = new Comentario(this.id, username, new ConteudoHTML(conteudo).removeJavaScript());
		comentarios.add(comentario);
		this.dataDeAtualizacao = new Date();
		return comentario;
	}

	public void adicionarComentarios(List<Comentario> comentarios) {
		this.comentarios.clear();
		this.comentarios.addAll(comentarios);
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(PostFields.id.name(), this.id);
		entidade.setProperty(PostFields.titulo.name(), this.titulo);
		entidade.setProperty(PostFields.conteudo.name(), new Text(this.conteudo));
		entidade.setProperty(PostFields.quantidadeDeCurtidas.name(), this.quantidadeDeCurtidas);
		entidade.setProperty(PostFields.usuariosQueCurtiram.name(), this.usuariosQueCurtiram);
		entidade.setProperty(PostFields.usuario.name(), this.usuario);
		entidade.setProperty(PostFields.usuarioMD5.name(), this.usuarioMD5);
		entidade.setProperty(PostFields.dataDeCriacao.name(), this.dataDeCriacao);
		entidade.setProperty(PostFields.dataDeAtualizacao.name(), this.dataDeAtualizacao);

		return entidade;
	}

	@Override
	public String toString() {
		return "Post [titulo=" + titulo + ", dataDeAtualizacao=" + dataDeAtualizacao + ", usuario=" + usuario + ", dataDeCriacao=" + dataDeCriacao + ", quantidadeDeCurtidas="
				+ quantidadeDeCurtidas + ", id=" + id + "]";
	}

	@Override
	public Document toDocument() {
		Document document = Document.newBuilder().setId(id)
				.addField(Field.newBuilder().setName(PostFields.id.name()).setText(id))
				.addField(Field.newBuilder().setName(PostFields.dataDeCriacao.name()).setDate(dataDeCriacao))
				.addField(Field.newBuilder().setName(PostFields.titulo.name()).setText(titulo))
				.addField(Field.newBuilder().setName(PostFields.conteudo.name()).setText(conteudo))
				.addField(Field.newBuilder().setName(PostFields.usuario.name()).setText(usuario)).build();
		return document;
	}

	@Override
	public void registraDataDeMigracao(Date data) {
		TimeMachine tMachine = new TimeMachine();
		Date dataFormatada = tMachine.transformaEmData(tMachine.formataData(data));

		super.registraDataDeMigracao(dataFormatada);
		this.dataDeAtualizacao = dataFormatada;
	}
}
