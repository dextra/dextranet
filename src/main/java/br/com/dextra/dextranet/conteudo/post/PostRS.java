package br.com.dextra.dextranet.conteudo.post;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

@Path("/post")
public class PostRS {

	private PostRepository repositorioDePosts = new PostRepository();

	private CurtidaRepository repositorioDeCurtidas = new CurtidaRepository();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("titulo") String titulo, @FormParam("conteudo") String conteudo) {
		Post post = new Post(obtemUsuarioLogado());
		post.preenche(titulo, conteudo);

		repositorioDePosts.persiste(post);

		return Response.ok().entity(post).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Post post = repositorioDePosts.obtemPorId(id);
		return Response.ok().entity(post).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar(@QueryParam("r") @DefaultValue(Application.REGISTROS_POR_PAGINA) Integer registrosPorPagina,
			@QueryParam("p") @DefaultValue("1") Integer pagina) {
		List<Post> posts = this.listarPostsOrdenados(registrosPorPagina, pagina);
		return Response.ok().entity(posts).build();
	}

	@Path("/{postId}/curtida")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response curtir(@PathParam("postId") String postId) throws EntityNotFoundException {
		Post post = repositorioDePosts.obtemPorId(postId);
		String usuarioLogado = obtemUsuarioLogado();
		Curtida curtida = post.curtir(usuarioLogado);

		if (curtida != null) {
			repositorioDeCurtidas.persiste(curtida);
		}

		return Response.ok().entity(post).build();
	}

	@Path("/{postId}/curtida")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response descurtir(@PathParam("postId") String postId) throws EntityNotFoundException {
		Post post = repositorioDePosts.obtemPorId(postId);
		String usuarioLogado = obtemUsuarioLogado();

		post.descurtir(usuarioLogado);
		repositorioDeCurtidas.remove(post.getId(), usuarioLogado);

		return Response.ok().entity(post).build();
	}

	@Path("/{postId}/curtida")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listarCurtidas(@PathParam("postId") String postId) throws EntityNotFoundException {
		List<Curtida> curtidas = repositorioDeCurtidas.listaPorConteudo(postId);
		return Response.ok().entity(curtidas).build();
	}

	protected List<Post> listarPostsOrdenados(Integer registrosPorPagina, Integer pagina) {
		EntidadeOrdenacao dataDeAtualizacaoDecrescente = new EntidadeOrdenacao(PostFields.dataDeAtualizacao.name(),
				SortDirection.DESCENDING);

		List<Post> posts = repositorioDePosts.lista(registrosPorPagina, pagina, dataDeAtualizacaoDecrescente);
		return posts;
	}

	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}