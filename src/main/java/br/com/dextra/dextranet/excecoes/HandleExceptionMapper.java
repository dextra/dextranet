package br.com.dextra.dextranet.excecoes;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.dextra.dextranet.messages.Messages;

@Provider
public class HandleExceptionMapper implements ExceptionMapper<HttpException> {

	@Override
	public Response toResponse(HttpException exception) {
		switch (exception.httpCode()) {
		case 400:
			return createResponse(Status.BAD_REQUEST, Messages.getMessage("http.error.400"));
		case 500:
			return createResponse(Status.INTERNAL_SERVER_ERROR, Messages.getMessage("http.error.500"));
		default:
			throw new RuntimeException("[" + getClass().getName() + "]: code" + exception.httpCode() + " not found");
		}
	}

	private Response createResponse(Status status, String message) {
		return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
	}

}