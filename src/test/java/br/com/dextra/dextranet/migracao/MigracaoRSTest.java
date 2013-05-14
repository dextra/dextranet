package br.com.dextra.dextranet.migracao;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.Conteudo;
import br.com.dextra.dextranet.conteudo.post.Post;
import br.com.dextra.dextranet.conteudo.post.PostRepository;
import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioRepository;
import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class MigracaoRSTest extends TesteIntegracaoBase {

	private MigracaoRS rest = new MigracaoRS();

	private PostRepository repositorioDePosts = new PostRepository();
	private ComentarioRepository repositorioDeComentarios = new ComentarioRepository();

	private TimeMachine timeMachine = new TimeMachine();

	private Date cincoDiasAtras = timeMachine.diasParaAtras(5);
	private Date quatroDiasAtras = timeMachine.diasParaAtras(4);

	@Test
	public void testaInserirPost() throws EntityNotFoundException {
		Conteudo post = rest.criaNovoPostParaMigracao(cincoDiasAtras, "username", "titulo", "conteudo");
		Conteudo postMigrado = repositorioDePosts.obtemPorId(post.getId());

		Assert.assertEquals("username", postMigrado.getUsuario());
		Assert.assertEquals(cincoDiasAtras, postMigrado.getDataDeCriacao());
	}

	@Test
	public void testaInserirComentario() throws EntityNotFoundException {
		Conteudo post = rest.criaNovoPostParaMigracao(cincoDiasAtras, "username", "titulo", "conteudo");
		Comentario comentario = rest.criaNovoComentarioParaMigracao(post.getId(), quatroDiasAtras, "outro-usuario",
				"conteudo");

		Post postMigrado = repositorioDePosts.obtemPorId(post.getId());
		Comentario comentarioMigrado = repositorioDeComentarios.obtemPorId(comentario.getId());
		Assert.assertEquals("outro-usuario", comentarioMigrado.getUsuario());
		Assert.assertEquals(quatroDiasAtras, comentarioMigrado.getDataDeCriacao());
	}

}
