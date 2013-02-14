package br.com.dextra.dextranet.area;

import junit.framework.Assert;

import org.junit.Test;

public class AreaTest {

	@Test
	public void testaArea() {
		Area area = new Area("Desenvolvimento");
		Area outra = new Area("Recursos Humanos");

		Assert.assertFalse(area.getId().equals(outra.getId()));
	}
}