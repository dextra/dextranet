package br.com.dextra.dextranet.conteudo.post.curtida;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;

public class Curtida extends Entidade {

	private String conteudoId;

	private String usuario;

	private String usuarioMD5;

	private Date data;

	public Curtida(String conteudoId, String username) {
		super();
		this.conteudoId = conteudoId;
		this.usuario = username;
		this.usuarioMD5 = Usuario.geraMD5(username);
		this.data = new TimeMachine().dataAtual();
	}

	public Curtida(Entity entity) {
		this.id = (String) entity.getProperty(CurtidaFields.id.name());
		this.usuario = (String) entity.getProperty(CurtidaFields.usuario.name());
		this.usuarioMD5 = (String) entity.getProperty(CurtidaFields.usuarioMD5.name());
		this.conteudoId = (String) entity.getProperty(CurtidaFields.conteudoId.name());
		this.data = (Date) entity.getProperty(CurtidaFields.data.name());
	}

	public String getConteudoId() {
		return conteudoId;
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

		entity.setProperty(CurtidaFields.id.name(), this.id);
		entity.setProperty(CurtidaFields.usuario.name(), this.usuario);
		entity.setProperty(CurtidaFields.usuarioMD5.name(), this.usuarioMD5);
		entity.setProperty(CurtidaFields.conteudoId.name(), this.conteudoId);
		entity.setProperty(CurtidaFields.data.name(), this.data);

		return entity;
	}

}
