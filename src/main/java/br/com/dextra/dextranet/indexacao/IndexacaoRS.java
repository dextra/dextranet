package br.com.dextra.dextranet.indexacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostFields;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.excecoes.HttpException;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.SearchQueryException;
import com.google.appengine.api.search.SortExpression;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.api.search.SortOptions.Builder;

@Path("/indexacao")
public class IndexacaoRS {
	private PostRepository repositorioDePosts = new PostRepository();
	private IndexacaoRepository repositorioDeIndex = new IndexacaoRepository();

	@GET
	@Path("/")
	@Produces(Application.JSON_UTF8)
	public Response buscarConteudo(@QueryParam("query") String query) throws HttpException {
		try {
			List<Post> ret = new ArrayList<Post>();

			ConteudoHTML conteudoHTML = new ConteudoHTML(query);
			query = conteudoHTML.removeJavaScript();

			Query postQuery = criarOpcoesOrdenacaoPosts(query);
			List<Post> posts = repositorioDeIndex.buscar(Post.class, postQuery);
			for (Conteudo post : posts) {
				ret.add(repositorioDePosts.obtemPorId(post.getId()));
			}

			List<Comentario> comentarios = repositorioDeIndex.buscar(Comentario.class, query);
			for (Comentario comentario : comentarios) {
				Post post = repositorioDePosts.obtemPorId(comentario.getPostId());
				if(!ret.contains(post)) {
					ret.add(post);
				}
			}

			return Response.ok().entity(ret).build();
		} catch (SearchQueryException e) {
			throw new HttpException(Status.INTERNAL_SERVER_ERROR);
		} catch (EntityNotFoundException e) {
			throw new HttpException(Status.NOT_FOUND);
		}
	}

	private Query criarOpcoesOrdenacaoPosts(String query) {
		Builder sortOptions = SortOptions.newBuilder();
		sortOptions
				.addSortExpression(
						SortExpression.newBuilder().setExpression(PostFields.dataDeCriacao.name()).setDirection(SortExpression.SortDirection.DESCENDING)
								.setDefaultValueDate(new Date())).setLimit(Application.LIMITE_REGISTROS_FULL_TEXT_SEARCH).build();

		QueryOptions options = QueryOptions.newBuilder().setLimit(Application.LIMITE_REGISTROS_FULL_TEXT_SEARCH).setSortOptions(sortOptions).build();
		return Query.newBuilder().setOptions(options).build(query);

	}

	@GET
	@Path("/delete")
	public void apagarIndicesFTS(){
		repositorioDeIndex.apagaIndices(Post.class);
		repositorioDeIndex.apagaIndices(Comentario.class);
	}

}
