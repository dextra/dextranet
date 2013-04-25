package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.Date;
import java.util.List;

import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

public class Comentario extends Conteudo {

	private String postId;

	public Comentario(String postId, String username, String conteudo) {
		super(username);
		this.postId = postId;
		this.conteudo = new ConteudoHTML(conteudo).removeJavaScript();
	}

	@SuppressWarnings("unchecked")
	public Comentario(Entity entity) {
		super((String) entity.getProperty(ComentarioFields.usuario.toString()));
		this.id = (String) entity.getProperty(ComentarioFields.id.toString());
		this.conteudo = ( (Text) entity.getProperty(ComentarioFields.conteudo.toString()) ).getValue();
		this.quantidadeDeCurtidas = (Long) entity.getProperty(ComentarioFields.quantidadeDeCurtidas.toString());
		this.usuariosQueCurtiram = (List<String>) entity.getProperty(ComentarioFields.usuariosQueCurtiram.toString());
		this.dataDeCriacao = (Date) entity.getProperty(ComentarioFields.dataDeCriacao.toString());
		this.postId = (String) entity.getProperty(ComentarioFields.postId.toString());
	}

	public String getPostId() {
		return postId;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(ComentarioFields.id.toString(), this.id);
		entidade.setProperty(ComentarioFields.conteudo.toString(), new Text(this.conteudo));
		entidade.setProperty(ComentarioFields.quantidadeDeCurtidas.toString(), this.quantidadeDeCurtidas);
		entidade.setProperty(ComentarioFields.usuariosQueCurtiram.toString(), this.usuariosQueCurtiram);
		entidade.setProperty(ComentarioFields.usuario.toString(), this.usuario);
		entidade.setProperty(ComentarioFields.usuarioMD5.toString(), this.usuarioMD5);
		entidade.setProperty(ComentarioFields.dataDeCriacao.toString(), this.dataDeCriacao);
		entidade.setProperty(ComentarioFields.postId.toString(), this.postId);

		return entidade;
	}

}
