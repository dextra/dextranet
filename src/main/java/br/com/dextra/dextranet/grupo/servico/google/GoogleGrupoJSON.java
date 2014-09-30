package br.com.dextra.dextranet.grupo.servico.google;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.dextra.dextranet.grupo.UsuarioJSON;
import br.com.dextra.dextranet.usuario.Usuario;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGrupoJSON {
	private String id;
	private String idServico;
	private String emailGrupo;
	private List<UsuarioJSON> usuarioJSONs;
	private String emailsExternos;

	public GoogleGrupoJSON() {
	}

	public GoogleGrupoJSON(String id, String idServico, String emailGrupo, List<UsuarioJSON> usuarioJSONs, String emailsExternos) {
		this.id = id;
		this.idServico = idServico;
		this.emailGrupo = emailGrupo;
		this.usuarioJSONs = usuarioJSONs;
		this.emailsExternos = emailsExternos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailDomainGrupo() {
		return emailGrupo + Usuario.DEFAULT_DOMAIN;
	}
	
	public String getEmailGrupo() {
		return emailGrupo;
	}

	public void setEmailGrupo(String emailGrupo) {
		this.emailGrupo = emailGrupo;
	}


	public String getIdServico() {
		return idServico;
	}

	public void setIdServico(String idServico) {
		this.idServico = idServico;
	}

	public List<UsuarioJSON> getUsuarioJSONs() {
		return usuarioJSONs;
	}

	public void setUsuarioJSONs(List<UsuarioJSON> usuarioJSONs) {
		this.usuarioJSONs = usuarioJSONs;
	}
	
	public String getEmailsExternos() {
		return emailsExternos;
	}

	public void setEmailsExternos(String emailsExternos) {
		this.emailsExternos = emailsExternos;
	}

}