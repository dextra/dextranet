package br.com.dextra.dextranet.conteudo.post.comentario;

import java.util.Date;

import org.junit.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;

// a heranca eh para poder testar os objetos envolvendo entity
public class ComentarioTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaConstrutor() {
		Date dataAtual = timeMachine.dataAtual();
		Comentario novoComentario = new Comentario("post-id", "dextranet", "<script>alert('Hello!');</script><h1>conteudo</h1>");
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoComentario.getUsuarioMD5());
		Assert.assertEquals(0, novoComentario.getQuantidadeDeCurtidas());
		Assert.assertEquals(timeMachine.formataData(dataAtual), timeMachine.formataData(novoComentario.getDataDeCriacao()));
		Assert.assertEquals("post-id", novoComentario.getPostId());
		Assert.assertEquals("<h1>conteudo</h1>", novoComentario.getConteudo());
	}

	@Test
	public void testaConstrutorComEntity() {
		Comentario comentarioTemporario = new Comentario("post-id", "usuario", "conteudo");

		Entity comentarioEntity = comentarioTemporario.toEntity();
		Comentario comentario = new Comentario(comentarioEntity);

		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.id.name()), comentario.getId());
		Assert.assertEquals(((Text) comentarioEntity.getProperty(ComentarioFields.conteudo.name())).getValue(),
				comentario.getConteudo());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.quantidadeDeCurtidas.name()),
				comentario.getQuantidadeDeCurtidas());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.usuario.name()), comentario.getUsuario());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.usuariosQueCurtiram.name()),
				comentario.getUsuariosQueCurtiram());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.usuarioMD5.name()),
				comentario.getUsuarioMD5());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.dataDeCriacao.name()),
				comentario.getDataDeCriacao());
		Assert.assertEquals(comentarioEntity.getProperty(ComentarioFields.postId.name()), comentario.getPostId());
	}

	@Test
	public void testeToEntity() {
		Comentario comentario = new Comentario("post-id", "usuario", "conteudo");

		Entity comentarioEntity = comentario.toEntity();

		Assert.assertEquals(comentario.getId(), comentarioEntity.getProperty(ComentarioFields.id.name()));
		Assert.assertEquals(comentario.getConteudo(),
				((Text) comentarioEntity.getProperty(ComentarioFields.conteudo.name())).getValue());
		Assert.assertEquals(comentario.getQuantidadeDeCurtidas(),
				comentarioEntity.getProperty(ComentarioFields.quantidadeDeCurtidas.name()));
		Assert.assertEquals(comentario.getUsuario(), comentarioEntity.getProperty(ComentarioFields.usuario.name()));
		Assert.assertEquals(comentario.getUsuariosQueCurtiram(),
				comentarioEntity.getProperty(ComentarioFields.usuariosQueCurtiram.name()));
		Assert.assertEquals(comentario.getUsuarioMD5(),
				comentarioEntity.getProperty(ComentarioFields.usuarioMD5.name()));
		Assert.assertEquals(comentario.getDataDeCriacao(),
				comentarioEntity.getProperty(ComentarioFields.dataDeCriacao.name()));
		Assert.assertEquals(comentario.getPostId(), comentarioEntity.getProperty(ComentarioFields.postId.name()));
	}

}
