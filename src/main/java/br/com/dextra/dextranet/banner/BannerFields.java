package br.com.dextra.dextranet.banner;

public enum BannerFields {
	ID("id"), TITULO("titulo"), BLOBKEY("blobKey"), DATA_INICIO("dataInicio"), DATA_FIM("dataFim"), JA_COMECOU(
			"jaComecou"), JA_TERMINOU("jaTerminou"), USUARIO("usuario"), DATA_DE_ATUALIZACAO("dataDeAtualizacao");

	private String value;

	BannerFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}

}
