package br.com.dextra.dextranet.grupo;

import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;

public class Membro extends Entidade {
	private String idUsuario;
	private String idGrupo;
	private String nomeUsuario;
	private String email;

	public Membro(String idUsuario, String idGrupo, String nomeUsuario, String email) {
		super();
		this.idUsuario = idUsuario;
		this.idGrupo = idGrupo;
		this.nomeUsuario = nomeUsuario;
		this.email = email;
	}

	public Membro(Entity entity) {
		this.id = (String) entity.getProperty(MembroFields.id.name());
		this.idGrupo = (String) entity.getProperty(MembroFields.idGrupo.name());
		this.idUsuario = (String) entity.getProperty(MembroFields.idUsuario.name());
		this.nomeUsuario = (String) entity.getProperty(MembroFields.nomeUsuario.name());
		this.email = (String) entity.getProperty(MembroFields.email.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(MembroFields.id.name(), this.id);
		entidade.setProperty(MembroFields.idGrupo.name(), this.idGrupo);
		entidade.setProperty(MembroFields.idUsuario.name(), this.idUsuario);
		entidade.setProperty(MembroFields.nomeUsuario.name(), this.nomeUsuario);
		entidade.setProperty(MembroFields.email.name(), this.email);

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this.getClass())
			.append("id", this.id)
				.append("idUsuario", this.idUsuario)
					.append("nomeUsuario", this.nomeUsuario)
						.append("idGrupo", this.idGrupo)
							.append("email", this.email).toString();
	}

}
