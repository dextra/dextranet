package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

@Path("/post")
public class PostRS {

	private PostRepository repositorio = new PostRepository();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("titulo") String titulo, @FormParam("conteudo") String conteudo) {
		Post post = new Post(AutenticacaoService.identificacaoDoUsuarioLogado());
		post.preenche(titulo, conteudo);

		repositorio.persiste(post);

		return Response.ok().entity(post).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Post post = repositorio.obtemPorId(id);
		return Response.ok().entity(post).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Post> posts = this.listarPostsOrdenados();
		return Response.ok().entity(posts).build();
	}

	protected List<Post> listarPostsOrdenados() {
		EntidadeOrdenacao dataDeAtualizacaoDecrescente = new EntidadeOrdenacao(PostFields.dataDeAtualizacao.toString(),
				SortDirection.DESCENDING);

		List<Post> posts = repositorio.lista(dataDeAtualizacaoDecrescente);
		return posts;
	}

}