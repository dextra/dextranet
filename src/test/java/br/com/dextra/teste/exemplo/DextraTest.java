package br.com.dextra.teste.exemplo;

import java.util.ArrayList;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.restAPI.PostResource;
import br.com.dextra.teste.TesteFuncionalBase;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.JsonObject;

public class DextraTest extends TesteFuncionalBase{


/*	@Test
	public void testaBuscaDextraSistemas() {
		siteDextra.navegarNaPagina("http://www.dextra.com.br");
		siteDextra.preencheInputText("input#gsc-i-id1", "java");
		siteDextra.clica("input.gsc-search-button");
		siteDextra.confereResultadoDaBusca(0);
	}*/

	 private final LocalServiceTestHelper helper =
	        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

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

	    @Test
	    public void testeListarPosts1() {
	    	ArrayList<JsonObject> listaVazia=new ArrayList<JsonObject>();
	        Assert.assertEquals(listaVazia, PostResource.listarPosts("",""));
	    }

	    @Test
	    public void testeListarPosts2() {
	    	Key key=KeyFactory.createKey("post", new Date().getTime());

	    	Entity valueEntity=new Entity(key);
	    	valueEntity.setProperty("conteudo","nosso primeiro post!");

	    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	    	ds.put(valueEntity);

	    	JsonObject json = new JsonObject();
	    	json.addProperty("conteudo","nosso primeiro post!");
	    	ArrayList<JsonObject> listaTeste=new ArrayList<JsonObject>();
	    	listaTeste.add(json);
	        Assert.assertEquals(listaTeste, PostResource.listarPosts("",""));
	    }

	    @Test
	    public void testeListarPosts3() {

	    	doInsertPostsTests();

	    	ArrayList<JsonObject> listaTeste=new ArrayList<JsonObject>();

	    	JsonObject json1 = new JsonObject();
	    	json1.addProperty("conteudo","post1!");
	    	listaTeste.add(json1);

	    	JsonObject json2 = new JsonObject();
	    	json2.addProperty("conteudo","post2!");
	    	listaTeste.add(json2);

	        Assert.assertEquals(listaTeste, PostResource.listarPosts("2",""));
	    }

//	    @Test
//	    public void testeListarPosts4() {
//
//	    	doInsertPostsTests();
//
//	    	ArrayList<JsonObject> listaTeste=new ArrayList<JsonObject>();
//
//	    	JsonObject json2 = new JsonObject();
//	    	json2.addProperty("conteudo","post2!");
//	    	listaTeste.add(json2);
//
//	        Assert.assertEquals(listaTeste, PostResource.listarPosts("1",""));
//	    }

	    public void doInsertPostsTests() {
	    	Key key=KeyFactory.createKey("post", 16576454);
	    	Entity valueEntity1=new Entity(key);
	    	valueEntity1.setProperty("conteudo","post1!");

	    	key=KeyFactory.createKey("post", 25468445);
	    	Entity valueEntity2=new Entity(key);
	    	valueEntity2.setProperty("conteudo","post2!");

	    	key=KeyFactory.createKey("post", 384646655);
	    	Entity valueEntity3=new Entity(key);
	    	valueEntity3.setProperty("conteudo","post3!");

	    	key=KeyFactory.createKey("post", 484646655);
	    	Entity valueEntity4=new Entity(key);
	    	valueEntity3.setProperty("conteudo","post4!");

	    	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	    	ds.put(valueEntity1);
	    	ds.put(valueEntity2);
	    	ds.put(valueEntity3);
	    	ds.put(valueEntity4);
	    }



}
