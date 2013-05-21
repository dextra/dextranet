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
	private PostRepository repositorioDePosts = new PostRepository();
	private IndexacaoRepository repositorioDeIndex = new IndexacaoRepository();

	@GET
	@Path("/")
	@Produces(Application.JSON_UTF8)
	public Response buscarConteudo(@QueryParam("query") String query) throws EntityNotFoundException, HttpException {
		try {
			Set<Post> ret = new HashSet<Post>();

			List<Post> posts = repositorioDeIndex.buscar(Post.class, query);
			for (Post post : posts) {
				ret.add(repositorioDePosts.obtemPorId(post.getId()));
			}

			List<Comentario> comentarios = repositorioDeIndex.buscar(Comentario.class, query);
			for (Comentario comentario : comentarios) {
				ret.add(repositorioDePosts.obtemPorId(comentario.getPostId()));
			}

			return Response.ok().entity(ret).build();
		} catch (SearchQueryException e) {
			throw new HttpException(500);
		}
	}

}