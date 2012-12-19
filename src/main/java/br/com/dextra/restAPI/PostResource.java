package br.com.dextra.restAPI;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Path("/posts")
public class PostResource {

	@GET
	@Produces("application/json;charset=UTF-8")
	public String listarPosts(
			@DefaultValue("") @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q) {
		/*ArrayList<String> listaDePosts = new ArrayList<String>();
		if (!q.equalsIgnoreCase("")) {
			buscarPostsPorNome(q);
		}
		else if (!maxResults.equalsIgnoreCase("")) {{
			buscarPosts(q);
		}*/

/*		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query("post");
		PreparedQuery prepare = datastore.prepare(query);

		Iterable<Entity> asIterable = prepare.asIterable();

		for (Entity entity : asIterable) {
			JsonObject json = new JsonObject();
			for (Entry<String, Object> entry : entity.getProperties().entrySet()) {
				json.addProperty(entry.getKey(), entry.getValue().toString());
			}
			listaDePosts.add(json);
		}
*/
		return q;
	}

	private void buscarPostsPorNome(String queryParam) {
		// TODO Auto-generated method stub

	}

}
