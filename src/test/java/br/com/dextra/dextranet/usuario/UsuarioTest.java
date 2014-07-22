package br.com.dextra.dextranet.usuario;

import static junit.framework.Assert.*;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

public class UsuarioTest extends TesteIntegracaoBase {

	@Test
	public void testaCriacaoHash() {
		Usuario novoUsuario = new Usuario("dextranet");
		assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoUsuario.getMD5());
	}

	@Test
	public void testeConstrutor() {
		Entity usuarioEntity = new Usuario("dextranet").preenchePerfil("Dextranet Reload", "DxNet", "DEV", "CPS",
				"229", "(19) 3256-6722", "(19) 9784-4510", "dxnet", "skypeDxNet", "myblog").toEntity();
		Usuario usuario = new Usuario(usuarioEntity);

		assertEquals(usuarioEntity.getProperty(UsuarioFields.id.name()), usuario.getId());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.username.name()), usuario.getUsername());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.md5.name()), usuario.getMD5());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.nome.name()), usuario.getNome());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.apelido.name()), usuario.getApelido());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.area.name()), usuario.getArea());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.unidade.name()), usuario.getUnidade());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.ramal.name()), usuario.getRamal());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.telefoneResidencial.name()), usuario.getTelefoneResidencial());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.telefoneCelular.name()), usuario.getTelefoneCelular());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.gitHub.name()), usuario.getGitHub());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.skype.name()), usuario.getSkype());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.blog.name()), usuario.getBlog());
		assertEquals(usuarioEntity.getProperty(UsuarioFields.ultimaAtualizacao.name()),
				usuario.getUltimaAtualizacao());
	}

	@Test
	public void testeToEntity() {
		Usuario usuario = new Usuario("dextranet").preenchePerfil("Dextranet Reload", "DxNet", "DEV", "CPS", "229",
				"(19) 3256-6722", "(19) 9784-4510", "dxnet", "skypeDxNet", "myblog");
		Entity usuarioEntity = usuario.toEntity();

		assertEquals(usuario.getId(), usuarioEntity.getProperty(UsuarioFields.id.name()));
		assertEquals(usuario.getUsername(), usuarioEntity.getProperty(UsuarioFields.username.name()));
		assertEquals(usuario.getMD5(), usuarioEntity.getProperty(UsuarioFields.md5.name()));
		assertEquals(usuario.getNome(), usuarioEntity.getProperty(UsuarioFields.nome.name()));
		assertEquals(usuario.getApelido(), usuarioEntity.getProperty(UsuarioFields.apelido.name()));
		assertEquals(usuario.getArea(), usuarioEntity.getProperty(UsuarioFields.area.name()));
		assertEquals(usuario.getUnidade(), usuarioEntity.getProperty(UsuarioFields.unidade.name()));
		assertEquals(usuario.getRamal(), usuarioEntity.getProperty(UsuarioFields.ramal.name()));
		assertEquals(usuario.getTelefoneResidencial(), usuarioEntity.getProperty(UsuarioFields.telefoneResidencial.name()));
		assertEquals(usuario.getTelefoneCelular(), usuarioEntity.getProperty(UsuarioFields.telefoneCelular.name()));
		assertEquals(usuario.getGitHub(), usuarioEntity.getProperty(UsuarioFields.gitHub.name()));
		assertEquals(usuario.getSkype(), usuarioEntity.getProperty(UsuarioFields.skype.name()));
		assertEquals(usuario.getBlog(), usuarioEntity.getProperty(UsuarioFields.blog.name()));
		assertNotNull(usuario.getUltimaAtualizacao());
		assertEquals(usuario.getUltimaAtualizacao(),
				usuarioEntity.getProperty(UsuarioFields.ultimaAtualizacao.name()));
		assertEquals(usuario.isAtivo(), usuarioEntity.getProperty(UsuarioFields.ativo.name()));
	}

}
