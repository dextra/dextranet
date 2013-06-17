package br.com.dextra.dextranet.usuario;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

public class UsuarioTest extends TesteIntegracaoBase {

	@Test
	public void testaCriacaoHash() {
		Usuario novoUsuario = new Usuario("dextranet");
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoUsuario.getMD5());
	}

	@Test
	public void testeConstrutor() {
		Entity usuarioEntity = new Usuario("dextranet").preenchePerfil("Dextranet Reload", "DxNet", "DEV", "CPS",
				"229", "(19) 3256-6722", "(19) 9784-4510", "dxnet", "skypeDxNet", "myblog").toEntity();
		Usuario usuario = new Usuario(usuarioEntity);

		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.id.name()), usuario.getId());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.username.name()), usuario.getUsername());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.md5.name()), usuario.getMD5());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.nome.name()), usuario.getNome());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.apelido.name()), usuario.getApelido());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.area.name()), usuario.getArea());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.unidade.name()), usuario.getUnidade());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.ramal.name()), usuario.getRamal());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.telefoneResidencial.name()), usuario.getTelefoneResidencial());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.telefoneCelular.name()), usuario.getTelefoneCelular());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.gitHub.name()), usuario.getGitHub());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.skype.name()), usuario.getSkype());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.blog.name()), usuario.getBlog());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.ultimaAtualizacao.name()),
				usuario.getUltimaAtualizacao());
	}

	@Test
	public void testeToEntity() {
		Usuario usuario = new Usuario("dextranet").preenchePerfil("Dextranet Reload", "DxNet", "DEV", "CPS", "229",
				"(19) 3256-6722", "(19) 9784-4510", "dxnet", "skypeDxNet", "myblog");
		Entity usuarioEntity = usuario.toEntity();

		Assert.assertEquals(usuario.getId(), usuarioEntity.getProperty(UsuarioFields.id.name()));
		Assert.assertEquals(usuario.getUsername(), usuarioEntity.getProperty(UsuarioFields.username.name()));
		Assert.assertEquals(usuario.getMD5(), usuarioEntity.getProperty(UsuarioFields.md5.name()));
		Assert.assertEquals(usuario.getNome(), usuarioEntity.getProperty(UsuarioFields.nome.name()));
		Assert.assertEquals(usuario.getApelido(), usuarioEntity.getProperty(UsuarioFields.apelido.name()));
		Assert.assertEquals(usuario.getArea(), usuarioEntity.getProperty(UsuarioFields.area.name()));
		Assert.assertEquals(usuario.getUnidade(), usuarioEntity.getProperty(UsuarioFields.unidade.name()));
		Assert.assertEquals(usuario.getRamal(), usuarioEntity.getProperty(UsuarioFields.ramal.name()));
		Assert.assertEquals(usuario.getTelefoneResidencial(), usuarioEntity.getProperty(UsuarioFields.telefoneResidencial.name()));
		Assert.assertEquals(usuario.getTelefoneCelular(), usuarioEntity.getProperty(UsuarioFields.telefoneCelular.name()));
		Assert.assertEquals(usuario.getGitHub(), usuarioEntity.getProperty(UsuarioFields.gitHub.name()));
		Assert.assertEquals(usuario.getSkype(), usuarioEntity.getProperty(UsuarioFields.skype.name()));
		Assert.assertEquals(usuario.getBlog(), usuarioEntity.getProperty(UsuarioFields.blog.name()));
		Assert.assertNotNull(usuario.getUltimaAtualizacao());
		Assert.assertEquals(usuario.getUltimaAtualizacao(),
				usuarioEntity.getProperty(UsuarioFields.ultimaAtualizacao.name()));
	}

}
