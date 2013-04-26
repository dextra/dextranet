package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/post")
public class ComentarioRS {

	private PostRepository repositorioDePosts = new PostRepository();

	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();

	@Path("/{postId}/comentario")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response comentar(@PathParam("postId") String postId, @FormParam("conteudo") String conteudo)
			throws EntityNotFoundException {
		Post post = repositorioDePosts.obtemPorId(postId);
		Comentario comentario = post.comentar(this.obtemUsuarioLogado(), conteudo);

		repositorioDePosts.persiste(post);
		repositorioDeCurtidas.persiste(comentario);

		return Response.ok().entity(post).build();
	}

	@Path("/{postId}/comentario")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listarComentarios(@PathParam("postId") String postId) throws EntityNotFoundException {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Path("/{postId}/comentario")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response descomentar(@PathParam("postId") String postId) throws EntityNotFoundException {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Path("/{postId}/comentario/{comentarioId}/curtida")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response curtir(@PathParam("postId") String postId, @PathParam("comentarioId") String comentarioId)
			throws EntityNotFoundException {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Path("/{postId}/comentario/{comentarioId}/curtida")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response descurtir(@PathParam("postId") String postId, @PathParam("comentarioId") String comentarioId)
			throws EntityNotFoundException {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Path("/{postId}/comentario/{comentarioId}/curtida")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listarCurtidas(@PathParam("postId") String postId, @PathParam("comentarioId") String comentarioId)
			throws EntityNotFoundException {
		List<Curtida> curtidas = repositorioDeCurtidas.listaPorConteudo(comentarioId);
		return Response.ok().entity(curtidas).build();
	}

	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}
