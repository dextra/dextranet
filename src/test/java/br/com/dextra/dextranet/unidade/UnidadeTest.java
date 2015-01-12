package br.com.dextra.dextranet.unidade;

import org.junit.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

//a heranca eh para poder testar os objetos envolvendo entity
public class UnidadeTest extends TesteIntegracaoBase {

	@Test
	public void testeConstrutor() {
		Entity unidadeEntity = new Unidade("campinas").toEntity();
		Unidade unidade = new Unidade(unidadeEntity);

		Assert.assertEquals(unidadeEntity.getProperty(UnidadeFields.id.name()), unidade.getId());
		Assert.assertEquals(unidadeEntity.getProperty(UnidadeFields.nome.name()), unidade.getNome());
	}

	@Test
	public void testeToEntity() {
		Unidade unidade = new Unidade("campinas");
		Entity unidadeEntity = unidade.toEntity();

		Assert.assertEquals(unidade.getId(), unidadeEntity.getProperty(UnidadeFields.id.name()));
		Assert.assertEquals(unidade.getNome(), unidadeEntity.getProperty(UnidadeFields.nome.name()));
	}

}
