package br.com.dextra.dextranet.area;

import junit.framework.Assert;
import org.junit.Test;
import br.com.dextra.teste.TesteIntegracaoBase;


public class AreaRepositoryTest extends TesteIntegracaoBase {

	private AreaRepository ar = new AreaRepository();

	@Test
	public void testaInsercao() {
		Area nova = new Area("Desenvolvimento");
		ar.inserir(nova);
		Area buscada = ar.buscar(nova.getId());
		Assert.assertEquals(buscada.getName(), nova.getName());
	}
}