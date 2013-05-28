package br.com.dextra.dextranet.web.microblog;

import junit.framework.Assert;

import org.junit.Test;

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

	//@Test
	public void testaMicroBlog() {
		dadoQueUsuarioAcessaPaginaPrincipal();
		eleCriaMicroPosts();
		entaoChecaSeMicroPostFoiCriado();
	}

	private void eleCriaMicroPosts() {
		for (int cont = 0; cont <= QTD_MICRO_POSTS; cont++) {
			String substituto = cont + "";
			microBlog.redigeMensagem(mensagemMicroPost(substituto));
			microBlog.clicaBotaoEnviar();
		}
	}

	private String mensagemMicroPost(String substituto) {
		return MENSAGEM_MICRO_POST.replace("[NUM]", substituto);
	}

	private void entaoChecaSeMicroPostFoiCriado() {
		Boolean microPostExistente = microBlog.microPostExistente(mensagemMicroPost("0"));
		Assert.assertTrue(microPostExistente);
	}

	private void dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acessaPaginaPrincipal();
	}
}
