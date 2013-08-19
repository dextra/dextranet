package br.com.dextra.dextranet.grupo.servico.google;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.dextra.dextranet.grupo.UsuarioJSON;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EdicaoGoogleGrupoJSON {
	private List<String> emailGrupos;
	private List<String> emailUsuarios;


	public EdicaoGoogleGrupoJSON() {
	}

	public EdicaoGoogleGrupoJSON(List<String> emailGrupos, List<String> emailUsuarios) {
		this.emailGrupos = emailGrupos;
		this.emailUsuarios = emailUsuarios;
	}

	public List<String> getEmailGrupos() {
		return emailGrupos;
	}

	public void setEmailGrupos(List<String> emailGrupos) {
		this.emailGrupos = emailGrupos;
	}

	public List<String> getEmailUsuarios() {
		return emailUsuarios;
	}

	public void setEmailUsuarios(List<String> emailUsuarios) {
		this.emailUsuarios = emailUsuarios;
	}


}