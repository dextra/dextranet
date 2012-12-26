package br.com.dextra.post;

import java.util.Iterator;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.dextra.repository.PostRepository;
import br.com.dextra.utils.EntityJsonConverter;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/post")
public class PostRS {

	static final String SMAXRESULTS = "20";
	final int MAXRESULTS = Integer.parseInt(SMAXRESULTS);

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void novoPost(@FormParam("title") String titulo,
			@FormParam("content") String conteudo) {

		String autor = "usuarioTemporario";
		PostRepository novoPost = new PostRepository();
		novoPost.criaNovoPost(titulo, conteudo, autor);
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public static String listarPosts(
			@DefaultValue(SMAXRESULTS) @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q,
			@DefaultValue("1") @QueryParam(value = "page") String page) throws NumberFormatException, EntityNotFoundException {

		Iterable<Entity> listaPosts;

		if (q.equals("")) {
			listaPosts = PostRepository.buscarTodosOsPosts(Integer
					.parseInt(maxResults), page);
		} else {
			listaPosts = PostRepository.buscarPosts(Integer
					.parseInt(maxResults), q,page);
		}
		return EntityJsonConverter.converterListaEntities(listaPosts)
				.toString();
	}
}
