package br.com.dextra.dextranet.document;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.dextra.dextranet.post.PostRS;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/document")
public class DocumentRS {

	static final String SMAXRESULTS = "20";
	final int MAXRESULTS = Integer.parseInt(SMAXRESULTS);

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public static String listarDocuments(
			@DefaultValue(SMAXRESULTS) @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q,
			@DefaultValue("0") @QueryParam(value = "page") String page) throws NumberFormatException, EntityNotFoundException {

		return null;
		//new PostRS().listarPosts(maxResults, q, page).toString();
	}
}
