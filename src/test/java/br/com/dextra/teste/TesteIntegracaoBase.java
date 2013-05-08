package br.com.dextra.teste;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.dextra.dextranet.banner.Banner;
import br.com.dextra.dextranet.banner.BannerRepository;
import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.dextranet.microblog.MicroBlogRepository;
import br.com.dextra.dextranet.microblog.MicroPost;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.container.GAETestServer;

public class TesteIntegracaoBase {

	protected static GAETestServer server = new GAETestServer();

	private static final boolean noStorage = true;

	@BeforeClass
	public static void setup() {
		server.enableDatastore(noStorage);
		server.enableSearch();
		server.start();
	}

	@AfterClass
	public static void shutdown() throws IOException {
		server.stop();
		FileUtils.deleteDirectory(new File("WEB-INF"));
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

	public void limpaUsuariosInseridos(UsuarioRepository repositorioDeUsuarios) {
		List<Usuario> usuarios = repositorioDeUsuarios.lista();
		for (Usuario usuario : usuarios) {
			repositorioDeUsuarios.remove(usuario.getId());
		}
	}

	public void limpaBannersInseridos(BannerRepository repositorioDeBanners) {
		List<Banner> bannersCadastrados = repositorioDeBanners.lista();
		for (Banner banner : bannersCadastrados) {
			repositorioDeBanners.remove(banner.getId());
		}
	}

	protected void limpaMicroPostsInseridos(MicroBlogRepository repository) {
		List<MicroPost> microPosts = repository.buscarMicroPosts();
		for (MicroPost micropost : microPosts) {
			repository.remove(micropost.getId());
		}
	}

}
