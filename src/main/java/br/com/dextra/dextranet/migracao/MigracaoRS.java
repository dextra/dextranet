package br.com.dextra.dextranet.migracao;

import java.util.Date;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRS;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRS;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/migracao")
public class MigracaoRS {

	private PostRepository repositorioDePosts = new PostRepository();

	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	@Path("/post")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserirPost(@FormParam("data") Date data, @FormParam("usuario") String username,
			@FormParam("titulo") String titulo, @FormParam("conteudo") String conteudo) {
		Post post = criaNovoPostParaMigracao(data, username, titulo, conteudo);
		return Response.ok().entity(post).build();
	}

	@Path("/post/${postId}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obterPost(@PathParam("postId") String postId) throws EntityNotFoundException {
		PostRS postRest = new PostRS();
		return postRest.obter(postId);
	}

	@Path("/post/${postId}/comentario")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserirComentario(@PathParam("postId") String postId, @FormParam("data") Date data,
			@FormParam("usuario") String usuario, @FormParam("conteudo") String conteudo)
			throws EntityNotFoundException {
		Comentario comentario = criaNovoComentarioParaMigracao(postId, data, usuario, conteudo);
		return Response.ok().entity(comentario).build();
	}

	@Path("/post/${postId}/comentario")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response inserirComentario(@PathParam("postId") String postId) throws EntityNotFoundException {
		ComentarioRS comentarioRest = new ComentarioRS();
		return comentarioRest.listarComentarios(postId);
	}

	protected Post criaNovoPostParaMigracao(Date data, String username, String titulo, String conteudo) {
		Post postMigracao = new Post(username, titulo, conteudo);
		postMigracao.registraDataDeMigracao(data);

		return repositorioDePosts.persiste(postMigracao);
	}

	protected Comentario criaNovoComentarioParaMigracao(String postId, Date data, String username, String conteudo)
			throws EntityNotFoundException {
		Post post = repositorioDePosts.obtemPorId(postId);

		Comentario comentarioMigracao = post.comentar(username, conteudo);
		comentarioMigracao.registraDataDeMigracao(data);

		repositorioDePosts.persiste(post);
		return repositorioDeComentarios.persiste(comentarioMigracao);
	}

}
