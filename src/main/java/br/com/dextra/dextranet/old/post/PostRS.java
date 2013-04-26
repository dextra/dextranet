package br.com.dextra.dextranet.old.post;

import javax.ws.rs.Path;

@Path("/post")
public class PostRS {

//	PostRepository postRepository = new PostRepository();
//
//	@Path("/")
//	@POST
//	@Produces("application/json;charset=UTF-8")
//	public Response novoPost(@FormParam("title") String titulo, @FormParam("content") String conteudo)
//			throws PolicyException, ScanException, FileNotFoundException, IOException {
//
//		UserService userService = UserServiceFactory.getUserService();
//		String autor = userService.getCurrentUser().getNickname();
//
//		Post post = new Post(titulo, conteudo, autor);
//		postRepository.criar(post);
//
//		return Response.ok().build();
//	}
//
//	@Path("/")
//	@GET
//	@Produces("application/json;charset=UTF-8")
//	public String listarPosts(@DefaultValue("") @QueryParam(value = "max-results") String maxResults,
//			@DefaultValue("") @QueryParam(value = "q") String q,
//			@DefaultValue("0") @QueryParam(value = "page") String page) throws NumberFormatException,
//			EntityNotFoundException {
//
//		List<Post> listaPosts = new ArrayList<Post>();
//
//		int resultsMax = Integer.parseInt(maxResults);
//		int offSet = Integer.parseInt(page) * resultsMax;
//
//		PostRepository novoPost = new PostRepository();
//
//		if (q.equals("")) {
//			listaPosts = novoPost.buscarTodosOsPosts(resultsMax, offSet);
//		} else {
//			listaPosts = novoPost.buscarPosts(resultsMax, q, offSet);
//		}
//		return Converters.converterListaDePostsParaListaDeJson(listaPosts).name();
//	}
//
//	@Path("/{id}")
//	@GET
//	@Produces("application/json;charset=UTF-8")
//	public String buscaPostPorID(@PathParam(value = "id") String id) throws EntityNotFoundException {
//		return new PostRepository().obtemPorId(id).toJson().name();
//	}
//
//	@Path("/{id}")
//	@DELETE
//	@Produces("application/json;charset=UTF-8")
//	public Response removePost(@PathParam(value = "id") String id) {
//		new PostRepository().remove(id);
//
//		return Response.ok().build();
//	}
}
