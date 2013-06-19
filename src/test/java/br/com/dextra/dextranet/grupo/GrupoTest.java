package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.grupos.Grupo;
import br.com.dextra.dextranet.grupos.GrupoFields;
import br.com.dextra.dextranet.grupos.Membro;
import br.com.dextra.dextranet.grupos.MembroFields;

import com.google.appengine.api.datastore.Entity;

public class GrupoTest {

	private String nome = "Grupo 1";
	private String descricao = "Descricao do Grupo 1";
	private String proprietario = "login.google";
	private String idUsuario = "67b3f88f-434c-4422-8fff-6c7e5d7acbc6";

	@Test
	public void testaConstrutor() {
		ArrayList<Membro> membros = new ArrayList<Membro>();
		Grupo grupo = new Grupo(nome, descricao, proprietario);
		Membro membro = new Membro(idUsuario, grupo.getId());
		membros.add(membro);

		grupo.setMembros(membros);
		Assert.assertEquals(nome, grupo.getNome());
		Assert.assertEquals(descricao, grupo.getDescricao());
		Assert.assertEquals(proprietario, grupo.getProprietario());
		Assert.assertEquals(nome, grupo.getNome());
		Assert.assertEquals(idUsuario, grupo.getMembros().get(0).getIdUsuario());
		Assert.assertEquals(grupo.getId(), grupo.getMembros().get(0).getIdGrupo());
	}

	@Test
	public void testaConstrutorEntity() {
		ArrayList<Membro> membros = new ArrayList<Membro>();
		Grupo grupo = new Grupo(nome, descricao, proprietario);
		Membro membro = new Membro(idUsuario, grupo.getId());
		membros.add(membro);
		grupo.setMembros(membros);

		Entity entity = grupo.toEntity();
		Assert.assertEquals(grupo.getId(), entity.getProperty(GrupoFields.id.name()));
		Assert.assertEquals(grupo.getNome(), entity.getProperty(GrupoFields.nome.name()));
		Assert.assertEquals(grupo.getDescricao(), entity.getProperty(GrupoFields.descricao.name()));
		Assert.assertEquals(grupo.getProprietario(), entity.getProperty(GrupoFields.proprietario.name()));
		Assert.assertEquals(grupo.getMembros().get(0).getIdUsuario(), entity.getProperty(MembroFields.idUsuario.name()));
		Assert.assertEquals(grupo.getMembros().get(0).getIdGrupo(), entity.getProperty(MembroFields.idGrupo.name()));
	}
}
