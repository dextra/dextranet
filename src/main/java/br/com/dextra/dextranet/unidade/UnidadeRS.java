package br.com.dextra.dextranet.unidade;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import br.com.dextra.dextranet.utils.JsonUtil;
import com.google.appengine.api.datastore.EntityNotFoundException;

@Path("/unidade")
public class UnidadeRS {

	private UnidadeRepository repo = new UnidadeRepository();

	@Path("/inserir")
	@POST
	@Produces("application/json;charset=UTF-8")
	public String inserir(@FormParam("name") String name)
			throws EntityNotFoundException {

		Unidade unidade = new Unidade(name);


		repo.inserir(unidade);
		return JsonUtil.stringify(unidade);

	}

	@Path("/obter/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String obter(@PathParam("id") String id) {
		Unidade unidade = repo.buscar(id);
		return JsonUtil.stringify(unidade);
	}

	@Path("/obterTodos")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String getDadosEquipe(@Context HttpServletRequest re) {
		List<Unidade> equipe = repo.buscarTodos();
		return JsonUtil.stringify(equipe);

	}

}
