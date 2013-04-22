package br.com.dextra.teste;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.dextranet.post.comment.Comment;
import br.com.dextra.dextranet.post.comment.CommentRepository;
import br.com.dextra.dextranet.utils.Data;
import br.com.dextra.teste.container.GAETestServer;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class TesteIntegracaoBase {

	protected static GAETestServer server = new GAETestServer();

	private static final boolean noStorage = true;

	@BeforeClass
	public static void setup() {
		server.enableDatastore(noStorage);
		server.start();
	}

	@AfterClass
	public static void shutdown() {
		server.stop();
	}

	public List<Post> geraPosts(int numeroDePosts) throws InterruptedException, FileNotFoundException, IOException {

		List<Post> listaDePostsCriados = new ArrayList<Post>();
		Post novoPost = null;
		String dataDeAtualizacao;
		// ESSE FOR NAO DEVE PASSSAR DE 60 POR CAUSO DO setSegundoDaData(i)
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
			comment = new Comment("teste de comentario " + i, "usuario.dextra", idDoPostQueVouComentar, false);
			comment.setSegundoDaDataDeCriacao(i);
			new CommentRepository().criar(comment);
			post.comentar(comment);
			retorno.add(comment);
		}
		return retorno;
	}
}
