package br.com.dextra.dextranet.post;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class PostRepositoryTest extends TesteIntegracaoBase {

	private PostRepository repositorio = new PostRepository();

	@Test
	public void testaRemocao() {
		Post novoPost = new Post("usuario").preenche("titulo", "conteudo");

		Post postCriado = repositorio.persiste(novoPost);

		String idDoPostCriado = postCriado.getId();
		repositorio.remove(idDoPostCriado);

		try {
			repositorio.obtemPorId(idDoPostCriado);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

}