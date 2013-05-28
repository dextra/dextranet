package br.com.dextra.dextranet.indexacao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostFields;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

public class IndexacaoRepositoryTest extends TesteIntegracaoBase {

	private PostRepository repositorioDePosts = new PostRepository();
	private IndexacaoRepository repositorioDeIndex = new IndexacaoRepository();
	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	@Test
	public void testaBuscaPostsIndexados() {
		Post post01 = new Post("usuario01", "post1", "esse eh um post de teste");
		Post post02 = new Post("usuario02", "post2", "esse eh um post de teste");
		repositorioDePosts.persiste(post01);
		repositorioDePosts.persiste(post02);

		List<Post> busca = repositorioDeIndex.buscar(Post.class, PostFields.titulo.name() + ": post1");
		Assert.assertEquals(1, busca.size());

		busca = repositorioDeIndex.buscar(Post.class, PostFields.conteudo.name() + ": esse eh um post de teste");
		Assert.assertEquals(2, busca.size());

		busca = repositorioDeIndex.buscar(Post.class, PostFields.usuario.name() + ": usuario01");
		Assert.assertEquals(1, busca.size());

		busca = repositorioDeIndex.buscar(Post.class, "esse eh um post de teste");
		Assert.assertEquals(2, busca.size());

		busca = repositorioDeIndex.buscar(Post.class, "esse eh um post de teste");
		Assert.assertEquals(2, busca.size());

	}

	@Test
	public void testaBuscaComentariosIndexados() {
		Post post01 = new Post("usuario01", "post1", "esse eh um post de teste");
		Post post02 = new Post("usuario02", "post2", "esse eh um post de teste");
		repositorioDePosts.persiste(post01);
		repositorioDePosts.persiste(post02);

		Comentario comentario01 = new Comentario(post01.getId(), "usuario", "conteudo1-1");
		Comentario comentario02 = new Comentario(post02.getId(), "usuario", "conteudo2-1");
		repositorioDeComentarios.persiste(comentario01);
		repositorioDeComentarios.persiste(comentario02);

		List<Comentario> comentarios = repositorioDeIndex.buscar(Comentario.class, "conteudo1-1");
		Assert.assertEquals(1, comentarios.size());

		comentarios = repositorioDeIndex.buscar(Comentario.class, "conteudo2-1");
		Assert.assertEquals(1, comentarios.size());

	}

}
