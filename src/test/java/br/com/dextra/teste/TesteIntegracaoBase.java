package br.com.dextra.teste;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import br.com.dextra.dextranet.comment.Comment;
import br.com.dextra.dextranet.comment.CommentRepository;
import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.dextranet.utils.Data;
import br.com.dextra.teste.container.MyContainer;

public class TesteIntegracaoBase {

	protected static MyContainer myContainer;
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	protected final LocalServiceTestHelper fts = new LocalServiceTestHelper(new LocalSearchServiceTestConfig());

	@BeforeClass
	public static void setup() {
		myContainer = new MyContainer();
		try {
			myContainer.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@AfterClass
	public static void shutdown() {
		myContainer.stop();
	}

	@Before
	public void setUp() {
		helper.setUp();
		fts.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	public List<Post> geraPosts(int numeroDePosts) throws InterruptedException, FileNotFoundException, IOException {

		List<Post> listaDePostsCriados = new ArrayList<Post>();
		Post novoPost = null;
		String dataDeAtualizacao;
		// ESSE FOR NÃO DEVE PASSSAR DE 60 POR CAUSO DO setSegundoDaData(i)
		for (int i = 0; i <= numeroDePosts - 1; i++) {
			dataDeAtualizacao = new Data().setSegundoDaData(i);
			novoPost = new Post("titulo de teste" + (i + 1), "conteudo de teste" + (i + 1), "usuario",
					dataDeAtualizacao);
			listaDePostsCriados.add(new PostRepository().criar(novoPost));
		}

		return listaDePostsCriados;
	}

	public List<Comment> comentar(String idDoPostQueVouComentar, int qtd) throws EntityNotFoundException,
			InterruptedException, ParseException {
		Comment comment;
		Post post = new PostRepository().obtemPorId(idDoPostQueVouComentar);
		List<Comment> retorno = new ArrayList<Comment>();
		for (int i = 0; i < qtd; i++) {
			comment = new Comment("teste de comentário " + i, "usuario.dextra", idDoPostQueVouComentar, false);
			comment.setSgundoDaDataDeCriacao(i);
			new CommentRepository().criar(comment);
			post.comentar(comment);
			retorno.add(comment);
		}
		return retorno;
	}
}
