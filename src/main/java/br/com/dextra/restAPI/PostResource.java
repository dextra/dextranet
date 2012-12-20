package br.com.dextra.restAPI;

import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.dextra.repository.PostRepository;
import br.com.dextra.utils.EntityJsonConverter;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;


@Path("/posts")
public class PostResource {



	@GET
	@Produces("application/json;charset=UTF-8")
	public static ArrayList<JsonObject> listarPosts(
			@DefaultValue("20") @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q) {
		if(q.equals(""))
		{
		Iterable<Entity> listaPosts = PostRepository.buscarTodosOsPosts(Integer.parseInt(maxResults));
		return EntityJsonConverter.converterListaEntities(listaPosts);
		}
		else
		{

			return null;
		}

	}

}
