package br.com.dextra.dextranet.conteudo.post.curtida;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class CurtidaRepositoryTest extends TesteIntegracaoBase {

	private CurtidaRepository repositorio = new CurtidaRepository();

	@Test
	public void testaRemocao() {
		Post novoPost = new Post("usuario").preenche("titulo", "conteudo");
		Curtida curtida = novoPost.curtir("dextranet");

		Curtida curtidaCriada = repositorio.persiste(curtida);

		String idDaCurtidaCriada = curtidaCriada.getId();
		repositorio.remove(idDaCurtidaCriada);

		try {
			repositorio.obtemPorId(idDaCurtidaCriada);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

}