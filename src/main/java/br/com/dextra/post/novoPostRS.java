package br.com.dextra.post;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/post")
public class novoPostRS {

	@Path("/novo")
	@GET
	@Produces("application/json;charset=UTF-8")
	public void novoPost(@QueryParam(value = "txtTitulo") String titulo,
			@QueryParam(value = "txtConteudo") String conteudo) {
		String usuario = "usuarioTeste";

		Post novoPost = new Post();
		novoPost.criaNovoPost(titulo, conteudo, usuario);
		System.out.println("Inserido? "+novoPost.pegaDadosCorretos(titulo, conteudo, usuario));
	}

	@Path("/lista")
	@GET
	@Produces("application/json;charset=UTF-8")
	public void listaPosts() {

	}
}
