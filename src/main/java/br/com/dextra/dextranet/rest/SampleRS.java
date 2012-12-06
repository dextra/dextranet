package br.com.dextra.dextranet.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/sample")
public class SampleRS {

	@GET
	@Path("/{value}")
	@Produces("application/json;charset=UTF-8")
	public String simpleService(@PathParam("value") String value) {
		StringBuilder json = new StringBuilder();
		json.append("{ \"value\" : \"");
		json.append(value);
		json.append("\" }");

		return json.toString();
	}

}
