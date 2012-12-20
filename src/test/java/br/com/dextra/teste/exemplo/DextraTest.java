package br.com.dextra.teste.exemplo;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.post.Post;
import br.com.dextra.restAPI.PostResource;
import br.com.dextra.teste.TesteFuncionalBase;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class DextraTest extends TesteFuncionalBase {

	/*
	 * @Test public void testaBuscaDextraSistemas() {
	 * siteDextra.navegarNaPagina("http://www.dextra.com.br");
	 * siteDextra.preencheInputText("input#gsc-i-id1", "java");
	 * siteDextra.clica("input.gsc-search-button");
	 * siteDextra.confereResultadoDaBusca(0); }
	 */

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

	// run this test twice to prove we're not leaking any state across tests
	private void doTest() {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		ds.put(new Entity("yam"));
		ds.put(new Entity("yam"));
	}

	@Test
	public void testInsert1() {
		doTest();
	}

	@Test
	public void testInsert2() {
		doTest();
	}

	private void meDeUmPost(String titulo, String conteudo, String usuario,
			Date data, long time) {

		Key key = KeyFactory.createKey("post", time);

		Entity valueEntity = new Entity(key);

		valueEntity.setProperty("titulo", titulo);
		valueEntity.setProperty("conteudo", conteudo);
		valueEntity.setProperty("usuario", usuario);
		valueEntity.setProperty("data", data);

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		datastore.put(valueEntity);
	}

	@Test
	public void testeListarPosts1() {
		String resultadoEsperado = "[]";
		Assert.assertEquals(resultadoEsperado, PostResource.listarPosts("20",
				"", ""));
	}

	@Test
	public void testeListarPosts2() {
		Date data = new Date();
		long time = new Date().getTime();
		meDeUmPost("1 post", "bla", "user", data, time);

		String json = null;
		json = "[{\"data\":\""
				+ data
				+ "\",\"titulo\":\"1 post\",\"usuario\":\"user\",\"conteudo\":\"bla\",\"key\":\"post("
				+ time + ")\",\"dataDeAtualizacao\":\""
				+ data
				+ "\"}]";

		Assert.assertEquals(json, PostResource.listarPosts("20", "", ""));
	}

	@Test
	public void testeListarPosts3() {
		Date data2 = new Date();
		long time2 = new Date().getTime();
		 meDeUmPost("2 post","bla2","user2" , data2,time2);

		Date data3 = new Date();
		long time3 = new Date().getTime();
		 meDeUmPost("3 post","bla3","user3" , data3,time3);

		String json = null;
		json = "[{\"data\":\""
				+ data2
				+ "\",\"titulo\":\"2 post\",\"usuario\":\"user2\",\"conteudo\":\"bla2\",\"key\":\"post("+time2+")\"},"
				+ "{\"data\":\""
				+ data3
				+ "\",\"titulo\":\"3 post\",\"usuario\":\"user3\",\"conteudo\":\"bla3\",\"key\":\"post("+time3+")\",\"dataDeAtualizacao\":\""
				+ data3
				+ "\"}]";

		Assert.assertEquals(json, PostResource.listarPosts("2", "", ""));
	}

	@Test
	public void testeListarPosts4() {
		Date data1 = new Date();
		long time1 = new Date().getTime();
		 meDeUmPost("1 post","bla1","user1" , data1,time1);

		Date data2 = new Date();
		long time2 = new Date().getTime();
		 meDeUmPost("2 post","bla2","user2" , data2,time2);

		Date data3 = new Date();
		long time3 = new Date().getTime();
		 meDeUmPost("3 post","bla3","user3" , data3,time3);

		String json = null;
		json = "[{\"data\":\""
				+ data2
				+ "\",\"titulo\":\"2 post\",\"usuario\":\"user2\",\"conteudo\":\"bla2\",\"key\":\"post("+time2+")\"},"
				+ "{\"data\":\""
				+ data3
				+ "\",\"titulo\":\"3 post\",\"usuario\":\"user3\",\"conteudo\":\"bla3\",\"key\":\"post("+time3+")\"}]";

		Assert.assertEquals(json, PostResource.listarPosts("2", "", "post(\""+time2+"\")"));
	}


	// FIXME: Gabriel/Tonho estão com problemas que serão resolvidos rapida e
	// futuramente!

	// @Test
	// public void testeBuscarPosts1() {
	// Date data2 = new Date();
	// // meDeUmPost("2 post","bla2","user2" , data2);
	//
	// Date data3 = new Date();
	// // meDeUmPost("3 post","bla3","user3" , data3);
	//
	// Post.criaNovoPost("3 post","bla 3","user3");
	// Post.criaNovoPost("2 post","bla 2","user2");
	// String json = null;
	// json =
	// "[{\"data\":\""+data2+"\",\"titulo\":\"2 post\",\"usuario\":\"user2\",\"conteudo\":\"bla2\"},"+
	// "{\"data\":\""+data3+"\",\"titulo\":\"3 post\",\"usuario\":\"user3\",\"conteudo\":\"bla3\"}]";
	//
	// Assert.assertEquals(json, PostResource.listarPosts("2","bla"));
	// }

}
