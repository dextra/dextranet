package br.com.dextra.dextranet.perfil;

public enum PerfilFields {

	NAME("name"), AREA("area"), UNIT("unit"), BRANCH("branch"), SKYPE("skype"),
	GTALK("gTalk"), PHONERESIDENCE("phoneResidence"), PHONEMOBILE("phoneMobile"), GITHUBUSER("githubUser");

	private String value;

	PerfilFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}
}
