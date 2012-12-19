package br.com.dextra.post;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/post")
public class novoPostRS {

	@Path("/novo")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void novoPost(@FormParam("title") String titulo,
			@FormParam("content") String conteudo) {
		String usuario = "usuarioTeste";

		Post novoPost = new Post();
		novoPost.criaNovoPost(titulo, conteudo, usuario);
		System.out.println("Inserido? "+novoPost.pegaDadosCorretos(titulo, conteudo, usuario));
	}
}
