package br.com.dextra.dextranet.grupo;

import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.datastore.Entity;

public class ServicoGrupo extends Entidade {
	private String idServico;
	private String idGrupo;
	private String emailGrupo;
	private String emailsExternos;

	public ServicoGrupo(String idServico, String idGrupo, String emailGrupo) {
		super();
		this.idServico = idServico;
		this.idGrupo = idGrupo;
		this.emailGrupo = emailGrupo;
	}

	public ServicoGrupo(String idServico, String idGrupo, String emailGrupo, String emailsExternos) {
		super();
		this.idServico = idServico;
		this.idGrupo = idGrupo;
		this.emailGrupo = emailGrupo;
		this.emailsExternos = emailsExternos;
	}

	public ServicoGrupo(Entity entity) {
		this.id = (String) entity.getProperty(ServicoGrupoFields.id.name());
		this.idServico = (String) entity.getProperty(ServicoGrupoFields.idServico.name());
		this.idGrupo = (String) entity.getProperty(ServicoGrupoFields.idGrupo.name());
		this.emailGrupo = (String) entity.getProperty(ServicoGrupoFields.emailGrupo.name());
		this.emailsExternos = ((String) entity.getProperty(ServicoGrupoFields.emailsExternos.name()));
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(ServicoGrupoFields.id.name(), this.id);
		entidade.setProperty(ServicoGrupoFields.idServico.name(), this.idServico);
		entidade.setProperty(ServicoGrupoFields.idGrupo.name(), this.idGrupo);
		entidade.setProperty(ServicoGrupoFields.emailGrupo.name(), this.emailGrupo);
		entidade.setProperty(ServicoGrupoFields.emailsExternos.name(), this.getEmailsExternos());

		return entidade;
	}

	public ServicoGrupo preenche(String emailsExternos) {
		this.emailsExternos = emailsExternos;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public String getIdServico() {
		return idServico;
	}

	public String getEmailGrupo() {
		return emailGrupo;
	}

	public void setEmailGrupo(String emailGrupo) {
		this.emailGrupo = emailGrupo;
	}

	public String getEmailsExternos() {
		return emailsExternos;
	}

	public void setEmailsExternos(String emailsExternos) {
		this.emailsExternos = emailsExternos;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this.getClass())
			.append("id", this.id)
				.append("idServico", this.idServico)
					.append("idGrupo", this.idGrupo)
						.append("emailGrupo", this.emailGrupo)
							.append("emailsExternos", this.emailsExternos).toString();
	}

}
