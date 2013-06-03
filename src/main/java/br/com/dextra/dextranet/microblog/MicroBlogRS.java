package br.com.dextra.dextranet.microblog;

import java.util.Date;
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
import javax.ws.rs.core.Response.Status;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/microblog")
public class MicroBlogRS {
	private MicroBlogRepository microBlogRepository = new MicroBlogRepository();

    @Path("/post")
    @POST
    public void post(@FormParam("texto") String text) {
        MicroPost micropost = new MicroPost(text, obtemUsuarioLogado());
        MicroBlogRepository repository = getMicroBlogRepository();
        repository.salvar(micropost);
    }

    protected Usuario obtemUsuarioLogado() {
        return new UsuarioRepository().obtemUsuarioLogado();
    }

    protected MicroBlogRepository getMicroBlogRepository() {
        return new MicroBlogRepository();
    }

    @Path("/post")
    @GET
    public Response get() {
        MicroBlogRepository repository = getMicroBlogRepository();
        JsonArray microPosts = new JsonArray();
        for (MicroPost microPost : repository.buscarMicroPosts()) {
            JsonObject microPostJson = new JsonObject();
            microPostJson.addProperty("texto", microPost.getTexto());
            microPostJson.addProperty("autor", microPost.getAutor().getUsername());
            microPosts.add(microPostJson);
        }
        return Response.ok(microPosts.toString()).build();
    }

    @Path("/post")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar(@QueryParam("r") @DefaultValue(Application.REGISTROS_POR_PAGINA_MICROPOSTS) Integer registrosPorPagina, @QueryParam("p") @DefaultValue("1") Integer pagina) {
		List<MicroPost> microPosts = this.listarMicroPostsOrdenados(registrosPorPagina, pagina);
		return Response.ok().entity(microPosts).build();
	}


	protected List<MicroPost> listarMicroPostsOrdenados(Integer registrosPorPagina, Integer pagina) {
		EntidadeOrdenacao dataDeAtualizacaoDecrescente = new EntidadeOrdenacao(MicroBlogFields.DATA.getField(), SortDirection.DESCENDING);

		List<MicroPost> microPosts = microBlogRepository.lista(registrosPorPagina, pagina, dataDeAtualizacaoDecrescente);
		return microPosts;
	}

	@Path("/count/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response verificaNovosMicroPosts(@QueryParam("d") Date data) {
		MicroBlogRepository microBlogRepository = new MicroBlogRepository();
		int total = microBlogRepository.verificaNovos(data, MicroPost.class.getName(), MicroBlogFields.DATA.getField());

		return Response.ok().entity(total).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response deletar(@PathParam("id") String id) throws EntityNotFoundException {
		MicroPost microPost = microBlogRepository.obtemPorId(id);
		if (microPost.getAutor().equals(obtemUsuarioLogado())) {
			microBlogRepository.remove(id);
			return Response.ok().build();
		} else {
			return Response.status(Status.FORBIDDEN).build();
		}

	}

}
