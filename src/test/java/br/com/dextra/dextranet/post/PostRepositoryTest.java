package br.com.dextra.dextranet.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class PostRepositoryTest extends TesteIntegracaoBase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private final LocalServiceTestHelper fts = new LocalServiceTestHelper(
			new LocalSearchServiceTestConfig());
	private PostRepository postRepository = new PostRepository();

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
	public void criarNovoPost() throws FileNotFoundException, IOException {
		Post novoPost = null;
		try {
			novoPost = new Post("titulo de teste", "conteudo de teste", "usuario");

		} catch (PolicyException e1) {
			Assert.fail("Policy exception");

		} catch (ScanException e1) {

			Assert.fail("Scan exception");
		}
		postRepository.criar(novoPost);

		Post postRecuperado = null;
		try {
			postRecuperado = postRepository.obtemPorId(novoPost.getId());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoPost.getTitulo(), postRecuperado.getTitulo());
		Assert.assertEquals(novoPost.getConteudo(), postRecuperado
				.getConteudo());
	}

	@Test
	public void listaTodosPosts() throws InterruptedException, FileNotFoundException, IOException {
		ArrayList<Post> posts = geraPosts(4);
		Iterator<Post> iterator = posts.iterator();
		ArrayList<String> conteudoCriado = new ArrayList<String>();

		while (iterator.hasNext()){
			Post post = iterator.next();
			conteudoCriado.add(post.getConteudo());
		}

		Assert.assertEquals(inverteLista(conteudoCriado), pegaListaDePosts());
	}

	private ArrayList<String> inverteLista(ArrayList<String> conteudoCriado) {
		ArrayList<String> aux = new ArrayList<String>();
		for (int i = conteudoCriado.size()-1; i >= 0; i--)  {
			aux.add(conteudoCriado.get(i));
		}

		return aux;
	}

	private ArrayList<String> pegaListaDePosts() {
		Iterable<Post> postsEncontradosNaBusca = this.postRepository.buscarTodosOsPosts(4, 0);
		Iterator<Post> iterator = postsEncontradosNaBusca.iterator();
		ArrayList<String> lista = new ArrayList<String>();

		while (iterator.hasNext()) {
			Post post = iterator.next();
			lista.add(post.getConteudo());
		}

		return lista;
	}

	@Test
	public void testeBuscarPosts() throws NumberFormatException,
			EntityNotFoundException, InterruptedException, FileNotFoundException, IOException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 4;

		ArrayList<Post> postsCriados = geraPosts(qtdOriginalDePosts);
		ArrayList<Post> listaPostQueEuQuero = new ArrayList<Post>();

		String idDoPostQueEuQuero = pegaOIdDoPostQueEuQuero(2, postsCriados);

		Post postQueEuQuero = postRepository.obtemPorId(idDoPostQueEuQuero);
		listaPostQueEuQuero.add(postQueEuQuero);

		ArrayList<Post> listaPostRecuperado = null;

		listaPostRecuperado = postRepository.buscarPosts(maxResults, postQueEuQuero.getTitulo(), offSet);

		Assert.assertEquals(1, listaPostRecuperado.size());
		Assert.assertEquals(listaPostQueEuQuero.get(0).getTitulo(),
				listaPostRecuperado.get(0).getTitulo());
		Assert.assertEquals(listaPostQueEuQuero.get(0).getConteudo(),
				listaPostRecuperado.get(0).getConteudo());
	}

	private String pegaOIdDoPostQueEuQuero(int i, ArrayList<Post> postsCriados) {

		return postsCriados.get(i).getId();
	}

	private ArrayList<Post> geraPosts(int numeroDePosts) throws InterruptedException, FileNotFoundException, IOException {

		ArrayList<Post> listaDePostsCriados = new ArrayList<Post>();
		Post novoPost = null;

		for (int i = 0; i < numeroDePosts; i++) {
			try {
				novoPost=new Post("titulo de teste" + (i + 1), "conteudo de teste" + (i + 1),
						"usuario");
			} catch (PolicyException e1) {
				Assert.fail("Policy exception");
			} catch (ScanException e1) {
				Assert.fail("Scan exception");
			}
			Thread.sleep(1000);
			listaDePostsCriados.add(postRepository.criar(novoPost));
		}
		return listaDePostsCriados;
	}
}
