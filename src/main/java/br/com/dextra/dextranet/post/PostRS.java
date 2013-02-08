package br.com.dextra.dextranet.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import br.com.dextra.dextranet.utils.Converters;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/post")
public class PostRS {

	PostRepository postRepository = new PostRepository();

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response novoPost(@FormParam("title") String titulo, @FormParam("content") String conteudo) throws PolicyException, 
			ScanException, FileNotFoundException, IOException {

		UserService userService = UserServiceFactory.getUserService();
		String autor = userService.getCurrentUser().getNickname();

		Post post = new Post(titulo, conteudo, autor);
		postRepository.criar(post);

		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String listarPosts(@DefaultValue("") @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q,
			@DefaultValue("0") @QueryParam(value = "page") String page) throws NumberFormatException,
			EntityNotFoundException {

		List<Post> listaPosts = new ArrayList<Post>();

		int resultsMax = Integer.parseInt(maxResults);
		int offSet = Integer.parseInt(page) * resultsMax;

		PostRepository novoPost = new PostRepository();

		if (q.equals("")) {
			listaPosts = novoPost.buscarTodosOsPosts(resultsMax, offSet);
		} else {
			listaPosts = novoPost.buscarPosts(resultsMax, q, offSet);
		}
		return new Converters().converterListaDePostsParaListaDeJson(listaPosts).toString();
	}

	@Path("/{id}")
	@DELETE
	@Produces("application/json;charset=UTF-8")
	public Response removePost(@PathParam(value = "id") String id) {
		new PostRepository().remove(id);

		return Response.ok().build();
	}
}
