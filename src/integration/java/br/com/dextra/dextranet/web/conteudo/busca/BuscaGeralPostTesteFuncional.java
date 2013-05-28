package br.com.dextra.dextranet.web.conteudo.busca;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.web.conteudo.post.PaginaNovoPost;
import br.com.dextra.dextranet.web.conteudo.post.comentario.PaginaNovoComentario;

public class BuscaGeralPostTesteFuncional  extends TesteFuncionalBase {
	private static final String CONTEUDO_COMENTARIO = "Texto do comentário.";
	private static final String CONTEUDO_POST = "Conteudo do post";
	private static final String TITULO_POST = "Titulo do post";
	private static final int QTD_POSTS = 1;//TODO: aumentar para 2
	private PaginaNovoPost paginaNovoPost;
	private PaginaNovoComentario paginaNovoComentario;
	private BuscaGeralPost buscaGeralPost;

	public BuscaGeralPostTesteFuncional() {
		buscaGeralPost =  new BuscaGeralPost(driver);
		paginaNovoPost = new PaginaNovoPost(driver);
		paginaNovoComentario = new PaginaNovoComentario(driver);
	}

	@Test
	public void testaBuscaGeralPorPost() {
		paginaPrincipal.acessaPaginaPrincipal();
//		eleCriaPostsEComentario();
		eleFazBuscaPosts();
		entaoChecaSePostEncontrado();
		eleBuscaPostPorComentarios();
		entaoChecaSePostFoiEncontradoPorComentario();
	}

	private void eleBuscaPostPorComentarios() {
		buscaGeralPost.redigeConteudoDaBusca("conteudo:" + CONTEUDO_COMENTARIO);
		buscaGeralPost.clicaNoBotaoPesquisa();
	}

	private void entaoChecaSePostEncontrado() {
		String conteudo = CONTEUDO_POST + " 0";
		String titulo = TITULO_POST + " 0";
		Assert.assertTrue(paginaNovoPost.existePostPor(titulo, conteudo));
	}

	private void entaoChecaSePostFoiEncontradoPorComentario() {
		paginaNovoComentario.clicaEmMostrarComentarioPrimeiroPost();
		Assert.assertTrue(paginaNovoComentario.existeComentarioPor(CONTEUDO_COMENTARIO));
	}

	private void eleFazBuscaPosts() {
		buscaGeralPost.redigeConteudoDaBusca("titulo:" + TITULO_POST + " 0");
		buscaGeralPost.clicaNoBotaoPesquisa();
	}

	private void eleCriaPostsEComentario() {
		for (int cont = 0; cont <= QTD_POSTS; cont++) {
			String tituloPost = TITULO_POST + " " + cont;
			String conteudo = CONTEUDO_POST + " " + cont;

			paginaNovoPost = paginaPrincipal.clicaEmNovoPost();
			paginaNovoPost.criarNovoPost(tituloPost, conteudo);
			paginaNovoPost.existePostPor(tituloPost, conteudo);
			criaComentarioParaPost(tituloPost, paginaNovoPost.getIdPost());
		}
	}

	private void criaComentarioParaPost(String tituloPost, String idPost) {
		PaginaNovoComentario paginaNovoComentario = new PaginaNovoComentario(driver);
		paginaNovoComentario.setIdPost(idPost);

		String conteudo = CONTEUDO_COMENTARIO;
		paginaNovoComentario.criaNovoComentario(conteudo);
	}
}
