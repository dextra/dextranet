package br.com.dextra.dextranet.area;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class AreaRepositoryTest extends TesteIntegracaoBase {

	private AreaRepository repositorio = new AreaRepository();

	@Test
	public void testeConstrutor() {
		Entity areaEntity = new Area("desenvolvimento").toEntity();
		Area area = new Area(areaEntity);

		Assert.assertEquals(areaEntity.getProperty(AreaFields.id.toString()), area.getId());
		Assert.assertEquals(areaEntity.getProperty(AreaFields.nome.toString()), area.getNome());
	}

	@Test
	public void testeToEntity() {
		Area area = new Area("desenvolvimento");
		Entity areaEntity = area.toEntity();

		Assert.assertEquals(area.getId(), areaEntity.getProperty(AreaFields.id.toString()));
		Assert.assertEquals(area.getNome(), areaEntity.getProperty(AreaFields.nome.toString()));
	}

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