package br.com.dextra.dextranet.grupo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailsJSON {
	private String email;

	public EmailsJSON() {
	}

	public EmailsJSON(String email) {
		this.email= email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}