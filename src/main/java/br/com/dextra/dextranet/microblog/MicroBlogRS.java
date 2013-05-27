package br.com.dextra.dextranet.microblog;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.rest.config.Application;

import com.google.appengine.api.datastore.Query.SortDirection;

@Path("/microblog")
public class MicroBlogRS {

	@Path("/post")
	@POST
	public void post(@FormParam("texto") String texto) {
		MicroPost micropost = new MicroPost(texto);
		MicroBlogRepository repository = getMicroBlogRepository();
		repository.salvar(micropost);
	}

	protected MicroBlogRepository getMicroBlogRepository() {
		return new MicroBlogRepository();
	}

	@Path("/post")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response get() {
		MicroBlogRepository repository = getMicroBlogRepository();
		
		EntidadeOrdenacao dataDeAtualizacaoDecrescente = new EntidadeOrdenacao(MicroBlogFields.DATA.getField(), SortDirection.DESCENDING);
		List<MicroPost> microPosts = repository.lista(dataDeAtualizacaoDecrescente);
		return Response.ok().entity(microPosts).build();
	}
}
