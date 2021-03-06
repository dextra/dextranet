package br.com.dextra.teste;

import java.io.IOException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.dextra.dextranet.banner.Banner;
import br.com.dextra.dextranet.banner.BannerRepository;
import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.conteudo.post.curtida.CurtidaRepository;
import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.ServicoGrupo;
import br.com.dextra.dextranet.grupo.ServicoGrupoRepository;
import br.com.dextra.dextranet.grupo.servico.Servico;
import br.com.dextra.dextranet.grupo.servico.ServicoRepository;
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
	}

	public void limpaPostsInseridos(PostRepository repositorioDePosts) {
		List<Post> postsCadastrados = repositorioDePosts.lista();
		for (Conteudo post : postsCadastrados) {
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

	public void limpaUsuariosInseridos(UsuarioRepository repositorioDeUsuarios) throws EntityNotFoundException {
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

	public void limpaGrupoInseridos(GrupoRepository repositorioDeGrupo) {
		List<Grupo> grupos = repositorioDeGrupo.lista();
		for (Grupo grupo : grupos) {
			repositorioDeGrupo.remove(grupo.getId());
		}
	}

	public void limpaMembroInseridos(MembroRepository repositorioDeMembro) {
		List<Membro> membros = repositorioDeMembro.lista();
		if (membros != null) {
			for (Membro membro : membros) {
				repositorioDeMembro.remove(membro.getId());
			}
		}
	}

	public void limpaServico(ServicoRepository servicoRepository) {
		List<Servico> servicos = servicoRepository.lista();
		for (Servico servico : servicos) {
			servicoRepository.remove(servico.getId());
		}
	}

	public void limpaServicoGrupo(ServicoGrupoRepository servicoGrupoRepository) {
		List<ServicoGrupo> servicoGrupos = servicoGrupoRepository.lista();
		for (ServicoGrupo servicoGrupo : servicoGrupos) {
	        servicoGrupoRepository.remove(servicoGrupo.getId());
        }
	}

	protected void limpaMicroPostsInseridos(MicroBlogRepository repository) {
		List<MicroPost> microPosts = repository.buscarMicroPosts();
		for (MicroPost micropost : microPosts) {
			repository.remove(micropost.getId());
		}
	}
}