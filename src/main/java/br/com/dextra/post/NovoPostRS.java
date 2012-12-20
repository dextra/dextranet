package br.com.dextra.post;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// TODO: Pessoal, sera que nao valeria a pena melhorarmos o nome desta classe. Vejam que ela chama
// NovoPostRS mas ela faz mais coisas do que simplesmente um novo POST.

@Path("/post")
public class NovoPostRS {

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

	@Path("/lista")
	@GET
	@Produces("application/json;charset=UTF-8")
	public void listaPosts() {

	}
}
