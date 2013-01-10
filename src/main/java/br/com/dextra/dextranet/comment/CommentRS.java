package br.com.dextra.dextranet.comment;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import br.com.dextra.dextranet.utils.CommentToJson;
import br.com.dextra.dextranet.utils.Converters;

@Path("/comment")
public class CommentRS {

	private CommentRepository repositorio = new CommentRepository();

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response novoComment(@FormParam("text") String text ,
			@FormParam("author") String autor,
			@FormParam("idReference") String id,
			@DefaultValue("false") @FormParam("tree") String arvore) throws FileNotFoundException, PolicyException, ScanException, IOException {

		Comment comment = new Comment(text, autor, id, new Converters().toBoolean(arvore));
		repositorio.criar(comment);

		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String consultar() {
		//Iterable<Entity> retorno = this.repositorio.buscar();

		return CommentToJson.converterListaEntities(null).toString();
	}

}
