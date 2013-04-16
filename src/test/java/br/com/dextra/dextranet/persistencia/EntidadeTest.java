package br.com.dextra.dextranet.persistencia;

import junit.framework.Assert;

import org.junit.Test;

public class EntidadeTest {

	@Test
	public void testeEntidade() {
		EntidadeFake entidadeA = new EntidadeFake();
		EntidadeFake entidadeB = new EntidadeFake();

		Assert.assertFalse(entidadeA.getId().equals(entidadeB.getId()));
		Assert.assertFalse(entidadeA.hashCode() == entidadeB.hashCode());
		Assert.assertFalse(entidadeA.equals(entidadeB));
	}

}
