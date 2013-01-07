package br.com.dextra.teste.servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.dextra.dextranet.post.PostRS;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.persistencia.CommentFields;
import br.com.dextra.persistencia.PostFields;
import br.com.dextra.teste.TesteIntegracaoBase;
import br.com.dextra.utils.IndexKeys;
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

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private final LocalServiceTestHelper fts = new LocalServiceTestHelper(new LocalSearchServiceTestConfig());
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
	@Ignore
	public void listarPostsVazios() throws EntityNotFoundException {
		String resultadoEsperado = "[]";
		Assert.assertEquals(resultadoEsperado, new PostRS().listarPosts("20","", "0"));
	}

	@Test
	@Ignore
	public void criarUmPostEVerificaOServicoDeListarPosts() throws EntityNotFoundException {
		ArrayList<Entity> lista = new ArrayList<Entity>();
		PostRepository repositorio = new PostRepository();

		String titulo = "Post1";
		String conteudo = "Content1";
		String usuario = "User1";

		Entity entidade = repositorio.criar(titulo, conteudo, usuario);
		lista.add(entidade);

		Assert.assertEquals(extraiEntityToJson(lista), new PostRS().listarPosts("20", "", "0"));
	}

	@Test
	@Ignore
	public void criarPostsEVerificaOServicoDeListarPosts() throws EntityNotFoundException, InterruptedException {
		int quantidade = 10;
		String retornoEsperado = criaPostsERecuperaJson(quantidade);

		Assert.assertEquals(retornoEsperado, new PostRS().listarPosts(Integer.toString(quantidade), "", "0"));
	}


	private String criaPostsERecuperaJson (int quantidade) throws InterruptedException {
		ArrayList<Entity> entidades = criarPosts(quantidade);

		return extraiEntityToJson(entidades);
	}

	private ArrayList<Entity> criarPosts(int quantidade)throws InterruptedException {
		ArrayList<Entity> entidades =  new ArrayList<Entity>();

		for (int i = 1; i <= quantidade; i++) {
			Entity entidade = postDoRepository.criar("titulo numero = " + i,"conteudo numero = " + i, "usuario numero = " + i);
			entidades.add(entidade);
			Thread.sleep(987);
		}
		Collections.reverse(entidades);

		return entidades;
	}

	private String extraiEntityToJson(ArrayList<Entity> entidades) {
		StringBuilder jsonExtraido = new StringBuilder();
		jsonExtraido.append("[");

		Iterator<Entity> iterator = entidades.iterator();

		while(iterator.hasNext()){
			Entity entidade = iterator.next();

			String titulo = (String) entidade.getProperty(PostFields.TITULO.getField());
			Text conteudo = (Text) entidade.getProperty(PostFields.CONTEUDO.getField());
			String usuario = (String) entidade.getProperty(PostFields.USUARIO.getField());
			String id = (String) entidade.getProperty(PostFields.ID.getField());
			String data = (String) entidade.getProperty(PostFields.DATA.getField());
			String comentarios = "0";
			Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);

			jsonExtraido.append(this.criaUmJson(titulo, conteudo.getValue(), usuario, id, key, data, comentarios));

			if(iterator.hasNext()){
				jsonExtraido.append(",");
			}
		}
		jsonExtraido.append("]");
		return jsonExtraido.toString();
	}

	private  ArrayList<String> verificarSePostEstaNaLista(String tituloEsperado,ArrayList<Entity> listaDePosts){

		ArrayList<String> lista = new ArrayList<String>();
		Iterator<Entity> iterator = listaDePosts.iterator();

		while (iterator.hasNext()) {

			Entity entidade = iterator.next();
			String titulo = (String) entidade.getProperty(PostFields.TITULO.getField());

			if (titulo.equals(tituloEsperado)) {
				lista.add(titulo);
			}
		}
		return lista;
	}



	@Test
	public void testeBuscarPosts1() throws NumberFormatException,EntityNotFoundException, InterruptedException {
		ArrayList<Entity> listaOriginalDePosts = this.criarPosts(10);
		ArrayList<String> listaPostsEsperadosParaABusca = verificarSePostEstaNaLista("titulo numero = 7",listaOriginalDePosts);
		ArrayList<String> listaDePostsBuscados = buscaPosts();

		Assert.assertEquals(listaPostsEsperadosParaABusca,listaDePostsBuscados);


	}
	private ArrayList<String> buscaPosts() throws EntityNotFoundException{
		Iterable<Entity> listaPostsQueRetornaramDaBusca = this.postDoRepository.buscarPosts(20,"titulo numero = 7", 0);
		Iterator<Entity> iterator = listaPostsQueRetornaramDaBusca.iterator();
		ArrayList<String> lista = new ArrayList<String>();

		while(iterator.hasNext()){
			Entity entidade = iterator.next();
			String titulo = (String) entidade.getProperty(PostFields.TITULO.getField());
			lista.add(titulo);
		}

		return lista;
	}




	/*	private List<Entity> dadoUmaListaDePostsQueEuSalvei(int cont) {
			List<Entity> listaPostsOriginais = new ArrayList<Entity>();

			for (int i = 0; i < cont; i++) {
				String conteudo = "Content" + i;
				String titulo = "Post" + i;
				if (i == 34 || i == 23)
					conteudo = "Content 23 ou 34";
				String usuario = "User" + i;
				Date data = new Date();
				data.setDate(i);
				String id = Utils.geraID();
				Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
				Entity entity = postDoRepository.criar(titulo, conteudo,usuario);
				listaPostsOriginais.add(entity);
			}

			return listaPostsOriginais;
		}
	*/

/*	@Test
	public void testeBuscarPosts2() throws NumberFormatException,EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 45;
		String q = "Content";

		ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar = new ArrayList<Integer>();
		listaDeNumeroDosPostsQueEuQueroBuscar.add(34);
		listaDeNumeroDosPostsQueEuQueroBuscar.add(23);

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);
		List<Entity> listaPostsConsultados = quandoEuBuscoOsPostsSalvos(maxResults, q, offSet);
		List<Entity> listaPostsEsperados = quandoEuBuscoOsPostsPelaListaOriginal(listaPostsOriginais, maxResults, page,listaDeNumeroDosPostsQueEuQueroBuscar);

		entaoEuListeiOsPostsCorretos(listaPostsEsperados, listaPostsConsultados);
	}*/

	/*@Test
	public void testeListarPostsPaginados0() throws EntityNotFoundException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 31;

		List<Entity> listaPostsOriginais = dadoUmaListaDePostsQueEuSalvei(qtdOriginalDePosts);
		List<Entity> listaPostsConsultados = quandoEuListoOsPostsSalvos(maxResults, offSet);
		List<Entity> listaPostsEsperados = quandoEuListoOsPostsPelaListaOriginal(listaPostsOriginais, maxResults, page);

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
*/
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

	private StringBuilder geraJsonComparacao(String titulo2, String conteudo2,
			String usuario2, String data2, String id2, Key key2) {
			StringBuilder comparacao = new StringBuilder();
			comparacao.append("["+ criaUmJson(titulo2, conteudo2, usuario2, id2, key2, data2,"0") + "]");

			return comparacao;
	}

	private String criaUmJson(String titulo, String string, String usuario,String id, Key key, String data, String comentario) {

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

	private List<Entity> quandoEuBuscoOsPostsPelaListaOriginal(List<Entity> listaPostsOriginais, int maxResults, int page,
			ArrayList<Integer> listaDeNumeroDosPostsQueEuQueroBuscar) {
		ArrayList<Entity> listaSaida = new ArrayList<Entity>();

		for (Entity entity : listaPostsOriginais) {

			for (int post : listaDeNumeroDosPostsQueEuQueroBuscar) {
				if (Integer.parseInt(entity.getProperty("titulo").toString().substring(16)) == post) {
					listaSaida.add(entity);
				}

			}
		}

		Collections.reverse(listaSaida);

		return listaSaida;
	}

	private List<Entity> quandoEuBuscoOsPostsSalvos(int maxResults, String q,int offSet) throws EntityNotFoundException {
		List<Entity> listaSaida = new ArrayList<Entity>();
		Iterable<Entity> it = postDoRepository.buscarPosts(maxResults, q,offSet);

		for (Entity entity : it) {
			listaSaida.add(entity);
		}

		return listaSaida;
	}

	private List<Entity> dadoUmaOutraListaDePostsQueEuSalvei(int cont)throws InterruptedException {

		List<Entity> listaPostsOriginais = new ArrayList<Entity>();
		for (int i = 0; i < cont; i++) {
			String conteudo = "Content " + i;
			String titulo = "Post" + i;
			String usuario = "User" + i;
			String data = Utils.pegaData();
			String id = Utils.geraID();
			Key key = KeyFactory.createKey(IndexKeys.POST.getKey(), id);
			Entity entity = postDoRepository.criar(titulo, conteudo, usuario);
			listaPostsOriginais.add(entity);
			Thread.sleep(1000);
		}
		return listaPostsOriginais;
	}

	public void testaRemocaoPost() {
		Entity postCriado = postDoRepository.criar("Titulo", "Conteudo","Usuario");
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
