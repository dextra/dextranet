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

@Path("/comentario")
public class ComentarioRS {

	private PostRepository repositorioDePosts = new PostRepository();

	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();

	@Path("/{postId}")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response comentar(@PathParam("postId") String postId, @FormParam("conteudo") String conteudo)
			throws EntityNotFoundException {
		Post post = repositorioDePosts.obtemPorId(postId);
		Comentario comentario = post.comentar(this.obtemUsuarioLogado(), conteudo);
		repositorioDeComentarios.persiste(comentario);

		return Response.ok().entity(post).build();
	}

	@Path("/{postId}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listarComentarios(@PathParam("postId") String postId) throws EntityNotFoundException {
		List<Comentario> comentarios = repositorioDeComentarios.listaPorPost(postId);
		return Response.ok().entity(comentarios).build();
	}

	@Path("/{postId}/{comentarioId}/curtida")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response curtir(@PathParam("postId") String postId, @PathParam("comentarioId") String comentarioId)
			throws EntityNotFoundException {
		Comentario comentario = repositorioDeComentarios.obtemPorId(comentarioId);
		Curtida curtida = comentario.curtir(this.obtemUsuarioLogado());

		if (curtida != null) {
			repositorioDeCurtidas.persiste(curtida);
		}

		repositorioDeComentarios.persiste(comentario);

		return Response.ok().entity(comentario).build();
	}

	@Path("/{postId}/{comentarioId}/curtida")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response descurtir(@PathParam("postId") String postId, @PathParam("comentarioId") String comentarioId)
			throws EntityNotFoundException {
		Comentario comentario = repositorioDeComentarios.obtemPorId(comentarioId);
		comentario.descurtir(this.obtemUsuarioLogado());

		repositorioDeCurtidas.remove(comentarioId, obtemUsuarioLogado());
		repositorioDeComentarios.persiste(comentario);

		return Response.ok().entity(comentario).build();
	}

	@Path("/{postId}/{comentarioId}/curtida")
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
