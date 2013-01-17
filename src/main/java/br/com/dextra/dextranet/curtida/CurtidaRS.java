package br.com.dextra.dextranet.curtida;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;

import com.google.appengine.api.datastore.EntityNotFoundException;



@Path("/curtida")
public class CurtidaRS {

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response curtir(@FormParam("usuario") String usuario, @FormParam("id") String id) throws EntityNotFoundException {
		Post post = new PostRepository().obtemPorId(id);
		post.curtir(usuario);

		return Response.ok().build();
	}

}
