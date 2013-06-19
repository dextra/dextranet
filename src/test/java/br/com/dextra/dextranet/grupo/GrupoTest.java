package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.grupos.Grupo;
import br.com.dextra.dextranet.grupos.Membro;

public class GrupoTest {

	@Test
	public void testaConstrutor() {
		String nome = "Grupo 1";
		String descricao = "Descricao do Grupo 1";
		String proprietario = "login.google";

		ArrayList<Membro> membros = new ArrayList<Membro>();
		String idUsuario = "67b3f88f-434c-4422-8fff-6c7e5d7acbc6";
		String idGrupo = "67b3f88f-434c-4422-8fff-6c7e5d7a9999";
		Membro membro = new Membro(idUsuario, idGrupo);
		membros.add(membro);

		Grupo grupo = new Grupo(nome, descricao, proprietario, membros);
		Assert.assertEquals(nome, grupo.getNome());
		Assert.assertEquals(descricao, grupo.getDescricao());
		Assert.assertEquals(proprietario, grupo.getProprietario());
		Assert.assertEquals(nome, grupo.getNome());
		Assert.assertEquals(idUsuario, grupo.getMembros().get(0).getIdUsuario());
		Assert.assertEquals(idGrupo, grupo.getMembros().get(0).getIdGrupo());
	}

}
