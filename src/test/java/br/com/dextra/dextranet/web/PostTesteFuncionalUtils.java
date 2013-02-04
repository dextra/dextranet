package br.com.dextra.dextranet.web;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.web.post.PaginaNovoPost;
import br.com.dextra.teste.TesteFuncionalBase;

public class PostTesteFuncionalUtils extends TesteFuncionalBase {

	protected List<String> postsInseridos = new ArrayList<String>();

	protected void eCriouPosts(int quantidadeDePosts) {
		for (int i = 1; i <= quantidadeDePosts; i++){
			String titulo = "Titulo de Teste Numero: " + i;
			String conteudo = "Texto do teste numero: " + i;

			PaginaNovoPost paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
			paginaNovoPost.criaNovoPost(titulo, conteudo);

			// armazena o titulo do post criado para futura comparacao
			postsInseridos.add(titulo.toUpperCase());
		}
	}
	
}
