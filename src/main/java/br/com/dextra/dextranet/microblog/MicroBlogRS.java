package br.com.dextra.dextranet.microblog;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Path("/microblog")
public class MicroBlogRS {
	private MicroBlogRepository microBlogRepository = new MicroBlogRepository();

    @Path("/post")
    @POST
    public void post(@FormParam("text") String text) {
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
            microPostJson.addProperty("text", microPost.getTexto());
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

}
