package br.com.dextra.dextranet.microblog;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Query.SortDirection;

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
	public void testOrdemPosts() {
		TimeMachine timeMachine = new TimeMachine();
		Date inicioDoDia = timeMachine.inicioDoDia(new Date());
		Date fimDoDia = timeMachine.fimDoDia(inicioDoDia);
		Date umDiaAtras = timeMachine.diasParaAtras(inicioDoDia, 1);

		MicroPost microPost = new MicroPost("micromessa1", umDiaAtras);
		repository.salvar(microPost);
		MicroPost microPost2 = new MicroPost("micromessa2", inicioDoDia);
		repository.salvar(microPost2);
		MicroPost microPost3 = new MicroPost("micromessa3", fimDoDia);
		repository.salvar(microPost3);

		EntidadeOrdenacao ordem = new EntidadeOrdenacao(MicroBlogFields.DATA.getField(), SortDirection.DESCENDING);
		List<MicroPost> buscarMicroPosts = repository.lista(ordem);
		assertEquals(3, buscarMicroPosts.size());
		assertEquals("micromessa3", buscarMicroPosts.get(0).getTexto());
		assertEquals("micromessa2", buscarMicroPosts.get(1).getTexto());
		assertEquals("micromessa1", buscarMicroPosts.get(2).getTexto());
	}
}
