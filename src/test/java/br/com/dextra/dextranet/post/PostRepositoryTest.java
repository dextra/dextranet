package br.com.dextra.dextranet.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class PostRepositoryTest extends TesteIntegracaoBase {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	private final LocalServiceTestHelper fts = new LocalServiceTestHelper(new LocalSearchServiceTestConfig());
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
		Post novoPost = new Post("titulo de teste", "conteudo de teste", "usuario");
		postRepository.criar(novoPost);

		Post postRecuperado = null;
		try {
			postRecuperado = postRepository.obtemPorId(novoPost.getId());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoPost.getTitulo(), postRecuperado.getTitulo());
		Assert.assertEquals(novoPost.getConteudo(), postRecuperado.getConteudo());
		Assert.assertEquals(novoPost.getId(), postRecuperado.getId());
	}

	@Test
	public void listaTodosPosts() throws InterruptedException, FileNotFoundException, IOException {
		List<Post> posts = geraPosts(4);
		Iterator<Post> iterator = posts.iterator();
		List<String> conteudoCriado = new ArrayList<String>();

		while (iterator.hasNext()) {
			Post post = iterator.next();
			conteudoCriado.add(post.getConteudo());
		}

		Assert.assertEquals(inverteLista(conteudoCriado), pegaListaDePosts());
	}

	@Test
	public void testaRemocaoPost() {
		Post novoPost = new Post("Titulo", "Conteudo", "Usuario");
		Post postCriado = postRepository.criar(novoPost);

		String idDoPostCriado = postCriado.getId();
		postRepository.remove(idDoPostCriado);

		try {
			postRepository.obtemPorId(idDoPostCriado);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testeBuscarPosts() throws NumberFormatException, EntityNotFoundException, InterruptedException,
			FileNotFoundException, IOException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 4;

		List<Post> postsCriados = geraPosts(qtdOriginalDePosts);
		List<Post> listaPostQueEuQuero = new ArrayList<Post>();

		String idDoPostQueEuQuero = pegaOIdDoPostQueEuQuero(2, postsCriados);

		Post postQueEuQuero = postRepository.obtemPorId(idDoPostQueEuQuero);
		listaPostQueEuQuero.add(postQueEuQuero);

		List<Post> listaPostRecuperado = null;

		listaPostRecuperado = postRepository.buscarPosts(maxResults, postQueEuQuero.getTitulo(), offSet);

		Assert.assertEquals(1, listaPostRecuperado.size());
		Assert.assertEquals(listaPostQueEuQuero.get(0).getTitulo(), listaPostRecuperado.get(0).getTitulo());
		Assert.assertEquals(listaPostQueEuQuero.get(0).getConteudo(), listaPostRecuperado.get(0).getConteudo());
	}

	private List<String> inverteLista(List<String> conteudoCriado) {
		List<String> aux = new ArrayList<String>();
		for (int i = conteudoCriado.size() - 1; i >= 0; i--) {
			aux.add(conteudoCriado.get(i));
		}

		return aux;
	}

	private List<String> pegaListaDePosts() {
		Iterable<Post> postsEncontradosNaBusca = this.postRepository.buscarTodosOsPosts(4, 0);
		Iterator<Post> iterator = postsEncontradosNaBusca.iterator();
		List<String> lista = new ArrayList<String>();

		while (iterator.hasNext()) {
			Post post = iterator.next();
			lista.add(post.getConteudo());
		}

		return lista;
	}

	private String pegaOIdDoPostQueEuQuero(int i, List<Post> postsCriados) {

		return postsCriados.get(i).getId();
	}

	@Test
	public void testeCurtirPost() throws NumberFormatException, EntityNotFoundException, InterruptedException,
			FileNotFoundException, IOException {

		int maxResults = 20;
		int page = 0;
		int offSet = page * maxResults;
		int qtdOriginalDePosts = 4;

		List<Post> postsCriados = geraPosts(qtdOriginalDePosts);

		postsCriados.get(2).curtirECriarIndice("gabriel.ferreira");
		postsCriados.get(2).curtirECriarIndice("marco.bordon");
		postsCriados.get(2).curtirECriarIndice("marco.bordon");
		postsCriados.get(2).curtirECriarIndice("kaique.monteiro");

		List<Post> listaPostRecuperado = null;

		listaPostRecuperado = postRepository.buscarTodosOsPosts(maxResults, offSet);

		Assert.assertEquals(3, listaPostRecuperado.get(0).getLikes());

	}


}
