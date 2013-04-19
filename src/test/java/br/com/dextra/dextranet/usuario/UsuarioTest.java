package br.com.dextra.dextranet.usuario;

import junit.framework.Assert;

import org.junit.Test;

public class UsuarioTest {

	@Test
	public void testaCriacaoHash() {
		Usuario novoUsuario = new Usuario("dextranet");
		Assert.assertEquals("dextranet@dextra-sw.com", novoUsuario.getEmail());
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoUsuario.getMD5());	
	}

}
