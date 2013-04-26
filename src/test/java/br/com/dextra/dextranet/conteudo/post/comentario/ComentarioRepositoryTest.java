package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class ComentarioRepositoryTest extends TesteIntegracaoBase {

	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	private PostRepository repositorioDePosts = new PostRepository();

	@Test
	public void testaRemocao() {
		Comentario novoComentario = new Comentario("post-id", "usuario", "conteudo");

		Comentario comentarioCriado = repositorioDeComentarios.persiste(novoComentario);

		String idDoComentarioCriado = comentarioCriado.getId();
		repositorioDeComentarios.remove(idDoComentarioCriado);

		try {
			repositorioDeComentarios.obtemPorId(idDoComentarioCriado);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testaListaPorPost() {
		Post post01 = new Post("usuario", "titulo 01", "conteudo 01");
		Comentario comentario01 = post01.comentar("dextranet", "comentario dextranet");
		Comentario comentario02 = post01.comentar("outro-usuario", "comentario outro-usuario");

		Post post02 = new Post("dextranet", "titulo 02", "conteudo 02");
		Comentario comentario03 = post02.comentar("usuario", "comentario usuario");
		Comentario comentario04 = post02.comentar("outro-usuario", "comentario outro-usuario");

		repositorioDePosts.persiste(post01);
		repositorioDeComentarios.persiste(comentario02);
		repositorioDeComentarios.persiste(comentario01);

		repositorioDePosts.persiste(post02);
		repositorioDeComentarios.persiste(comentario04);
		repositorioDeComentarios.persiste(comentario03);

		List<Comentario> comentariosDoPost = repositorioDeComentarios.listaPorPost(post01.getId());
		Assert.assertEquals(2, comentariosDoPost.size());
		Assert.assertEquals(comentario01, comentariosDoPost.get(0));
		Assert.assertEquals(comentario02, comentariosDoPost.get(1));
	}

}