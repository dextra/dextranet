package br.com.dextra.dextranet.indexacao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.excecoes.HttpException;
import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.search.SearchQueryException;

@Path("/indexacao")
public class IndexacaoRS {

	@GET
	@Path("/")
	@Produces(Application.JSON_UTF8)
	public Response buscarConteudo(@QueryParam("query") String query) throws EntityNotFoundException, HttpException {
		try {
			IndexacaoRepository repositorioDeIndex = new IndexacaoRepository();
			PostRepository repositorioDePosts = new PostRepository();

			Set<Post> posts = new HashSet<Post>(repositorioDeIndex.buscar(Post.class, query));
			List<Comentario> comentarios = repositorioDeIndex.buscar(Comentario.class, query);
			for (Comentario comentario : comentarios) {
				posts.add(repositorioDePosts.obtemPorId(comentario.getPostId()));
			}

			return Response.ok().entity(posts).build();
		} catch (SearchQueryException e) {
			throw new HttpException(500);
		}
	}

}
