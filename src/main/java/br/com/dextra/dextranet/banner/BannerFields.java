package br.com.dextra.dextranet.banner;

public enum BannerFields {
	ID("id"), TITULO("titulo"), BLOBKEY("blobKey"), DATA_INICIO("dataInicio"), DATA_FIM("dataFim");
	
	private String value;

	BannerFields(String value) {
		this.value = value;
	}

	public String getField() {
		return value;
	}

}
