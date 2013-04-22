package br.com.dextra.dextranet.post.comment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.dextranet.utils.Converters;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Path("/comment")
public class CommentRS {

	private CommentRepository repositorio = new CommentRepository();

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public Response novoComment(@FormParam("text") String text, @FormParam("idReference") String id,
			@DefaultValue("false") @FormParam("tree") String arvore) throws FileNotFoundException, PolicyException,
			ScanException, IOException, EntityNotFoundException, ParseException {

		UserService userService = UserServiceFactory.getUserService();
		String autor = userService.getCurrentUser().getNickname();

		Comment comment = new Comment(text, autor, id, new Converters().toBoolean(arvore));

		repositorio.criar(comment);

		Post post = new PostRepository().obtemPorId(id);
		post.comentar(comment);

		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String consultar(@DefaultValue("") @QueryParam("idReference") String idReference,
			@DefaultValue("") @QueryParam("idComment") String idComment) throws EntityNotFoundException {
		List<Comment> listaComments = new ArrayList<Comment>();

		if (idComment.equals(""))
			listaComments = repositorio.listarCommentsDeUmPost(idReference);

		else
			listaComments = repositorio.pegaCommentPorId(idComment);

		return Converters.converterListaDeCommentParaListaDeJson(listaComments).toString();

	}

}
