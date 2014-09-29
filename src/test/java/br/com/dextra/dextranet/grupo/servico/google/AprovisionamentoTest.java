package br.com.dextra.dextranet.grupo.servico.google;

import gapi.GoogleAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static br.com.dextra.dextranet.persistencia.TesteUtils.*;
import br.com.dextra.dextranet.persistencia.TesteUtils;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;
import com.google.api.services.admin.directory.model.Members;

public class AprovisionamentoTest extends TesteIntegracaoBase {
	private Aprovisionamento aprovisionamento = TesteUtils.getAprovisionamento();
	private String emailGrupo = "grupo@dextra-sw.com";
	
	@Before
	@After
	public void initial() throws IOException, GeneralSecurityException, URISyntaxException {
		removerGrupoGoogle(emailGrupo);
	}

	@Test
	public void testaCriarGrupo() throws IOException, GeneralSecurityException, URISyntaxException, InterruptedException {
		String nomeGrupo = "Grupo 1";
		String descricaoGrupo = "Grupo 1";
		
		aprovisionamento.criarGrupoGoogle(nomeGrupo, emailGrupo, descricaoGrupo);
		Group group = aprovisionamento.googleAPI().group().getGroup(emailGrupo);
		assertEquals(emailGrupo, group.getEmail());
	}
	
	@Test
	public void testarCriarGrupoComMembros() throws IOException, GeneralSecurityException, URISyntaxException {
		String nomeGrupo = "Grupo 1";
		String descricaoGrupo = "Grupo 1";
		String emailusuario1 = "usuario.1@dextra-sw.com";
		String emailusuario2 = "usuario.2@dextra-sw.com";
		
		List<String> emailMembros = Arrays.asList(emailusuario1, emailusuario2);

		Group grupoGoogle = aprovisionamento.criarGrupo(nomeGrupo, emailGrupo, descricaoGrupo, emailMembros);
		Members membrosGoogle = aprovisionamento.googleAPI().group().getMembersGroup(grupoGoogle);
		
		List<Member> membrosSearch = membrosGoogle.getMembers();
		assertTrue(membrosContains(membrosSearch, emailusuario1));
		assertTrue(membrosContains(membrosSearch, emailusuario2));
	}

	public void testarExcluirMembrosDoGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		List<String> emailMembros = Arrays.asList("usuario.1@dextra-sw.com", "usuario.2@dextra-sw.com");
		Group group = criarGrupoComMembros(emailGrupo, emailMembros);
		
		aprovisionamento.removerMembrosGrupoGoogle(emailGrupo, Arrays.asList("usuario.1@dextra-sw.com"));
		
		List<Member> members = aprovisionamento.googleAPI().group().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 1);
		assertEquals("usuario.2@dextra-sw.com", members.get(0));
		
	}

	@Test
	public void testaRemoverMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		Group group = criarGrupoGoogle(emailGrupo);
		String usuario1 = "usuario.1@dextra-sw.com";
		String usuario2 = "usuario.2@dextra-sw.com";
		List<String> emailMembros = Arrays.asList(usuario1, usuario2);
        adicionarMembroGrupoGoogle(emailMembros, emailGrupo);
		
		aprovisionamento.removerMembrosGrupoGoogle(emailGrupo, Arrays.asList(usuario1));
		List<Member> members = aprovisionamento.googleAPI().group().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 1);
		assertEquals(usuario2, members.get(0).getEmail());
	}
	
	@Test
	public void testaAdicionarMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		criarGrupoGoogle(emailGrupo);
		String usuario1 = "usuario.1@dextra-sw.com";
		String usuario2 = "usuario.2@dextra-sw.com";
		aprovisionamento.adicionarMembrosGrupo(emailGrupo, Arrays.asList(usuario1, usuario2));
		Group group = aprovisionamento.googleAPI().group().getGroup(emailGrupo);
		List<Member> members = aprovisionamento.googleAPI().group().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 2);
		assertTrue(membrosContains(members, usuario2));
		assertTrue(membrosContains(members, usuario1));
	}

	private Boolean membrosContains(List<Member> membros, String email) {
		for (Member member : membros) {
			if (member.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	private Group criarGrupoComMembros(String emailGrupo, List<String> emailsMembros)
			throws IOException, GeneralSecurityException, URISyntaxException {
		GoogleAPI googleAPI = aprovisionamento.googleAPI();
		Group group = criarGrupoGoogle(emailGrupo);
		
		String usuario1 = "usuario.1@dextra-sw.com";
		String usuario2 = "usuario.2@dextra-sw.com";
		
		Member membro1 = new Member();
		membro1.setEmail(usuario1);
		Member membro2 = new Member();
		membro2.setEmail(usuario2);
		
		List<Member> membrosAdd = Arrays.asList(membro1, membro2);
		for (Member membro : membrosAdd) {
			googleAPI.group().addMemberGroup(group, membro); 
		}
		return group;
	}
}
