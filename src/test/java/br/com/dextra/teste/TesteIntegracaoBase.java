package br.com.dextra.teste;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.dextra.dextranet.post.Post;
import br.com.dextra.dextranet.post.PostRepository;
import br.com.dextra.dextranet.utils.Data;
import br.com.dextra.teste.container.MyContainer;

public class TesteIntegracaoBase {

	protected static MyContainer myContainer;

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

	public List<Post> geraPosts(int numeroDePosts)
			throws InterruptedException, FileNotFoundException, IOException {

		List<Post> listaDePostsCriados = new ArrayList<Post>();
		Post novoPost = null;
		String dataDeAtualizacao;
		// ESSE FOR NÃO DEVE PASSSAR DE 60 POR CAUSO DO setSegundoDaData(i)
		for (int i = 0; i <= numeroDePosts - 1; i++) {
			dataDeAtualizacao = new Data().setSegundoDaData(i);
			novoPost = new Post("titulo de teste" + (i + 1),
					"conteudo de teste" + (i + 1), "usuario", dataDeAtualizacao);
			listaDePostsCriados.add(new PostRepository().criar(novoPost));
		}
		return listaDePostsCriados;
	}

}
