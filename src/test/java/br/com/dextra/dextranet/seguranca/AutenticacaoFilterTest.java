package br.com.dextra.dextranet.seguranca;

import junit.framework.Assert;

import org.junit.Test;

public class AutenticacaoFilterTest {

	private AutenticacaoFilter filtro = new AutenticacaoFilter();

	@Test
	public void testaFiltroDeUrl() {
		filtro.excludePatterns = "/_ah;/s/migracao";

		Assert.assertTrue(filtro.urlDeveSerIgnorada("/_ah/login"));
		Assert.assertTrue(filtro.urlDeveSerIgnorada("/s/migracao/post"));
		Assert.assertTrue(filtro.urlDeveSerIgnorada("/s/migracao/post/3424294234234/comentario"));
		Assert.assertFalse(filtro.urlDeveSerIgnorada("/index.html"));
		Assert.assertFalse(filtro.urlDeveSerIgnorada("/s/post"));
		Assert.assertFalse(filtro.urlDeveSerIgnorada("/s/banner"));
	}

}
