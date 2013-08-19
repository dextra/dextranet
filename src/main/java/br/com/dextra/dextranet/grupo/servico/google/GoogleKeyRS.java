package br.com.dextra.dextranet.grupo.servico.google;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/googlekey")
public class GoogleKeyRS {

	private GoogleKeyRepository repo = new GoogleKeyRepository();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("key") String key) throws EntityNotFoundException {
		GoogleKey googleKey = new GoogleKey(key);
		repo.persiste(googleKey);

		return Response.ok().entity(googleKey).build();
	}

//	@Path("/")
//	@GET
//	@Produces(Application.JSON_UTF8)
//	public Response listar() {
//		List<GoogleKey> googleKey = repo.lista();
//		return Response.ok().entity(googleKey.get(0).getKey()).build();
//	}


}
