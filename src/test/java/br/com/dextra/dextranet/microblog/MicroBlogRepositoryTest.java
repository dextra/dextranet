package br.com.dextra.dextranet.microblog;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

public class MicroBlogRepositoryTest extends TesteIntegracaoBase {

	private MicroBlogRepository repository = new MicroBlogRepository();

	@Before
	public void removeMicroPostsInseridos() {
		this.limpaMicroPostsInseridos(repository);
	}

	@Test
	public void removerMicroPost() {
		MicroPost micropost = new MicroPost("micromessage");
		repository.salvar(micropost);

		assertEquals(1, repository.buscarMicroPosts().size());

		repository.remove(micropost.getId());

		assertEquals(0, repository.buscarMicroPosts().size());
	}

	@Test
	public void micropostar() {
		MicroPost micropost = new MicroPost("micromessage");
		repository.salvar(micropost);

		assertEquals(1, repository.buscarMicroPosts().size());
		assertEquals("micromessage", repository.buscarMicroPosts().get(0)
				.getTexto());
	}

	@Test
	@Ignore
	public void testOrdemPosts() {
		MicroPost microPost = new MicroPost("micromessa1");
		MicroPost microPost2 = new MicroPost("micromessa2");
		MicroPost microPost3 = new MicroPost("micromessa3");
		repository.salvar(microPost);
		repository.salvar(microPost2);
		repository.salvar(microPost3);

		List<MicroPost> buscarMicroPosts = repository.buscarMicroPosts();
		assertEquals(3, buscarMicroPosts.size());
		assertEquals("micromessa3", buscarMicroPosts.get(0).getTexto());
		assertEquals("micromessa2", buscarMicroPosts.get(1).getTexto());
		assertEquals("micromessa1", buscarMicroPosts.get(2).getTexto());
	}
}
