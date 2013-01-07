package br.com.dextra.teste.utils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.utils.Converters;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class EntityJsonConverterTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void entityJsonConverterTest() throws EntityNotFoundException{
		Entity entity = dadoUmPost();
		String json = quandoEuConvertoPraUmJson(entity);
		entaoDeemUmNomeMelhor(json);
	}

	private void entaoDeemUmNomeMelhor(String json) {
		Assert.assertTrue(json.contains("valorDeTeste"));
		Assert.assertTrue(json.contains("Gabriel"));

	}

	private String quandoEuConvertoPraUmJson(Entity entity) {
		return Converters.toJson(entity).toString();
	}

	private Entity dadoUmPost() throws EntityNotFoundException {

		Post post=new Post("Post de Teste", "Conteudo de Teste", "User de Teste");
		return post.toEntity();
	}
}
