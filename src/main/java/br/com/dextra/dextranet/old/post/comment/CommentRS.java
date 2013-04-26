package br.com.dextra.dextranet.old.post.comment;

import javax.ws.rs.Path;

@Path("/comment")
public class CommentRS {

//	private CommentRepository repositorio = new CommentRepository();
//
//	@Path("/")
//	@POST
//	@Produces("application/json;charset=UTF-8")
//	public Response novoComment(@FormParam("text") String text, @FormParam("idReference") String id,
//			@DefaultValue("false") @FormParam("tree") String arvore) throws FileNotFoundException, PolicyException,
//			ScanException, IOException, EntityNotFoundException, ParseException {
//
//		UserService userService = UserServiceFactory.getUserService();
//		String autor = userService.getCurrentUser().getNickname();
//
//		Comment comment = new Comment(text, autor, id, new Converters().toBoolean(arvore));
//
//		repositorio.criar(comment);
//
//		Post post = new PostRepository().obtemPorId(id);
//		post.comentar(comment);
//
//		return Response.ok().build();
//	}
//
//	@Path("/")
//	@GET
//	@Produces("application/json;charset=UTF-8")
//	public String consultar(@DefaultValue("") @QueryParam("idReference") String idReference,
//			@DefaultValue("") @QueryParam("idComment") String idComment) throws EntityNotFoundException {
//		List<Comment> listaComments = new ArrayList<Comment>();
//
//		if (idComment.equals(""))
//			listaComments = repositorio.listarCommentsDeUmPost(idReference);
//
//		else
//			listaComments = repositorio.pegaCommentPorId(idComment);
//
//		return Converters.converterListaDeCommentParaListaDeJson(listaComments).name();
//
//	}

}
