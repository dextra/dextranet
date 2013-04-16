package br.com.dextra.dextranet.utils;

import junit.framework.Assert;

import org.junit.Test;

public class ConteudoHTMLTest {

	@Test
	public void removeCodigoIndevidoTest() {
		String conteudo = "<script>alert('Hello!');</script>teste";
		Assert.assertEquals("teste", new ConteudoHTML(conteudo).removeJavaScript());
	}

}
