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




}
