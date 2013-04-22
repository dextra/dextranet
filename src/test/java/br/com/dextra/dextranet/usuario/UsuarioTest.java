package br.com.dextra.dextranet.usuario;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

public class UsuarioTest extends TesteIntegracaoBase {

	@Test
	public void testaCriacaoHash() {
		Usuario novoUsuario = new Usuario("dextranet");
		Assert.assertEquals("dextranet@dextra-sw.com", novoUsuario.getEmail());
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", novoUsuario.getMD5());
	}

	@Test
	public void testeConstrutor() {
		Entity usuarioEntity = new Usuario("dextranet").preenchePerfil("Dextranet Reload", "DxNet", "DEV", "CPS",
				"229", "(19) 3256-6722", "(19) 9784-4510", "dxnet", "skypeDxNet").toEntity();
		Usuario usuario = new Usuario(usuarioEntity);

		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.id.toString()), usuario.getId());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.username.toString()), usuario.getUsername());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.md5.toString()), usuario.getMD5());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.nome.toString()), usuario.getNome());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.apelido.toString()), usuario.getApelido());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.area.toString()), usuario.getArea());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.unidade.toString()), usuario.getUnidade());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.ramal.toString()), usuario.getRamal());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.telefoneResidencial.toString()),
				usuario.getTelefoneResidencial());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.telefoneCelular.toString()),
				usuario.getTelefoneCelular());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.gitHub.toString()), usuario.getGitHub());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.skype.toString()), usuario.getSkype());
		Assert.assertEquals(usuarioEntity.getProperty(UsuarioFields.ultimaAtualizacao.toString()),
				usuario.getUltimaAtualizacao());
	}

	@Test
	public void testeToEntity() {
		Usuario usuario = new Usuario("dextranet").preenchePerfil("Dextranet Reload", "DxNet", "DEV", "CPS", "229",
				"(19) 3256-6722", "(19) 9784-4510", "dxnet", "skypeDxNet");
		Entity usuarioEntity = usuario.toEntity();

		Assert.assertEquals(usuario.getId(), usuarioEntity.getProperty(UsuarioFields.id.toString()));
		Assert.assertEquals(usuario.getUsername(), usuarioEntity.getProperty(UsuarioFields.username.toString()));
		Assert.assertEquals(usuario.getMD5(), usuarioEntity.getProperty(UsuarioFields.md5.toString()));
		Assert.assertEquals(usuario.getNome(), usuarioEntity.getProperty(UsuarioFields.nome.toString()));
		Assert.assertEquals(usuario.getApelido(), usuarioEntity.getProperty(UsuarioFields.apelido.toString()));
		Assert.assertEquals(usuario.getArea(), usuarioEntity.getProperty(UsuarioFields.area.toString()));
		Assert.assertEquals(usuario.getUnidade(), usuarioEntity.getProperty(UsuarioFields.unidade.toString()));
		Assert.assertEquals(usuario.getRamal(), usuarioEntity.getProperty(UsuarioFields.ramal.toString()));
		Assert.assertEquals(usuario.getTelefoneResidencial(),
				usuarioEntity.getProperty(UsuarioFields.telefoneResidencial.toString()));
		Assert.assertEquals(usuario.getTelefoneCelular(),
				usuarioEntity.getProperty(UsuarioFields.telefoneCelular.toString()));
		Assert.assertEquals(usuario.getGitHub(), usuarioEntity.getProperty(UsuarioFields.gitHub.toString()));
		Assert.assertEquals(usuario.getSkype(), usuarioEntity.getProperty(UsuarioFields.skype.toString()));
		Assert.assertNotNull(usuario.getUltimaAtualizacao());
		Assert.assertEquals(usuario.getUltimaAtualizacao(),
				usuarioEntity.getProperty(UsuarioFields.ultimaAtualizacao.toString()));
	}

}
