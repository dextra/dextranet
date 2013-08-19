package br.com.dextra.dextranet.grupo.servico.google;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.dextra.dextranet.grupo.UsuarioJSON;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGrupoJSON {
	private String id;
	private String idServico;
	private String nomeEmailGrupo;
	private String emailGrupo;
	private List<UsuarioJSON> usuarioJSONs;


	public GoogleGrupoJSON() {
	}

	public GoogleGrupoJSON(String id, String idServico, String nomeEmailGrupo,String emailGrupo, List<UsuarioJSON> usuarioJSONs) {
		this.id = id;
		this.idServico = idServico;
		this.nomeEmailGrupo = nomeEmailGrupo;
		this.emailGrupo = emailGrupo;
		this.usuarioJSONs = usuarioJSONs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomeEmailGrupo() {
		return nomeEmailGrupo;
	}

	public void setNomeEmailGrupo(String nomeEmailGrupo) {
		this.nomeEmailGrupo = nomeEmailGrupo;
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


}