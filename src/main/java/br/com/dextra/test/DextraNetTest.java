package br.com.dextra.test;

import junit.framework.Assert;

import org.junit.Test;
import br.com.dextra.teste.TesteFuncionalBase;

public class DextraNetTest extends TesteFuncionalBase
{
	PaginaDextraNet siteDextraNet = new PaginaDextraNet(driver);

	@Test
	public void testaBuscaDextraNet()
	{
		siteDextraNet.navegarNaPagina("http://dextra-estagio-2013.appspot.com/");
		verificaSeTemLogo();

	}

	private void verificaSeTemLogo()
	{
		Assert.assertTrue(siteDextraNet.obtemElemento("span.icon-header-logo") != null);
	}
}
