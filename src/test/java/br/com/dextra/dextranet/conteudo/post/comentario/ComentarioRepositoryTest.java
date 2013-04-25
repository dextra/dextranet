package br.com.dextra.dextranet.conteudo.post.comentario;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class ComentarioRepositoryTest extends TesteIntegracaoBase {

	private PostRepository repositorio = new PostRepository();

	@Test
	public void testaRemocao() {
		Comentario novoComentario = new Comentario("post-id", "usuario", "conteudo");

		Comentario comentarioCriado = repositorio.persiste(novoComentario);
		comentarioCriado.curtir("dextranet");

		String idDoComentarioCriado = comentarioCriado.getId();
		repositorio.remove(idDoComentarioCriado);

		try {
			repositorio.obtemPorId(idDoComentarioCriado);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

}