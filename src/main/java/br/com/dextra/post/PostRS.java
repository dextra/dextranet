package br.com.dextra.post;

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
		System.out.println("Inserido? "
				+ novoPost.pegaDadosCorretos(titulo, conteudo, autor));
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public static String listarPosts(
			@DefaultValue(SMAXRESULTS) @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q,
			@DefaultValue("") @QueryParam(value = "key") String key) {
		System.out.println(key);

		if (q.equals("")) {
			Iterable<Entity> listaPosts = PostRepository.buscarTodosOsPosts(
					Integer.parseInt(maxResults), key);
			return EntityJsonConverter.converterListaEntities(listaPosts)
					.toString();
		} else {
			return PostRepository.buscarPosts(Integer.parseInt(maxResults), q)
					.toString();
		}
	}
}
