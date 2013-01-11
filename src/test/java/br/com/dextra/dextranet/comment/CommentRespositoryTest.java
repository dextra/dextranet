package br.com.dextra.dextranet.comment;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
	public void criarUmComentario(){
		Comment novoComment = new Comment("Teste de Content", "marco.bordon", "123123123",false);
		commentRepository.criar(novoComment);

		Comment commentRecuperado = null;
		try {
			commentRecuperado = commentRepository.obtemPorId(novoComment.getId());
		} catch (EntityNotFoundException e) {
			Assert.fail("Post nao encontrado.");
		}

		Assert.assertEquals(novoComment.getAutor(), commentRecuperado.getAutor());
		Assert.assertEquals(novoComment.getIdReference(), commentRecuperado.getIdReference());
		Assert.assertEquals(novoComment.getText(), commentRecuperado.getText());
		Assert.assertEquals(novoComment.getDataDeCriacao(), commentRecuperado.getDataDeCriacao());
	}

	@Test
	@Ignore
	public void criarComentarioEmUmPost(){
	}

	@Test
	@Ignore
	public void consultarComentarioPeloID(){
	}

	@Test
	@Ignore
	public void consultarUmaListaDeComentariosDeUmPost(){
	}

	@Test
	@Ignore
	public void alterarComentarioPorID(){
	}

	@Test
	@Ignore
	public void removerComentarioPorID(){
	}
}
