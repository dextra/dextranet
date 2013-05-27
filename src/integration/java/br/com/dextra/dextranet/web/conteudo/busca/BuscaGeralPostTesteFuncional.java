package br.com.dextra.dextranet.web.conteudo.busca;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.web.conteudo.post.PaginaNovoPost;
import br.com.dextra.dextranet.web.conteudo.post.comentario.PaginaNovoComentario;

public class BuscaGeralPostTesteFuncional  extends TesteFuncionalBase {
	private PaginaNovoPost paginaNovoPost = null;
	private PaginaNovoComentario paginaNovoComentario;
	private BuscaGeralPost buscaGeralPost;

	@After
	public void criaPostsEComentarios() {
		paginaPrincipal.dadoQueUsuarioAcessaPaginaPrincipal();
		paginaNovoComentario = new PaginaNovoComentario(driver);
		eleCriaPostsEComentario();
	}

	@Test
	public void testaBuscaGeral() {
		eleFazBuscaPosts();
		eleChecaSePostsEncontrados();
		eleFazBuscaPostPorComentarios();
		eleChecaSePostsEncontrados();
	}

	private void eleFazBuscaPostPorComentarios() {
		// TODO Auto-generated method stub

	}

	private void eleChecaSePostsEncontrados() {

	}

	private void eleFazBuscaPosts() {
		// TODO Auto-generated method stub

	}

	private void eleCriaPostsEComentario() {
		for (int cont = 0; cont < 2; cont++) {
			String tituloPost = "Titulo de Teste " + cont;
			String conteudo = "Texto do teste " + cont;

			paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
			paginaNovoPost.criarNovoPost(tituloPost, conteudo);

			criaComentarioParaPost(tituloPost);
		}
	}

	private void criaComentarioParaPost(String tituloPost) {
		paginaNovoComentario.clicaEmNovoComentario();
		String conteudo = "Texto do comentário.";
		paginaNovoComentario.criaNovoComentario(conteudo);
	}
}
