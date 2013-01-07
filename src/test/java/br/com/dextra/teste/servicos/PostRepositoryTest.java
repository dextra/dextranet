package br.com.dextra.teste.servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.post.PostFields;
import br.com.dextra.dextranet.post.PostRS;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.teste.TesteIntegracaoBase;
import br.com.dextra.utils.IndexKeys;
import br.com.dextra.utils.Data;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.JsonObject;

public class PostRepositoryTest extends TesteIntegracaoBase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private final LocalServiceTestHelper fts = new LocalServiceTestHelper(
			new LocalSearchServiceTestConfig());

	private PostRepository postDoRepository = new PostRepository();

	@Before
	public void setUp() {
		helper.setUp();
		fts.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testeListarPostsVazios() throws EntityNotFoundException {
		String resultadoEsperado = "[]";
		Assert.assertEquals(resultadoEsperado, new PostRS().listarPosts("20",
				"", "0"));
	}

	@Test
	public void testeListarPosts2() throws EntityNotFoundException {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		String data = new Data().pegaData();
		String id = Data.geraID();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);

		postDoRepository.criaNovoPost(titulo, conteudo, usuario, id, key, data);
		StringBuilder comparacao = geraJsonComparacao(titulo, conteudo,
				usuario, data, id, key);

		Assert.assertEquals(comparacao.toString(), new PostRS().listarPosts(
				"20", "", "0"));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testeListarPosts3() throws EntityNotFoundException {

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Date data = new Date();
		data.setDate(1);
		String id = Data.geraID();
		Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);

		postDoRepository.criaNovoPost(titulo, conteudo, usuario, id, key, new Data()
				.formataPelaBiblioteca(data));

		String titulo2 = "Post2";
		String conteudo2 = "Content2";
		String usuario2 = "User2";

		Date data2 = new Date();
		data2.setDate(2);
		String id2 = Data.geraID();
		Key key2 = KeyFactory.createKey(IndexKeys.POST.getKey(), id2);

		postDoRepository.criaNovoPost(titulo2, conteudo2, usuario2, id2, key2,
				new Data().formataPelaBiblioteca(data2));

		String titulo3 = "Post3";
		String conteudo3 = "Content3";
		String usuario3 = "User3";

		Date data3 = new Date();
		data3.setDate(3);
		String id3 = Data.geraID();
		Key key3 = KeyFactory.createKey(IndexKeys.POST.getKey(), id3);

		postDoRepository.criaNovoPost(titulo3, conteudo3, usuario3, id3, key3,
				new Data().formataPelaBiblioteca(data3));

		StringBuilder comparacao = new StringBuilder();
		comparacao.append("["
				+ criaUmJson(titulo3, conteudo3, usuario3, id3, key3, new Data()
						.formataPelaBiblioteca(data3), "0") + ",");
		comparacao.append(criaUmJson(titulo2, conteudo2, usuario2, id2, key2,
				new Data().formataPelaBiblioteca(data2), "0")
				+ "]");

		Assert.assertEquals(comparacao.toString(), new PostRS().listarPosts(
				"2", "", "0"));

	}

	@Test
	public void testeBuscarPosts1() throws NumberFormatException,
			EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 45;
		String q = "Post29";

		ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar = new ArrayList<Integer>();
		listaDeNumeroDosPostsQueEuQueroBuscar.add(29);

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);

		List<Entity> listaPostsConsultados = quandoEuBuscoOsPostsSalvos(
				maxResults, q, offSet);

		List<Entity> listaPostsEsperados = quandoEuBuscoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page,
				listaDeNumeroDosPostsQueEuQueroBuscar);

		entaoEuListeiOsPostsCorretos(listaPostsEsperados, listaPostsConsultados);

	}

	@Test
	public void testeBuscarPosts2() throws NumberFormatException,
			EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 45;
		String q = "Content";

		ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar = new ArrayList<Integer>();
		listaDeNumeroDosPostsQueEuQueroBuscar.add(34);
		listaDeNumeroDosPostsQueEuQueroBuscar.add(23);

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);

		List<Entity> listaPostsConsultados = quandoEuBuscoOsPostsSalvos(
				maxResults, q, offSet);

		List<Entity> listaPostsEsperados = quandoEuBuscoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page,
				listaDeNumeroDosPostsQueEuQueroBuscar);

		entaoEuListeiOsPostsCorretos(listaPostsEsperados, listaPostsConsultados);

	}

	@Test
	public void testeListarPostsPaginados0() throws EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 31;

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

	@Test
	public void testeDeAtualizarPostQuandoComentadoNaEntity()
			throws NumberFormatException, EntityNotFoundException,
			InterruptedException {

		int maxResults = 1;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 5;
		int postQueEuQuero = 3;

		ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar = new ArrayList<Integer>();
		listaDeNumeroDosPostsQueEuQueroBuscar.add(postQueEuQuero);

		List<Entity> listaPostsOriginais = dadoUmaOutraListaDePostsQueEuSalvei(qtdOriginalDePosts);

		Entity entity = listaPostsOriginais.get(postQueEuQuero);

		postDoRepository
				.umPostFoiComentado(entity.getProperty("id").toString());

		List<Entity> listaPostsConsultados = quandoEuListoOsPostsSalvos(
				maxResults, offSet);

		System.out.println(listaPostsConsultados);

		List<Entity> listaPostsEsperados = quandoEuBuscoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page,
				listaDeNumeroDosPostsQueEuQueroBuscar);

		entaoComparoSeOCampoQueEuQueroEhDiferente(listaPostsEsperados,
				listaPostsConsultados, PostFields.DATA_DE_ATUALIZACAO
						.getField());

		entaoEuComparoSeONumeroDeComentariosFoiIncrementado(
				listaPostsEsperados, listaPostsConsultados);

	}

	@Test
	public void testeDeAtualizarPostQuandoComentadoNoDocumment()
			throws NumberFormatException, EntityNotFoundException,
			InterruptedException {

		int maxResults = 1;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 5;
		String q = "Content ";
		int postQueEuQuero = 3;

		ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar = new ArrayList<Integer>();
		listaDeNumeroDosPostsQueEuQueroBuscar.add(postQueEuQuero);

		List<Entity> listaPostsOriginais = dadoUmaOutraListaDePostsQueEuSalvei(qtdOriginalDePosts);

		Entity entity = listaPostsOriginais.get(postQueEuQuero);

		postDoRepository
				.umPostFoiComentado(entity.getProperty("id").toString());

		List<Entity> listaPostsConsultados = quandoEuBuscoOsPostsSalvos(maxResults, q, offSet);

		System.out.println(listaPostsConsultados);

		List<Entity> listaPostsEsperados = quandoEuBuscoOsPostsPelaListaOriginal(
				listaPostsOriginais, maxResults, page,
				listaDeNumeroDosPostsQueEuQueroBuscar);

		entaoComparoSeOCampoQueEuQueroEhDiferente(listaPostsEsperados,
				listaPostsConsultados, PostFields.DATA_DE_ATUALIZACAO
						.getField());

		entaoEuComparoSeONumeroDeComentariosFoiIncrementado(
				listaPostsEsperados, listaPostsConsultados);

	}

	private List<Entity> quandoEuListoOsPostsPelaListaOriginal(
			List<Entity> listaPostsOriginais, int maxResults, int offSet) {

		List<Entity> listaPostsEsperados = new ArrayList<Entity>();
		int qtdOriginalDePosts = listaPostsOriginais.size();
		int limit = qtdOriginalDePosts - (maxResults * (offSet + 1));
		if (limit < 0) {
			limit = 0;
		}
		for (int i = qtdOriginalDePosts - (offSet * maxResults); i > limit; i--) {
			listaPostsEsperados.add(listaPostsOriginais.get(i - 1));
		}

		return listaPostsEsperados;
	}

	private void entaoEuListeiOsPostsCorretos(List<Entity> listaPostsOriginais,
			Iterable<Entity> listaPostsConsultados) {

		Assert.assertEquals(listaPostsOriginais, listaPostsConsultados);

	}

	private void entaoComparoSeOCampoQueEuQueroEhDiferente(
			List<Entity> listaPostsOriginais,
			Iterable<Entity> listaPostsConsultados, String campoQueEuQuero) {

		List<Entity> listaMudadaPostsConsultados = (List<Entity>) listaPostsConsultados;

		Assert.assertEquals(listaPostsOriginais.size(),
				listaMudadaPostsConsultados.size());

		for (int i = 0; i < listaPostsOriginais.size(); i++) {

			Assert.assertNotSame(listaPostsOriginais.get(i).getProperty(
					campoQueEuQuero).toString(), listaMudadaPostsConsultados
					.get(i).getProperty(campoQueEuQuero).toString());

		}
	}

	private void entaoEuComparoSeONumeroDeComentariosFoiIncrementado(
			List<Entity> listaPostsOriginais,
			Iterable<Entity> listaPostsConsultados) {

		List<Entity> listaMudadaPostsConsultados = (List<Entity>) listaPostsConsultados;

		Assert.assertEquals(listaPostsOriginais.size(),
				listaMudadaPostsConsultados.size());

		int comentarios = 0;

		for (int i = 0; i < listaPostsOriginais.size(); i++) {

			comentarios = Integer.parseInt(listaPostsOriginais.get(i)
					.getProperty(PostFields.COMENTARIO.getField()).toString()) + 1;
			System.out.println(comentarios);
			Assert.assertEquals(String.valueOf(comentarios),
					listaMudadaPostsConsultados.get(i).getProperty(
							PostFields.COMENTARIO.getField()).toString());
		}

	}

	private List<Entity> quandoEuListoOsPostsSalvos(int maxResults, int offSet) {
		List<Entity> listaSaida = new ArrayList<Entity>();
		Iterable<Entity> it = postDoRepository.buscarTodosOsPosts(maxResults,
				offSet);
		for (Entity entity : it) {
			listaSaida.add(entity);
		}
		return listaSaida;
	}

	@SuppressWarnings("deprecation")
	private List<Entity> dadoUmaListaDePostsQueEuSalvei(int cont) {
		List<Entity> listaPostsOriginais = new ArrayList<Entity>();
		for (int i = 0; i < cont; i++) {
			String conteudo = "Content" + i;
			String titulo = "Post" + i;
			if (i == 34 || i == 23)
				conteudo = "Content 23 ou 34";
			String usuario = "User" + i;
			Date data = new Date();
			data.setDate(i);
			String id = Data.geraID();
			Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
			Entity entity = postDoRepository.criaNovoPost(titulo, conteudo,
					usuario, id, key, new Data().formataPelaBiblioteca(data));
			listaPostsOriginais.add(entity);
		}
		return listaPostsOriginais;
	}

	private StringBuilder geraJsonComparacao(String titulo2, String conteudo2,
			String usuario2, String data2, String id2, Key key2) {
		StringBuilder comparacao = new StringBuilder();
		comparacao.append("["
				+ criaUmJson(titulo2, conteudo2, usuario2, id2, key2, data2,
						"0") + "]");
		return comparacao;
	}

	private String criaUmJson(String titulo, String string, String usuario,
			String id, Key key, String data, String comentario) {

		JsonObject json = new JsonObject();

		json.addProperty("id", id);
		json.addProperty("titulo", titulo);
		json.addProperty("usuario", usuario);
		json.addProperty("comentarios", comentario);
		json.addProperty("dataDeAtualizacao", data);
		json.addProperty("conteudo", string);
		json.addProperty("likes", "0");
		json.addProperty("data", data);
		json.addProperty("key", key.toString());

		return json.toString();
	}

	private List<Entity> quandoEuBuscoOsPostsPelaListaOriginal(
			List<Entity> listaPostsOriginais, int maxResults, int page,
			ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar) {
		ArrayList<Entity> listaSaida = new ArrayList<Entity>();
		for (Entity entity : listaPostsOriginais) {

			for (int post : listaDeNumeroDosPostsQueEuQueroBuscar) {
				if (Integer.parseInt(entity.getProperty("titulo").toString()
						.substring(4)) == post) {
					listaSaida.add(entity);
				}

			}
		}

		Collections.reverse(listaSaida);

		return listaSaida;
	}

	private List<Entity> quandoEuBuscoOsPostsSalvos(int maxResults, String q,
			int offSet) throws EntityNotFoundException {

		List<Entity> listaSaida = new ArrayList<Entity>();
		Iterable<Entity> it = postDoRepository.buscarPosts(maxResults, q,
				offSet);
		for (Entity entity : it) {
			listaSaida.add(entity);
		}
		return listaSaida;
	}

	private List<Entity> dadoUmaOutraListaDePostsQueEuSalvei(int cont)
			throws InterruptedException {
		List<Entity> listaPostsOriginais = new ArrayList<Entity>();
		for (int i = 0; i < cont; i++) {
			String conteudo = "Content " + i;
			String titulo = "Post" + i;
			String usuario = "User" + i;
			String data = new Data().pegaData();
			String id = Data.geraID();
			Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
			Entity entity = postDoRepository.criaNovoPost(titulo, conteudo,
					usuario, id, key, data);
			listaPostsOriginais.add(entity);
			Thread.sleep(1000);
		}
		return listaPostsOriginais;
	}

	public void testaRemocaoPost() {
		Entity postCriado = postDoRepository.criaNovoPost("Titulo", "Conteudo",
				"Usuario");
		String idDoPost = postCriado.getProperty("id").toString();

		postDoRepository.remove(idDoPost);

		try {
			postDoRepository.obtemPorId(idDoPost);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}
}
