// TODO: Como conversamos hoje, as classes de servicoes web deverao ficar na mesma package dos
// outros objetos relacionados a um mesmo contexto. Sendo assim, esse cara aqui precisa ser
// migrado para outra package.
package br.com.dextra.restAPI;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.dextra.repository.PostRepository;
import br.com.dextra.utils.EntityJsonConverter;

import com.google.appengine.api.datastore.Entity;

// TODO: Conforme combinamos hoje, o padrao para classes de servico web serao RS. Precisamos corrigir
// isso.
@Path("/posts")
public class PostResource {



	@GET
	@Produces("application/json;charset=UTF-8")
	public static String listarPosts(
			@DefaultValue("20") @QueryParam(value = "max-results") String maxResults,
			@DefaultValue("") @QueryParam(value = "q") String q) {
		if(q.equals(""))
		{
		Iterable<Entity> listaPosts = PostRepository.buscarTodosOsPosts(Integer.parseInt(maxResults));
		return EntityJsonConverter.converterListaEntities(listaPosts).toString();
		}
		else
		{
			return PostRepository.buscarPosts(Integer.parseInt(maxResults), q).toString();
		}
	}

}
