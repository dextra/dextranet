package br.com.dextra.dextranet.persistencia;

import org.junit.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class EntidadeRepositoryTest extends TesteIntegracaoBase {

	private EntidadeRepository repositorio = new EntidadeRepository();

	@Test
	public void testaRemocao() {
		EntidadeFake novaEntidade = new EntidadeFake();
		Entidade entidadeCriada = repositorio.persiste(novaEntidade);

		String idDaEntidadeCriada = entidadeCriada.getId();
		repositorio.remove(idDaEntidadeCriada, EntidadeFake.class);

		try {
			repositorio.obtemPorId(idDaEntidadeCriada, EntidadeFake.class);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

}
