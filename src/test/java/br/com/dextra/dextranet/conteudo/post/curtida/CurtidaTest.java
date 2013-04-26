package br.com.dextra.dextranet.conteudo.post.curtida;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

//a heranca eh para poder testar os objetos envolvendo entity
public class CurtidaTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaConstrutor() {
		Curtida curtida = new Curtida("post", "dextranet");

		Assert.assertEquals("post", curtida.getConteudoId());
		Assert.assertEquals("dextranet", curtida.getUsuario());
		Assert.assertEquals("39566cf6ac41da40deb7c6452a9ed94b", curtida.getUsuarioMD5());
		Assert.assertEquals(timeMachine.formataData(timeMachine.dataAtual()), timeMachine.formataData(curtida.getData()));
		
	}

	@Test
	public void testaConstrutorComEntity() {
		Curtida curtidaTemporaria = new Curtida("post", "dextranet");

		Entity curtidaEntity = curtidaTemporaria.toEntity();
		Curtida curtida = new Curtida(curtidaEntity);

		Assert.assertEquals(curtidaEntity.getProperty(CurtidaFields.id.name()), curtida.getId());
		Assert.assertEquals(curtidaEntity.getProperty(CurtidaFields.usuario.name()), curtida.getUsuario());
		Assert.assertEquals(curtidaEntity.getProperty(CurtidaFields.usuarioMD5.name()), curtida.getUsuarioMD5());
		Assert.assertEquals(curtidaEntity.getProperty(CurtidaFields.data.name()), curtida.getData());
		Assert.assertEquals(curtidaEntity.getProperty(CurtidaFields.conteudoId.name()), curtida.getConteudoId());
	}

	@Test
	public void testeToEntity() {
		Curtida curtida = new Curtida("post", "dextranet");
		Entity curtidaEntity = curtida.toEntity();

		Assert.assertEquals(curtida.getId(), curtidaEntity.getProperty(CurtidaFields.id.name()));
		Assert.assertEquals(curtida.getUsuario(), curtidaEntity.getProperty(CurtidaFields.usuario.name()));
		Assert.assertEquals(curtida.getUsuarioMD5(), curtidaEntity.getProperty(CurtidaFields.usuarioMD5.name()));
		Assert.assertEquals(curtida.getData(), curtidaEntity.getProperty(CurtidaFields.data.name()));
		Assert.assertEquals(curtida.getConteudoId(), curtidaEntity.getProperty(CurtidaFields.conteudoId.name()));
	}

}
