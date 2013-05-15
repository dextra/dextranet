package br.com.dextra.dextranet.excecoes;

public class HttpException extends Exception {

	private static final long serialVersionUID = 1L;

	private int httpCode;

	public HttpException(int httpCode) {
		this.httpCode = httpCode;
	}

	public int httpCode() {
		return httpCode;
	}

}
