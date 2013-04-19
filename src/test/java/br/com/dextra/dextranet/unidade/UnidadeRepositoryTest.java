package br.com.dextra.dextranet.unidade;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class UnidadeRepositoryTest extends TesteIntegracaoBase {

	private UnidadeRepository repositorio = new UnidadeRepository();

	@Test
	public void testaRemocao() {
		Unidade novaUnidade = new Unidade("campinas");
		Unidade unidadeCriada = repositorio.persiste(novaUnidade);

		String idDaUnidadeCriada = unidadeCriada.getId();
		repositorio.remove(idDaUnidadeCriada);

		try {
			repositorio.obtemPorId(idDaUnidadeCriada);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaListaTodos() {
		Unidade saoPaulo = new Unidade("sao paulo");
		Unidade campoGrande = new Unidade("campo grande");
		Unidade campinas = new Unidade("campinas");

		repositorio.persiste(saoPaulo);
		repositorio.persiste(campinas);
		repositorio.persiste(campoGrande);

		List<Unidade> unidadesEncontradas = repositorio.lista();

		// verifica se encontrou todas
		Assert.assertEquals(3, unidadesEncontradas.size());
		// verifica se trouxe de forma ordenada
		Assert.assertEquals(campoGrande, unidadesEncontradas.get(1));
	}

}
