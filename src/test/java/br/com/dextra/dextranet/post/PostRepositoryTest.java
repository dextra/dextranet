package br.com.dextra.dextranet.post;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
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
	public void criarNovoPost() {
		Post novoPost = null;
		novoPost = new Post("titulo de teste", "conteudo de teste", "usuario");
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
	public void buscaTodosOsPosts() {

	}

	@Test
	public void testeBuscarPosts() throws NumberFormatException,
			EntityNotFoundException {

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

	private ArrayList<Post> geraPosts(int numeroDePosts) {

		ArrayList<Post> listaDePostsCriados = new ArrayList<Post>();
		Post novoPost = null;

		for (int i = 0; i < numeroDePosts; i++) {
			novoPost=new Post("titulo de teste" + i + 1, "conteudo de teste" + i + 1,
					"usuario");
			listaDePostsCriados.add(postRepository.criar(novoPost));
		}
		return listaDePostsCriados;
	}
}
