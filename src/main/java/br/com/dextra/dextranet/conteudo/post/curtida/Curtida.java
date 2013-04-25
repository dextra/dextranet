package br.com.dextra.dextranet.conteudo.post.curtida;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;

public class Curtida extends Entidade {

	private String postId;

	private String usuario;

	private String usuarioMD5;

	private Date data;

	public Curtida(String postId, String username) {
		super();
		this.postId = postId;
		this.usuario = username;
		this.usuarioMD5 = Usuario.geraMD5(username);
		this.data = new TimeMachine().dataAtual();
	}

	public Curtida(Entity entity) {
		this.id = (String) entity.getProperty(CurtidaFields.id.toString());
		this.usuario = (String) entity.getProperty(CurtidaFields.usuario.toString());
		this.usuarioMD5 = (String) entity.getProperty(CurtidaFields.usuarioMD5.toString());
		this.postId = (String) entity.getProperty(CurtidaFields.postId.toString());
		this.data = (Date) entity.getProperty(CurtidaFields.data.toString());
	}

	public String getPostId() {
		return postId;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getUsuarioMD5() {
		return usuarioMD5;
	}

	public Date getData() {
		return data;
	}

	@Override
	public Entity toEntity() {
		Entity entity = new Entity(this.getKey(this.getClass()));

		entity.setProperty(CurtidaFields.id.toString(), this.id);
		entity.setProperty(CurtidaFields.usuario.toString(), this.usuario);
		entity.setProperty(CurtidaFields.usuarioMD5.toString(), this.usuarioMD5);
		entity.setProperty(CurtidaFields.postId.toString(), this.postId);
		entity.setProperty(CurtidaFields.data.toString(), this.data);

		return entity;
	}

}
