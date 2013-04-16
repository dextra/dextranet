package br.com.dextra.dextranet.unidade;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class UnidadeRepositoryTest extends TesteIntegracaoBase {

	private UnidadeRepository repositorio = new UnidadeRepository();

	@Test
	public void testeConstrutor() {
		Entity unidadeEntity = new Unidade("campinas").toEntity();
		Unidade unidade = new Unidade(unidadeEntity);

		Assert.assertEquals(unidadeEntity.getProperty(UnidadeFields.id.toString()), unidade.getId());
		Assert.assertEquals(unidadeEntity.getProperty(UnidadeFields.nome.toString()), unidade.getNome());
	}

	@Test
	public void testeToEntity() {
		Unidade unidade = new Unidade("campinas");
		Entity unidadeEntity = unidade.toEntity();

		Assert.assertEquals(unidade.getId(), unidadeEntity.getProperty(UnidadeFields.id.toString()));
		Assert.assertEquals(unidade.getNome(), unidadeEntity.getProperty(UnidadeFields.nome.toString()));
	}

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
