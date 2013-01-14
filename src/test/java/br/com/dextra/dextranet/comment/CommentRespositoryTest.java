package br.com.dextra.dextranet.comment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.JsonObject;

public class CommentRespositoryTest extends TesteIntegracaoBase {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private final LocalServiceTestHelper fts = new LocalServiceTestHelper(
			new LocalSearchServiceTestConfig());

	private CommentRepository commentRepository = new CommentRepository();
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
	public void criarUmComentario() {
		Comment novoComment = new Comment("Teste de Content", "marco.bordon",
				"123123123", false);
		commentRepository.criar(novoComment);

		Comment commentRecuperado = null;
		try {
			commentRecuperado = commentRepository.obtemPorId(novoComment
					.getId());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoComment.getAutor(), commentRecuperado
				.getAutor());
		Assert.assertEquals(novoComment.getIdReference(), commentRecuperado
				.getIdReference());
		Assert.assertEquals(novoComment.getText(), commentRecuperado.getText());
		Assert.assertEquals(novoComment.getDataDeCriacao(), commentRecuperado
				.getDataDeCriacao());
	}

	@Test
	public void criarComentarioEmUmPost() throws FileNotFoundException, InterruptedException, IOException, EntityNotFoundException{

		List<Post> listaDePosts = geraPosts(6);

		Comment novoComment = new Comment("Teste de Content", "marco.bordon",listaDePosts.get(2).getId() ,false);
		commentRepository.criar(novoComment);

		listaDePosts = comentaOPost(2, listaDePosts,novoComment);

		Post postRecuperado = null;
		try {
			postRecuperado = postRepository.obtemPorId(novoComment.getIdReference());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoComment.getIdReference(), postRecuperado.getId());
		Assert.assertEquals(postRecuperado.getComentarios(),1);
		Assert.assertEquals(novoComment.getDataDeCriacao(), postRecuperado.getDataDeAtualizacao());

	}

	private List<Post> comentaOPost(int i, List<Post> listaDePosts,
			Comment novoComment) throws EntityNotFoundException {

		listaDePosts.get(i).comentar(novoComment);
		return listaDePosts;
	}

	@Test
	public void consultarComentarioPeloID() throws FileNotFoundException, InterruptedException, IOException, EntityNotFoundException {

		List<Post> listaDePosts = geraPosts(6);

		Comment novoComment = new Comment("Teste de Content", "marco.bordon",listaDePosts.get(2).getId() ,false);
		Comment novoComment2 = new Comment("Teste de Content2", "gabriel.ferreira",listaDePosts.get(4).getId() ,false);
		Comment novoComment3 = new Comment("Teste de Content3", "leticia.domingues",listaDePosts.get(2).getId() ,false);
		commentRepository.criar(novoComment);
		commentRepository.criar(novoComment2);
		commentRepository.criar(novoComment3);

		listaDePosts = comentaOPost(2, listaDePosts,novoComment);
		listaDePosts = comentaOPost(4, listaDePosts,novoComment2);
		listaDePosts = comentaOPost(2, listaDePosts,novoComment3);

		String resultadoDaBusca2 = null;
		try {
			resultadoDaBusca2 = new CommentRS().consultar(novoComment.getIdReference());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}



		//Assert.assertEquals(geraStringDeArrayDeJson(novoComment,novoComment3) ,resultadoDaBusca2);


	}

	private String geraStringDeArrayDeJson(Comment novoComment,
			Comment novoComment3) {
		List<JsonObject> listaDeJson = new ArrayList<JsonObject>();

		listaDeJson.add(novoComment3.toJson());
		listaDeJson.add(novoComment.toJson());




		return listaDeJson.toString();
	}

	@Test
	@Ignore
	public void consultarUmaListaDeComentariosDeUmPost() {
	}

	@Test
	@Ignore
	public void alterarComentarioPorID() {
	}

	@Test
	@Ignore
	public void removerComentarioPorID() {
	}
}
