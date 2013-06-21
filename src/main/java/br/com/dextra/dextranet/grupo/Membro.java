package br.com.dextra.dextranet.grupo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.appengine.api.datastore.Entity;

import br.com.dextra.dextranet.persistencia.Entidade;

public class Membro extends Entidade {
	private String idUsuario;
	private String idGrupo;
	private String nomeUsuario;

	public Membro(String idUsuario, String idGrupo, String nomeUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.idGrupo = idGrupo;
		this.nomeUsuario = nomeUsuario;
	}

	public Membro(Entity entity) {
		this.id = (String) entity.getProperty(MembroFields.id.name());
		this.idGrupo = (String) entity.getProperty(MembroFields.idGrupo.name());
		this.idUsuario = (String) entity.getProperty(MembroFields.idUsuario.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(MembroFields.id.name(), this.id);
		entidade.setProperty(MembroFields.idGrupo.name(), this.idGrupo);
		entidade.setProperty(MembroFields.idUsuario.name(), this.idUsuario);

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

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this.getClass())
			.append("id", this.id)
				.append("idUsuario", this.idUsuario)
					.append("nomeUsuario", this.nomeUsuario)
						.append("idGrupo", this.idGrupo).toString();
	}
}
