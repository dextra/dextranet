package br.com.dextra.dextranet.post;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

//a heranca eh para poder testar os objetos envolvendo entity
public class PostTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaConstrutor() {
		Post novoPost = new Post("dextranet");
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoPost.getUsuarioMD5());
		Assert.assertEquals(0, novoPost.getQuantidadeDeComentarios());
		Assert.assertEquals(0, novoPost.getQuantidadeDeCurtidas());
		Assert.assertEquals(timeMachine.formataData(timeMachine.dataAtual()), timeMachine.formataData(novoPost.getDataDeCriacao()));
	}

	@Test
	public void testaPreenche() {
		Post novoPost = new Post("usuario");
		novoPost.preenche("<script>alert('Hello!');</script><h3>titulo</h3>", "<script>alert('Hello!');</script><h1>conteudo</h1>");
		Assert.assertEquals("<h3>titulo</h3>", novoPost.getTitulo());
		Assert.assertEquals("<h1>conteudo</h1>", novoPost.getConteudo());
	}

	@Test
	public void testaConstrutorComEntity() {
		Post postTemporario = new Post("usuario");
		postTemporario.preenche("titulo", "Conteudo de teste.");

		Entity postEntity = postTemporario.toEntity();
		Post post = new Post(postEntity);

		Assert.assertEquals(postEntity.getProperty(PostFields.id.toString()), post.getId());
		Assert.assertEquals(postEntity.getProperty(PostFields.titulo.toString()), post.getTitulo());
		Assert.assertEquals(((Text) postEntity.getProperty(PostFields.conteudo.toString())).getValue(), post.getConteudo());
		Assert.assertEquals(postEntity.getProperty(PostFields.quantidadeDeCurtidas.toString()), post.getQuantidadeDeCurtidas());
		Assert.assertEquals(postEntity.getProperty(PostFields.quantidadeDeComentarios.toString()), post.getQuantidadeDeComentarios());
		Assert.assertEquals(postEntity.getProperty(PostFields.usuario.toString()), post.getUsuario());
		Assert.assertEquals(postEntity.getProperty(PostFields.usuarioMD5.toString()), post.getUsuarioMD5());
		Assert.assertEquals(postEntity.getProperty(PostFields.dataDeCriacao.toString()), post.getDataDeCriacao());
		Assert.assertEquals(postEntity.getProperty(PostFields.dataDeAtualizacao.toString()), post.getDataDeAtualizacao());
	}

	@Test
	public void testeToEntity() {
		Post post = new Post("usuario");
		post.preenche("titulo", "Conteudo de teste.");

		Entity postEntity = post.toEntity();

		Assert.assertEquals(post.getId(), postEntity.getProperty(PostFields.id.toString()));
		Assert.assertEquals(post.getTitulo(), postEntity.getProperty(PostFields.titulo.toString()));
		Assert.assertEquals(post.getConteudo(), ((Text) postEntity.getProperty(PostFields.conteudo.toString())).getValue());
		Assert.assertEquals(post.getQuantidadeDeCurtidas(), postEntity.getProperty(PostFields.quantidadeDeCurtidas.toString()));
		Assert.assertEquals(post.getQuantidadeDeComentarios(), postEntity.getProperty(PostFields.quantidadeDeComentarios.toString()));
		Assert.assertEquals(post.getUsuario(), postEntity.getProperty(PostFields.usuario.toString()));
		Assert.assertEquals(post.getUsuarioMD5(), postEntity.getProperty(PostFields.usuarioMD5.toString()));
		Assert.assertEquals(post.getDataDeCriacao(), postEntity.getProperty(PostFields.dataDeCriacao.toString()));
		Assert.assertEquals(post.getDataDeAtualizacao(), postEntity.getProperty(PostFields.dataDeAtualizacao.toString()));
	}
}
