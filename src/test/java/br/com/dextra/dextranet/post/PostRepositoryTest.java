package br.com.dextra.dextranet.post;

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
	public void criarNovoPost() {
		Post novoPost = null;
		for (int i = 0; i < 4; i++) {
			novoPost = new Post("titulo de teste"+i+1, "conteudo de teste"+i+1, "usuario");
			postRepository.criar(novoPost);
		}

		Post postRecuperado = null;
		try {
			postRecuperado = postRepository.obtemPorId(novoPost.getId());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoPost.getTitulo(), postRecuperado.getTitulo());
		Assert.assertEquals(novoPost.getConteudo(), postRecuperado.getConteudo());
	}

	@Test
	public void buscaTodosOsPosts() {

	}

	@Test
	public void buscaUmPostDaLista() {
	}

}
