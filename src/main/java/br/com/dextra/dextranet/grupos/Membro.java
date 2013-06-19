package br.com.dextra.dextranet.grupos;

import com.google.appengine.api.datastore.Entity;

import br.com.dextra.dextranet.persistencia.Entidade;

public class Membro extends Entidade {
	private String id;
	private String idUsuario;
	private String idGrupo;

	public Membro(String idUsuario, String idGrupo) {
		super();
		this.idUsuario = idUsuario;
		this.idGrupo = idGrupo;
	}

	public Membro(Entity entity) {
		this.id = (String) entity.getProperty(MembroFields.id.name());
		this.idGrupo = (String) entity.getProperty(MembroFields.idGrupo.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(MembroFields.id.name(), this.id);
		entidade.setProperty(MembroFields.idGrupo.name(), this.idGrupo);

		return entidade;
	}

	public String getId() {
		return id;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

}
