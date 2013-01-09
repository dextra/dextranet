package br.com.dextra.dextranet.comment;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.utils.CommentToJson;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/comment")
public class CommentRS {

	private CommentRepository repositorio = new CommentRepository();


	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response criar(@FormParam("text") String text, @FormParam("post")String PostID, @FormParam("autor") String autor)throws EntityNotFoundException{

		repositorio.criar(text,autor,PostID);
		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String consultar(){
		Iterable<Entity> retorno = this.repositorio.buscar();

		return	CommentToJson.converterListaEntities(retorno).toString();
	}

}
