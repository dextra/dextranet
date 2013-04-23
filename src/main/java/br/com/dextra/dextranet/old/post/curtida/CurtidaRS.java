package br.com.dextra.dextranet.old.post.curtida;

import javax.ws.rs.Path;

@Path("/curtida")
public class CurtidaRS {

//	@Path("/")
//	@POST
//	@Produces("application/json;charset=UTF-8")
//	public Response curtir(@FormParam("id") String id, @DefaultValue("true") @FormParam("isPost") String isPost)
//			throws EntityNotFoundException {
//
//		UserService userService = UserServiceFactory.getUserService();
//		String usuario = userService.getCurrentUser().getNickname();
//
//		// FIXME: Why use this if RestEasy can handle Boolean values?
//		if (new Converters().toBoolean(isPost)) {
//			Post post = new PostRepository().obtemPorId(id);
//			post.receberCurtida(usuario);
//		} else {
//			Comment comment = new CommentRepository().obtemPorId(id);
//			comment.receberCurtida(usuario);
//		}
//
//		return Response.ok().build();
//	}

}
