package br.com.dextra.teste.exemplo;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.post.PostRS;
import br.com.dextra.repository.PostRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.JsonObject;

public class DextraTest extends TesteIntegracaoBase {

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
	/*
	 * private void doTest() { DatastoreService ds =
	 * DatastoreServiceFactory.getDatastoreService();
	 *
	 * ds.put(new Entity("yam")); ds.put(new Entity("yam")); }
	 *
	 * @Test public void testInsert1() { doTest(); }
	 *
	 * @Test public void testInsert2() { doTest(); }
	 */

	@Test
	public void testeListarPosts1() {
		String resultadoEsperado = "[]";
		Assert
				.assertEquals(resultadoEsperado, PostRS.listarPosts("20", "",
						""));
	}

	@Test
	public void testeListarPosts2() {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Date data = new Date();
		long time = new Date().getTime();
		String id = String.valueOf(time);
		Key key = KeyFactory.createKey("post", id);

		PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);
		StringBuilder comparacao = new StringBuilder();
		comparacao.append("["
				+ criaUmJson(titulo, conteudo, usuario, id, key, data) + "]");

		Assert.assertEquals(comparacao.toString(), PostRS.listarPosts("20", "",
				""));
	}

	private String criaUmJson(String titulo, String conteudo, String usuario,
			String id, Key key, Date data) {

		JsonObject json = new JsonObject();

		json.addProperty("titulo", titulo);
		json.addProperty("usuario", usuario);
		json.addProperty("comentarios", "0");
		json.addProperty("dataDeAtualizacao", data.toString());
		json.addProperty("conteudo", conteudo);
		json.addProperty("likes", "0");
		json.addProperty("data", data.toString());
		json.addProperty("key", key.toString());

		return json.toString();
	}

	@Test
	public void testeListarPosts3() {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Date data = new Date();
		long time = new Date().getTime();
		String id = String.valueOf(time);
		Key key = KeyFactory.createKey("post", id);

		PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);

		String titulo2 = "Post2";
		String conteudo2 = "Content2";
		String usuario2 = "User2";

		Date data2 = new Date();
		long time2 = new Date().getTime();
		String id2 = String.valueOf(time2);
		Key key2 = KeyFactory.createKey("post", id2);

		PostRepository.criaNovoPost(titulo2, conteudo2, usuario2, id2, key2,
				data2);

		String titulo3 = "Post3";
		String conteudo3 = "Content3";
		String usuario3 = "User3";

		Date data3 = new Date();
		long time3 = new Date().getTime();
		String id3 = String.valueOf(time3);
		Key key3 = KeyFactory.createKey("post", id3);

		PostRepository.criaNovoPost(titulo3, conteudo3, usuario3, id3, key3,
				data3);

		StringBuilder comparacao = new StringBuilder();
		comparacao.append("["
				+ criaUmJson(titulo3, conteudo3, usuario3, id3, key3, data3)
				+ ",");
		comparacao.append(criaUmJson(titulo2, conteudo2, usuario2, id2, key2,
				data2)
				+ "]");

		Assert.assertEquals(comparacao.toString(), PostRS.listarPosts("2", "",
				""));

	}

	// FIXME:Gabriel/Tonho encontraram a solução para o FTS e voltarão a esse
	// test mais tarde

/*
	 * @Test public void testeListarPosts4() { Date data1 = new Date(); String
	 * titulo = "Post1"; String conteudo = "Content1"; String usuario = "User1";
	 *
	 * Date data = new Date(); long time = new Date().getTime(); String id =
	 * String.valueOf(time); Key key = KeyFactory.createKey("post", id);
	 *
	 * Post.criaNovoPost(titulo, conteudo, usuario, id, key, data);
	 *
	 * String titulo2 = "Post2"; String conteudo2 = "Content2"; String usuario2
	 * = "User2";
	 *
	 * Date data2 = new Date(); long time2 = new Date().getTime(); String id2 =
	 * String.valueOf(time2); Key key2 = KeyFactory.createKey("post", id2);
	 *
	 * Post.criaNovoPost(titulo2, conteudo2, usuario2, id2, key2, data2);
	 *
	 * String titulo3 = "Post3"; String conteudo3 = "Content3"; String usuario3
	 * = "User3";
	 *
	 * Date data3 = new Date(); long time3 = new Date().getTime(); String id3 =
	 * String.valueOf(time3); Key key3 = KeyFactory.createKey("post", id3);
	 *
	 * Post.criaNovoPost(titulo3, conteudo3, usuario3, id3, key3, data3);
	 *
	 * StringBuilder comparacao = new StringBuilder(); comparacao.append("[" +
	 * criaUmJson(titulo, conteudo, usuario, id, key, data) +"]");
	 *
	 * Assert.assertEquals(comparacao.toString(), PostRS.listarPosts("2", "",
	 * "post(\"" + id + "\")")); }
	 */

	// FIXME: Gabriel/Tonho estão com problemas que serão resolvidos rapida e
	// futuramente!

/*	@Test
	public void testeBuscarPosts1() {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Date data = new Date();
		long time = new Date().getTime();
		String id = String.valueOf(time);
		Key key = KeyFactory.createKey("post", id);

		PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);

		String titulo2 = "Post2";
		String conteudo2 = "bla";
		String usuario2 = "User2";

		Date data2 = new Date();
		long time2 = new Date().getTime();
		String id2 = String.valueOf(time2);
		Key key2 = KeyFactory.createKey("post", id2);

		PostRepository.criaNovoPost(titulo2, conteudo2, usuario2, id2, key2, data2);

		String titulo3 = "Post3";
		String conteudo3 = "Content3";
		String usuario3 = "User3";

		Date data3 = new Date();
		long time3 = new Date().getTime();
		String id3 = String.valueOf(time3);
		Key key3 = KeyFactory.createKey("post", id3);

		PostRepository.criaNovoPost(titulo3, conteudo3, usuario3, id3, key3, data3);

		StringBuilder comparacao = new StringBuilder();

		comparacao.append("["+criaUmJson(titulo2, conteudo2, usuario2, id2, key2,
				data2)
				+ "]");

		Assert.assertEquals(comparacao.toString(), PostRS.listarPosts("20", "bla",""));
	}*/

}
