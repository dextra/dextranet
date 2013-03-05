package br.com.dextra.dextranet.curtida;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.comment.CommentRepository;
import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.dextranet.utils.Converters;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/curtida")
public class CurtidaRS {

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response curtir(@FormParam("id") String id,
			@DefaultValue("true") @FormParam("isPost") String isPost)
			throws EntityNotFoundException {

		UserService userService = UserServiceFactory.getUserService();
		String usuario = userService.getCurrentUser().getNickname();

		// FIXME: Why use this if RestEasy can handle Boolean values?
		if (new Converters().toBoolean(isPost)) {
			Post post = new PostRepository().obtemPorId(id);
			post.receberCurtida(usuario);
		} else {
			Comment comment = new CommentRepository().obtemPorId(id);
			comment.receberCurtida(usuario);
		}

		return Response.ok().build();
	}

}
