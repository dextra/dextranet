package br.com.dextra.dextranet.conteudo.post;

import java.util.Date;

import org.junit.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.curtida.Curtida;
import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

//a heranca eh para poder testar os objetos envolvendo entity
public class PostTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaPreenche() {
		Post novoPost = new Post("usuario", "<script>alert('Hello!');</script><h3>titulo</h3>",
				"<script>alert('Hello!');</script><h1>conteudo</h1>");
		Assert.assertEquals("<h3>titulo</h3>", novoPost.getTitulo());
		Assert.assertEquals("<h1>conteudo</h1>", novoPost.getConteudo());
	}

	@Test
	public void testaConstrutorComEntity() {
		Post postTemporario = new Post("usuario", "titulo", "Conteudo de teste.");
		postTemporario.curtir("dextranet");

		Entity postEntity = postTemporario.toEntity();
		Post post = new Post(postEntity);

		Assert.assertEquals(postEntity.getProperty(PostFields.id.name()), post.getId());
		Assert.assertEquals(postEntity.getProperty(PostFields.titulo.name()), post.getTitulo());
		Assert.assertEquals(((Text) postEntity.getProperty(PostFields.conteudo.name())).getValue(), post.getConteudo());
		Assert.assertEquals(postEntity.getProperty(PostFields.quantidadeDeCurtidas.name()),
				post.getQuantidadeDeCurtidas());
		Assert.assertEquals(postEntity.getProperty(PostFields.usuario.name()), post.getUsuario());
		Assert.assertEquals(postEntity.getProperty(PostFields.usuariosQueCurtiram.name()),
				post.getUsuariosQueCurtiram());
		Assert.assertEquals(postEntity.getProperty(PostFields.usuarioMD5.name()), post.getUsuarioMD5());
		Assert.assertEquals(postEntity.getProperty(PostFields.dataDeCriacao.name()), post.getDataDeCriacao());
		Assert.assertEquals(postEntity.getProperty(PostFields.dataDeAtualizacao.name()), post.getDataDeAtualizacao());
	}

	@Test
	public void testeToEntity() {
		Post post = new Post("usuario", "titulo", "Conteudo de teste.");
		post.curtir("dextranet");

		Entity postEntity = post.toEntity();

		Assert.assertEquals(post.getId(), postEntity.getProperty(PostFields.id.name()));
		Assert.assertEquals(post.getTitulo(), postEntity.getProperty(PostFields.titulo.name()));
		Assert.assertEquals(post.getConteudo(), ((Text) postEntity.getProperty(PostFields.conteudo.name())).getValue());
		Assert.assertEquals(post.getQuantidadeDeCurtidas(),
				postEntity.getProperty(PostFields.quantidadeDeCurtidas.name()));
		Assert.assertEquals(post.getUsuario(), postEntity.getProperty(PostFields.usuario.name()));
		Assert.assertEquals(post.getUsuariosQueCurtiram(),
				postEntity.getProperty(PostFields.usuariosQueCurtiram.name()));
		Assert.assertEquals(post.getUsuarioMD5(), postEntity.getProperty(PostFields.usuarioMD5.name()));
		Assert.assertEquals(post.getDataDeCriacao(), postEntity.getProperty(PostFields.dataDeCriacao.name()));
		Assert.assertEquals(post.getDataDeAtualizacao(), postEntity.getProperty(PostFields.dataDeAtualizacao.name()));
	}

	@Test
	public void testeCurtida() {
		Post post = new Post("usuario", "titulo", "Conteudo de teste");

		Curtida curtida = post.curtir("dextranet");
		Assert.assertEquals(1, post.getQuantidadeDeCurtidas());
		Assert.assertTrue(post.usuarioJaCurtiu("dextranet"));
		Assert.assertTrue(post.getUsuariosQueCurtiram().contains("dextranet"));
		Assert.assertEquals(post.getId(), curtida.getConteudoId());

		post.curtir("outro-usuario");
		Assert.assertEquals(2, post.getQuantidadeDeCurtidas());
		Assert.assertTrue(post.usuarioJaCurtiu("outro-usuario"));
		Assert.assertTrue(post.getUsuariosQueCurtiram().contains("outro-usuario"));
	}

	@Test
	public void testeCurtidaDuplicada() {
		Post post = new Post("usuario", "titulo", "Conteudo de teste");

		post.curtir("dextranet");
		Assert.assertEquals(1, post.getQuantidadeDeCurtidas());

		post.curtir("dextranet");
		Assert.assertEquals(1, post.getQuantidadeDeCurtidas());
	}

	@Test
	public void testeDescurtida() {
		Post post = new Post("usuario", "titulo", "Conteudo de teste");

		post.curtir("dextranet");
		post.curtir("outro-usuario");
		Assert.assertEquals(2, post.getQuantidadeDeCurtidas());

		post.descurtir("dextranet");
		Assert.assertEquals(1, post.getQuantidadeDeCurtidas());
		Assert.assertFalse(post.getUsuariosQueCurtiram().contains("dextranet"));

		post.descurtir("dextranet");
		Assert.assertEquals(1, post.getQuantidadeDeCurtidas());
	}

	@Test
	public void testeComentario() {
		Post post = new Post("usuario", "titulo", "Conteudo de teste");

		Comentario comentario = post.comentar("dextranet", "<script>alert('Hello!');</script>meu comentario");
		Assert.assertEquals(1, post.getQuantidadeDeComentarios());
		Assert.assertEquals(post.getId(), comentario.getPostId());
		Assert.assertEquals("meu comentario", comentario.getConteudo());

		post.comentar("outro-usuario", "mais um comentario");
		Assert.assertEquals(2, post.getQuantidadeDeComentarios());
	}


	@Test
	public void testaRegistraDataMigracao() {
		Date cincoDiasAtras = timeMachine.diasParaAtras(5);
		Post post = new Post("usuario", "titulo", "Conteudo de teste");
		post.registraDataDeMigracao(cincoDiasAtras);

		String cincoDiasAtrasFormat = timeMachine.formataData(cincoDiasAtras);
		String dataCriacaoFormat = timeMachine.formataData(post.getDataDeCriacao());
		Assert.assertEquals(cincoDiasAtrasFormat, dataCriacaoFormat);
	}

}
