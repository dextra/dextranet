package br.com.dextra.dextranet.comment;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.dextranet.utils.Converters;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

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
	public void criarComentarioEmUmPost() throws FileNotFoundException,
			InterruptedException, IOException, EntityNotFoundException, ParseException {

		List<Post> listaDePosts = geraPosts(6);

		Comment novoComment = new Comment("Teste de Content", "marco.bordon",
				listaDePosts.get(2).getId(), false);
		commentRepository.criar(novoComment);

		listaDePosts = comentaOPost(2, listaDePosts, novoComment);

		Post postRecuperado = null;
		try {
			postRecuperado = postRepository.obtemPorId(novoComment
					.getIdReference());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoComment.getIdReference(), postRecuperado
				.getId());
		Assert.assertEquals(postRecuperado.getComentarios(), 1);
		Assert.assertEquals(novoComment.getDataDeCriacao(), postRecuperado
				.getDataDeAtualizacao());

	}

	private List<Post> comentaOPost(int i, List<Post> listaDePosts,
			Comment novoComment) throws EntityNotFoundException, ParseException {

		listaDePosts.get(i).comentar(novoComment);
		return listaDePosts;
	}

	@Test
	public void consultarComentarioPeloID() throws FileNotFoundException,
			InterruptedException, IOException, EntityNotFoundException,
			PolicyException, ScanException, ParseException {

		List<Post> listaDePosts = geraPosts(6);

		List<Comment> listaEsperada1 = comentar(listaDePosts.get(2).getId(), 2);
		List<Comment> listaEsperada2 = comentar(listaDePosts.get(4).getId(), 1);

		Assert.assertEquals(new Converters()
				.converterListaDeCommentParaListaDeJson(listaEsperada1)
				.toString(), new CommentRS().consultar(listaDePosts.get(2)
				.getId()));

		Assert.assertEquals(new Converters()
				.converterListaDeCommentParaListaDeJson(listaEsperada2)
				.toString(), new CommentRS().consultar(listaDePosts.get(4)
				.getId()));

	}

	private List<Comment> comentar(String idDoPostQueVouComentar, int qtd)
			throws EntityNotFoundException, InterruptedException, ParseException {
		Comment comment;
		Post post = new PostRepository().obtemPorId(idDoPostQueVouComentar);
		List<Comment> retorno = new ArrayList<Comment>();
		for (int i = 0; i < qtd; i++) {
			comment = new Comment("teste de comentário " + i, "usuario.dextra",
					idDoPostQueVouComentar, false);
			comment.setSgundoDaDataDeCriacao(i);
			new CommentRepository().criar(comment);
			post.comentar(comment);
			retorno.add(comment);
		}
		return retorno;
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
