package br.com.dextra.teste;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.teste.container.GAETestServer;

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

	public void limpaPostsInseridos(PostRepository repositorioDePosts) {
		List<Post> postsCadastrados = repositorioDePosts.lista();
		for (Post post : postsCadastrados) {
			repositorioDePosts.remove(post.getId());
		}
	}

	public void limpaComentariosInseridos(ComentarioRepository repositorioDeComentarios) {
		List<Comentario> comentariosCadastrados = repositorioDeComentarios.lista();
		for (Comentario comentario : comentariosCadastrados) {
			repositorioDeComentarios.remove(comentario.getId());
		}
	}

	public void limpaCurtidasInseridas(CurtidaRepository repositorioDeCurtidas) {
		List<Curtida> curtidasCadastradas = repositorioDeCurtidas.lista();
		for (Curtida curtida : curtidasCadastradas) {
			repositorioDeCurtidas.remove(curtida.getId());
		}
	}

}
