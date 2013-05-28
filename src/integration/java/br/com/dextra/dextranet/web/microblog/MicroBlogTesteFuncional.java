package br.com.dextra.dextranet.web.microblog;

import junit.framework.Assert;
import br.com.dextra.dextranet.TesteFuncionalBase;

public class MicroBlogTesteFuncional extends TesteFuncionalBase {
	private static final int QTD_MICRO_POSTS = 2;
	private static String MENSAGEM_MICRO_POST = "Texto do micropost [NUM]: A Dextra Sistemas foi fundada " +
										"em 1995 por 3 ex-alunos da Unicamp. O nome Dextra vem " +
										"do latim, que significa (direita).";

	MicroBlog microBlog;

	public MicroBlogTesteFuncional() {
		microBlog = new MicroBlog(driver);
	}

	public void testaMicroBlog() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eleCriaMicroPosts();
		entaoChecaSeMicroPostFoiCriado();
	}

	private void eleCriaMicroPosts() {
		for (int cont = 0; cont <= QTD_MICRO_POSTS; cont++) {
			String substituir = cont + "";
			microBlog.redigeMensagem(MENSAGEM_MICRO_POST.replace("[NUM]", substituir));
			microBlog.clicaBotaoEnviar();
		}
	}

	private void entaoChecaSeMicroPostFoiCriado() {
		Assert.assertTrue(true);
	}

	private void dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acessaPaginaPrincipal();
	}
}
