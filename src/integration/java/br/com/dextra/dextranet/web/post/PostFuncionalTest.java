package br.com.dextra.dextranet.web.post;

import org.junit.Test;

import br.com.dextra.teste.TesteFuncionalBase;

public class PostFuncionalTest extends TesteFuncionalBase {

	private PaginaNovoPost paginaNovoPost = null;

	public void criarNovoPost() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		
		String titulo = "Titulo de Teste";
		String conteudo = "Texto do teste";
		eCriouUmPost(titulo, conteudo);
		entaoUsuarioVisualizaOPost(titulo, conteudo);
	}
	
	protected void eCriouUmPost(String titulo, String conteudo) {
		paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
		paginaNovoPost.criarNovoPost(titulo, conteudo);
	}


	private void entaoUsuarioVisualizaOPost(String titulo, String conteudo) {
		paginaPrincipal.existePost(titulo, conteudo);
	}


}
