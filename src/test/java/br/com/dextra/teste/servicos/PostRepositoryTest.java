package br.com.dextra.teste.servicos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.post.PostRS;
import br.com.dextra.repository.PostRepository;
import br.com.dextra.teste.TesteIntegracaoBase;
import br.com.dextra.utils.Utils;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.JsonObject;

public class PostRepositoryTest extends TesteIntegracaoBase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private final LocalServiceTestHelper fts = new LocalServiceTestHelper(
			new LocalSearchServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
		fts.setUp();

	}

	@After
	public void tearDown() {
		helper.tearDown();
		// fts.tearDown();
	}

	@Test
	public void testeListarPostsVazios() throws EntityNotFoundException {
		String resultadoEsperado = "[]";
		Assert.assertEquals(resultadoEsperado, PostRS
				.listarPosts("20", "", "0"));
	}

	@Test
	public void testeListarPosts2() throws EntityNotFoundException {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Date data = new Date();
		String id = Utils.geraID();
		Key key = KeyFactory.createKey("post", id);

		PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);
		StringBuilder comparacao = geraJsonComparacao(titulo, conteudo,
				usuario, data, id, key);

		Assert.assertEquals(comparacao.toString(), PostRS.listarPosts("20", "",
				"0"));
	}

	private String criaUmJson(String titulo, String string, String usuario,
			String id, Key key, Date data) {

		JsonObject json = new JsonObject();

		json.addProperty("id", id);
		json.addProperty("titulo", titulo);
		json.addProperty("usuario", usuario);
		json.addProperty("comentarios", "0");
		json.addProperty("dataDeAtualizacao", data.toString());
		json.addProperty("conteudo", string);
		json.addProperty("likes", "0");
		json.addProperty("data", data.toString());
		json.addProperty("key", key.toString());

		return json.toString();
	}

	@Test
	public void testeListarPosts3() throws EntityNotFoundException {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Date data = new Date();
		String id = Utils.geraID();
		Key key = KeyFactory.createKey("post", id);

		PostRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);

		String titulo2 = "Post2";
		String conteudo2 = "Content2";
		String usuario2 = "User2";

		Date data2 = new Date();
		String id2 = Utils.geraID();
		Key key2 = KeyFactory.createKey("post", id2);

		PostRepository.criaNovoPost(titulo2, conteudo2, usuario2, id2, key2,
				data2);

		String titulo3 = "Post3";
		String conteudo3 = "Content3";
		String usuario3 = "User3";

		Date data3 = new Date();
		String id3 = Utils.geraID();
		Key key3 = KeyFactory.createKey("post", id3);

		PostRepository.criaNovoPost(titulo3, conteudo3, usuario3, id3, key3,
				data3);

		StringBuilder comparacao = new StringBuilder();
		comparacao.append("["
				+ criaUmJson(titulo3, new Text(conteudo3).toString(), usuario3, id3, key3, data3)
				+ ",");
		comparacao.append(criaUmJson(titulo2, new Text(conteudo2).toString(), usuario2, id2, key2, data2)
				+ "]");

		Assert.assertEquals(comparacao.toString(), PostRS.listarPosts("2", "",
				"0"));

	}

	@Test
	public void testeListarPostsPaginados0() throws EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 45;

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);

		List<Entity> listaPostsConsultados = quandoEuListoOsPostsSalvos(
				maxResults, offSet);

		List<Entity> listaPostsEsperados = quandoEuListoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page);

		entaoEuListeiOsPostsCorretos(listaPostsEsperados, listaPostsConsultados);

	}

	@Test
	public void testeListarPostsPaginados1() throws EntityNotFoundException {

		int maxResults = 5;
		int page = 1;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 7;

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);

		List<Entity> listaPostsConsultados = quandoEuListoOsPostsSalvos(
				maxResults, offSet);

		List<Entity> listaPostsEsperados = quandoEuListoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page);

		entaoEuListeiOsPostsCorretos(listaPostsEsperados, listaPostsConsultados);

	}

	private List<Entity> quandoEuListoOsPostsPelaListaOriginal(
			List<Entity> listaPostsOriginais, int maxResults, int offSet) {

		List<Entity> listaPostsEsperados = new ArrayList<Entity>();
		int qtdOriginalDePosts = listaPostsOriginais.size();
		int limit = qtdOriginalDePosts-(maxResults * (offSet+1));
		if(limit < 0){
			limit = 0;
		}
System.out.println(limit);
		for (int i = qtdOriginalDePosts - (offSet * maxResults); i > limit; i--) {
			listaPostsEsperados.add(listaPostsOriginais.get(i-1));
		}

		return listaPostsEsperados;
	}

	private void entaoEuListeiOsPostsCorretos(List<Entity> listaPostsOriginais,
			Iterable<Entity> listaPostsConsultados) {

		Assert.assertEquals(listaPostsOriginais, listaPostsConsultados);

	}

	private List<Entity> quandoEuListoOsPostsSalvos(int maxResults, int offSet) {
		List<Entity> listaSaida = new ArrayList<Entity>();
		Iterable<Entity> it = PostRepository.buscarTodosOsPosts(maxResults,
				offSet);
		for (Entity entity : it) {
			listaSaida.add(entity);
		}
		System.out.println(listaSaida.toString());
		return listaSaida;
	}

	private List<Entity> dadoUmaListaDePostsQueEuSalvei(int cont) {
		List<Entity> listaPostsOriginais = new ArrayList<Entity>();
		for (int i = 0; i < cont; i++) {
			String titulo = "Post" + i;
			String conteudo = "Content" + i;
			String usuario = "User" + i;
			Date data = new Date();
			String id = Utils.geraID();
			Key key = KeyFactory.createKey("post", id);
			Entity entity = PostRepository.criaNovoPost(titulo, conteudo,
					usuario, id, key, data);
			listaPostsOriginais.add(entity);
		}
		return listaPostsOriginais;
	}

	private StringBuilder geraJsonComparacao(String titulo2, String conteudo2,
			String usuario2, Date data2, String id2, Key key2) {
		StringBuilder comparacao = new StringBuilder();
		comparacao.append("["
				+ criaUmJson(titulo2, new Text(conteudo2).toString(), usuario2, id2, key2, data2)
				+ "]");
		return comparacao;
	}


/*	@Test
	public void testeBuscarPosts1() throws NumberFormatException,
			EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 45;
		String q = "Content22";

		ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar=new ArrayList<Integer>();
		listaDeNumeroDosPostsQueEuQueroBuscar.add(22);

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);

		List<Entity> listaPostsConsultados = quandoEuBuscoOsPostsSalvos(
				maxResults, offSet, q);

		List<Entity> listaPostsEsperados = quandoEuBuscoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page,listaDeNumeroDosPostsQueEuQueroBuscar);

		entaoEuListeiOsPostsCorretos(listaPostsEsperados, listaPostsConsultados);

	}*/

	private List<Entity> quandoEuBuscoOsPostsPelaListaOriginal(
			List<Entity> listaPostsOriginais, int maxResults, int page,
			ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar) {
		ArrayList<Entity> listaSaida = new ArrayList<Entity>();
/*		for(Entity entity : listaPostsOriginais)
		{
			if(Integer.parseInt(entity.getProperty("titulo").toString().substring(4))==
		}*/
		return null;
	}

	private List<Entity> quandoEuBuscoOsPostsSalvos(int maxResults, int offSet,
			String q) throws EntityNotFoundException {
		List<Entity> listaSaida = new ArrayList<Entity>();
		Iterable<Entity> it = PostRepository.buscarPosts(maxResults, q, offSet);
		for (Entity entity : it) {
			listaSaida.add(entity);
		}
		return listaSaida;
	}

}
