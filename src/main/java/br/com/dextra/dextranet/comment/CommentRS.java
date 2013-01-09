package br.com.dextra.dextranet.comment;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.utils.CommentToJson;

import com.google.appengine.api.datastore.Entity;

@Path("/comment")
public class CommentRS {

	private CommentRepository repositorio = new CommentRepository();

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response novoComment(@FormParam("texto") String text ,
			@FormParam("author") String autor,
			@FormParam("idReference") String id,
			@DefaultValue("false") @FormParam("tree") String arvore) {

		boolean tree=false;
		if(arvore.equals("true"))
			tree=true;

		Comment comment = new Comment(text, autor, id, tree);
		repositorio.criar(comment);

		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String consultar() {
		Iterable<Entity> retorno = this.repositorio.buscar();

		return CommentToJson.converterListaEntities(retorno).toString();
	}

}
