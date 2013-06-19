package br.com.dextra.dextranet.grupo;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.grupos.Grupo;
import br.com.dextra.dextranet.grupos.GrupoFields;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

public class GrupoTest extends TesteIntegracaoBase {
	private String nome = "Grupo 1";
	private String descricao = "Descricao do Grupo 1";
	private String proprietario = "login.google";

	@Test
	public void testaConstrutor() {
		Grupo grupo = new Grupo(nome, descricao, proprietario);

		Assert.assertEquals(this.nome, grupo.getNome());
		Assert.assertEquals(this.descricao, grupo.getDescricao());
		Assert.assertEquals(this.proprietario, grupo.getProprietario());
		Assert.assertEquals(this.nome, grupo.getNome());
	}

	@Test
	public void testaConstrutorEntity() {
		Grupo grupo = new Grupo(this.nome, this.descricao, this.proprietario);

		Entity entity = grupo.toEntity();
		Assert.assertEquals(entity.getProperty(GrupoFields.id.name()), grupo.getId());
		Assert.assertEquals(entity.getProperty(GrupoFields.nome.name()), grupo.getNome());
		Assert.assertEquals(entity.getProperty(GrupoFields.descricao.name()), grupo.getDescricao());
		Assert.assertEquals(entity.getProperty(GrupoFields.proprietario.name()), grupo.getProprietario());
	}
}
