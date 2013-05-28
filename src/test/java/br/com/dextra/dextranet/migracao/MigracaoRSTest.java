package br.com.dextra.dextranet.migracao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import junit.framework.Assert;

import org.junit.Test;

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
	public void testaDataPost() throws ParseException {
		Date dataAtual = Calendar.getInstance(Locale.ENGLISH).getTime();

		DateFormat formatPadrao = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(formatPadrao.parse(formatPadrao.format(dataAtual)));
	}

	@Test
	public void testaInserirPost() throws EntityNotFoundException {
		Post post = rest.criaNovoPostParaMigracao(cincoDiasAtras, "username", "titulo", "conteudo");
		Post postMigrado = repositorioDePosts.obtemPorId(post.getId());

		TimeMachine tMachine = new TimeMachine();
		String cincoDiasAtrasFormat = tMachine.formataData(cincoDiasAtras);
		String dataCriacaoFormat = tMachine.formataData(postMigrado.getDataDeCriacao());
		String dataAtualizacaoFormat = tMachine.formataData(postMigrado.getDataDeAtualizacao());

		Assert.assertEquals("username", postMigrado.getUsuario());
		Assert.assertEquals(cincoDiasAtrasFormat, dataCriacaoFormat);
		Assert.assertEquals(cincoDiasAtrasFormat, dataAtualizacaoFormat);
	}

	@Test
	public void testaInserirComentario() throws EntityNotFoundException {
		Post post = rest.criaNovoPostParaMigracao(cincoDiasAtras, "username", "titulo", "conteudo");
		Comentario comentario = rest.criaNovoComentarioParaMigracao(post.getId(), quatroDiasAtras, "outro-usuario",
				"conteudo");

		Post postMigrado = repositorioDePosts.obtemPorId(post.getId());
		Assert.assertEquals(quatroDiasAtras, postMigrado.getDataDeAtualizacao());

		Comentario comentarioMigrado = repositorioDeComentarios.obtemPorId(comentario.getId());
		Assert.assertEquals("outro-usuario", comentarioMigrado.getUsuario());
		Assert.assertEquals(quatroDiasAtras, comentarioMigrado.getDataDeCriacao());
	}

}
