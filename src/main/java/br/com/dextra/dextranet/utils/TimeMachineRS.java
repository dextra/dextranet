package br.com.dextra.dextranet.utils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

@Path("/timeMachine")
public class TimeMachineRS {
	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response dataAtual() {
		return Response.ok().entity((new TimeMachine()).dataAtual()).build();
	}

}