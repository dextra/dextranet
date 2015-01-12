package br.com.dextra.dextranet.area;

import org.junit.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

//a heranca eh para poder testar os objetos envolvendo entity
public class AreaTest extends TesteIntegracaoBase {

	@Test
	public void testeConstrutor() {
		Entity areaEntity = new Area("desenvolvimento").toEntity();
		Area area = new Area(areaEntity);

		Assert.assertEquals(areaEntity.getProperty(AreaFields.id.name()), area.getId());
		Assert.assertEquals(areaEntity.getProperty(AreaFields.nome.name()), area.getNome());
	}

	@Test
	public void testeToEntity() {
		Area area = new Area("desenvolvimento");
		Entity areaEntity = area.toEntity();

		Assert.assertEquals(area.getId(), areaEntity.getProperty(AreaFields.id.name()));
		Assert.assertEquals(area.getNome(), areaEntity.getProperty(AreaFields.nome.name()));
	}

}
