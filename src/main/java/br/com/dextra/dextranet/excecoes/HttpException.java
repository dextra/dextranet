package br.com.dextra.dextranet.excecoes;

import javax.ws.rs.core.Response.Status;

public class HttpException extends Exception {

	private static final long serialVersionUID = 1L;

	private Status status;

	public HttpException(Status status) {
		this.status = status;
	}

	public Status status() {
		return this.status;
	}

}
