package br.com.dextra.dextranet.releasenotes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

@Path("/releasenotes")
public class ReleaseNotesRS {

	@GET
	@Produces(Application.JSON_UTF8)
	public Response lista() {
		return null;
	}

	@POST
	@Produces(Application.JSON_UTF8)
	public Response atualiza() {
		
		return null;
	}

}
