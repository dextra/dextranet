package br.com.dextra.dextranet.conteudo.post.comentario;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.conteudo.post.comentario.Comentario;
import br.com.dextra.dextranet.conteudo.post.comentario.ComentarioFields;
import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

// a heranca eh para poder testar os objetos envolvendo entity
public class ComentarioTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaConstrutor() {
		Comentario novoComentario = new Comentario("post-id", "dextranet", "<script>alert('Hello!');</script><h1>conteudo</h1>");
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoComentario.getUsuarioMD5());
		Assert.assertEquals(0, novoComentario.getQuantidadeDeCurtidas());
		Assert.assertEquals(timeMachine.formataData(timeMachine.dataAtual()), timeMachine.formataData(novoComentario.getDataDeCriacao()));
		Assert.assertEquals("post-id", novoComentario.getPostId());
		Assert.assertEquals("<h1>conteudo</h1>", novoComentario.getConteudo());
	}

	@Test
	public void testaConstrutorComEntity() {
		Comentario comentarioTemporario = new Comentario("post-id", "usuario", "conteudo");

		Entity comentarioEntity = comentarioTemporario.toEntity();
		Comentario comentario = new Comentario(comentarioEntity);

		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.id.toString()), comentario.getId());
		Assert.assertEquals(((Text) comentarioEntity.getProperty(ComentarioFields.conteudo.toString())).getValue(), comentario.getConteudo());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.quantidadeDeCurtidas.toString()), comentario.getQuantidadeDeCurtidas());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.usuario.toString()), comentario.getUsuario());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.usuariosQueCurtiram.toString()), comentario.getUsuariosQueCurtiram());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.usuarioMD5.toString()), comentario.getUsuarioMD5());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.dataDeCriacao.toString()), comentario.getDataDeCriacao());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.postId.toString()), comentario.getPostId());
	}

	@Test
	public void testeToEntity() {
		Comentario comentario = new Comentario("post-id", "usuario", "conteudo");

		Entity comentarioEntity = comentario.toEntity();

		Assert.assertEquals(comentario.getId(), comentarioEntity.getProperty(ComentarioFields.id.toString()));
		Assert.assertEquals(comentario.getConteudo(), ((Text) comentarioEntity.getProperty(ComentarioFields.conteudo.toString())).getValue());
		Assert.assertEquals(comentario.getQuantidadeDeCurtidas(), comentarioEntity.getProperty(ComentarioFields.quantidadeDeCurtidas.toString()));
		Assert.assertEquals(comentario.getUsuario(), comentarioEntity.getProperty(ComentarioFields.usuario.toString()));
		Assert.assertEquals(comentario.getUsuariosQueCurtiram(), comentarioEntity.getProperty(ComentarioFields.usuariosQueCurtiram.toString()));
		Assert.assertEquals(comentario.getUsuarioMD5(), comentarioEntity.getProperty(ComentarioFields.usuarioMD5.toString()));
		Assert.assertEquals(comentario.getDataDeCriacao(), comentarioEntity.getProperty(ComentarioFields.dataDeCriacao.toString()));
		Assert.assertEquals(comentario.getPostId(), comentarioEntity.getProperty(ComentarioFields.postId.toString()));
	}

}
