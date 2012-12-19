package br.com.dextra.restAPI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.dextra.repository.PostRepository;
import br.com.dextra.utils.EntityJsonConverter;

import com.google.appengine.api.datastore.Entity;


@Path("/posts")
public class PostResource {



	@GET
	@Produces("application/json;charset=UTF-8")
	public static String listarPosts(
			@DefaultValue("") @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q) {

		Iterable<Entity> listaPosts = PostRepository.buscarPosts(maxResults, q);

		return EntityJsonConverter.converterListaEntities(listaPosts).toString();
	}

}
