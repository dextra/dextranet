package br.com.dextra.dextranet.web.conteudo.busca;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.TesteFuncionalBase;
import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.web.conteudo.post.PaginaPost;
import br.com.dextra.dextranet.web.conteudo.post.comentario.PaginaComentario;

public class BuscaGeralWebTest extends TesteFuncionalBase {
	private static final String CONTEUDO_COMENTARIO = "Texto do comentário.";
	private static final String CONTEUDO_POST = "Conteúdo do post";
	private static final String TITULO_POST = "Título do post";
	private static final int QTD_POSTS = 3;
	private PaginaPost paginaNovoPost;
	private PaginaComentario paginaNovoComentario;
	private PaginaBuscaGeral buscaGeralPost;

	private PostRepository repositorioDePosts = new PostRepository();
	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	public BuscaGeralWebTest() {
		buscaGeralPost = new PaginaBuscaGeral(driver);
		paginaNovoPost = new PaginaPost(driver);
		paginaNovoComentario = new PaginaComentario(driver);
	}

	@Before
	public void carregaDados() {
		this.criaPostsComComentarios();
	}

	@After
	public void removeDados() {
		this.limpaPostsInseridos(repositorioDePosts);
		this.limpaComentariosInseridos(repositorioDeComentarios);
	}

	@Test
	public void testaBuscaGeral() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		quandoEleBuscaPeloTituloDeUmPostExistente();
		entaoEleEncontraPostDesejado();

		quandoEleBuscaPeloComentarioDeUmPost();
		entaoEleEncontraPostComComentarioDesejado();
	}

	private void quandoEleBuscaPeloComentarioDeUmPost() {
		buscaGeralPost.redigeConteudoDaBusca(CONTEUDO_COMENTARIO);
		buscaGeralPost.clicaNoBotaoPesquisa();
	}

	private void entaoEleEncontraPostDesejado() {
		String conteudo = CONTEUDO_POST + " 0";
		String titulo = TITULO_POST + " 0";
		Assert.assertTrue(paginaNovoPost.existePostPor(titulo, conteudo));
	}

	private void entaoEleEncontraPostComComentarioDesejado() {
		paginaNovoComentario.clicaEmMostrarComentarioPrimeiroPost();
		Assert.assertTrue(paginaNovoComentario.existeComentarioPor(CONTEUDO_COMENTARIO));
	}

	private void quandoEleBuscaPeloTituloDeUmPostExistente() {
		buscaGeralPost.redigeConteudoDaBusca("titulo:" + TITULO_POST + " 0");
		buscaGeralPost.clicaNoBotaoPesquisa();
	}

	private void criaPostsComComentarios() {
		for (int cont = 0; cont <= QTD_POSTS; cont++) {
			String tituloPost = TITULO_POST + " " + cont;
			String conteudo = CONTEUDO_POST + " " + cont;

			Post novoPost = new Post("usuario", tituloPost, conteudo);
			repositorioDePosts.persiste(novoPost);

			this.criaComentarioParaPost(novoPost);
		}
	}

	private void criaComentarioParaPost(Post novoPost) {
		Comentario novoComentario = novoPost.comentar("dextranet", CONTEUDO_COMENTARIO);
		repositorioDeComentarios.persiste(novoComentario);
	}

	private void dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acessaPaginaPrincipal();
	}
}
