package br.com.dextra.dextranet.web.microblog;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.TesteFuncionalBase;

public class MicroBlogWebTest extends TesteFuncionalBase {
	private static final int QTD_MICRO_POSTS = 2;
	private static String MENSAGEM_MICRO_POST = "Texto do micropost [NUM]: A Dextra Sistemas foi fundada " +
										"em 1995 por 3 ex-alunos da Unicamp. O nome Dextra vem " +
										"do latim, que significa (direita).";

	PaginaMicroBlog microBlog;

	public MicroBlogWebTest() {
		microBlog = new PaginaMicroBlog(driver);
	}

	@Test
	public void testaMicroBlog() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		quandoEleCriaMicroPosts();
		entaoUmNovoMicroPostApareceNoMicroBlog();

		quandoEleExcluiMicroPost();
		entaoMicroPostNaoExisteMaisNoMicroBlog();
	}

	private void quandoEleCriaMicroPosts() {
		for (int cont = 0; cont <= QTD_MICRO_POSTS; cont++) {
			String substituto = cont + "";
			microBlog.redigeMensagem(mensagemMicroPost(substituto));
			microBlog.clicaBotaoEnviar();
			paginaPrincipal.waitToLoad();
		}
	}

	private String mensagemMicroPost(String substituto) {
		return MENSAGEM_MICRO_POST.replace("[NUM]", substituto);
	}

	private void entaoUmNovoMicroPostApareceNoMicroBlog() {
		Boolean microPostExistente = microBlog.microPostExistente(mensagemMicroPost(Integer.toString(QTD_MICRO_POSTS)));
		Assert.assertTrue(microPostExistente);
	}

	private void dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acessaPaginaPrincipal();
	}

	private void quandoEleExcluiMicroPost() {
		microBlog.excluiMicroPost();
		paginaPrincipal.waitToLoad();
	}

	private void entaoMicroPostNaoExisteMaisNoMicroBlog() {
		paginaPrincipal.waitToLoad();
		Boolean microPostExistente = microBlog.microPostExistente(mensagemMicroPost(Integer.toString(QTD_MICRO_POSTS)));
		Assert.assertTrue(!microPostExistente);
	}
}
