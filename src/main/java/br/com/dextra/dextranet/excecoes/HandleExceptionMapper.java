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
		Status status = exception.status();
		switch (status) {
		case BAD_REQUEST:
			return createResponse(status, Messages.getMessage("http.error.400"));
		case NOT_FOUND:
			return createResponse(status, Messages.getMessage("http.error.404"));
		case INTERNAL_SERVER_ERROR:
			return createResponse(status, Messages.getMessage("http.error.500"));
		default:
			throw new RuntimeException("[" + getClass().getName() + "]: status " + status + " not found");
		}
	}

	private Response createResponse(Status status, String message) {
		return Response.status(status).entity(message).type(MediaType.TEXT_PLAIN).build();
	}

}