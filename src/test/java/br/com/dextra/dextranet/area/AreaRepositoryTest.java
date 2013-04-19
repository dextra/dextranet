package br.com.dextra.dextranet.area;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class AreaRepositoryTest extends TesteIntegracaoBase {

	private AreaRepository repositorio = new AreaRepository();

	@Test
	public void testaRemocao() {
		Area novaArea = new Area("nova area");
		Area areaCriada = repositorio.persiste(novaArea);

		String idDaAreaCriada = areaCriada.getId();
		repositorio.remove(idDaAreaCriada);

		try {
			repositorio.obtemPorId(idDaAreaCriada);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaListaTodos() {
		Area desenvolvimento = new Area("desenvolvimento");
		Area treinamento = new Area("treinamento");
		Area comercial = new Area("comercial");

		repositorio.persiste(desenvolvimento);
		repositorio.persiste(treinamento);
		repositorio.persiste(comercial);

		List<Area> areasEncontradas = repositorio.lista();

		// verifica se encontrou todas
		Assert.assertEquals(3, areasEncontradas.size());
		// verifica se trouxe de forma ordenada
		Assert.assertEquals(desenvolvimento, areasEncontradas.get(1));
	}

}