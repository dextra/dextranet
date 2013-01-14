package br.com.dextra.dextranet.comment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.utils.Converters;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/comment")
public class CommentRS {

	private CommentRepository repositorio = new CommentRepository();

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response novoComment(@FormParam("text") String text,
			@FormParam("author") String autor,
			@FormParam("idReference") String id,
			@DefaultValue("false") @FormParam("tree") String arvore)
			throws FileNotFoundException, PolicyException, ScanException,
			IOException, EntityNotFoundException {

		Comment comment = new Comment(text, autor, id, new Converters()
				.toBoolean(arvore));

		repositorio.criar(comment);
		new Post().comentar(comment);

		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String consultar(@FormParam("id") String idReference)
			throws EntityNotFoundException {

		List<Comment> listaComments = new ArrayList<Comment>();
		listaComments = repositorio.listarCommentsDeUmPost(idReference);
		System.out.println("OH A LISTA AQUI OH (RS): "+listaComments);
		return new Converters().converterListaDeCommentParaListaDeJson(listaComments).toString();
	}

}
