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
		String emailRodrigo = "rodrigo.magalhaes@dextra-sw.com";
		String emailRafael = "rafael.mantellatto@dextra-sw.com";
		
		List<String> emailMembros = Arrays.asList(emailRodrigo, emailRafael);

		Group grupoGoogle = aprovisionamento.criarGrupo(nomeGrupo, emailGrupo, descricaoGrupo, emailMembros);
		Members membrosGoogle = aprovisionamento.googleAPI().group().getMembersGroup(grupoGoogle);
		
		List<Member> membrosSearch = membrosGoogle.getMembers();
		assertTrue(membrosContains(membrosSearch, emailRodrigo));
		assertTrue(membrosContains(membrosSearch, emailRafael));
	}

	public void testarExcluirMembrosDoGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		List<String> emailMembros = Arrays.asList("rodrigo.magalhaes@dextra-sw.com", "rafael.mantellatto@dextra-sw.com");
		Group group = criarGrupoComMembros(emailGrupo, emailMembros);
		
		aprovisionamento.removerMembrosGrupoGoogle(emailGrupo, Arrays.asList("rodrigo.magalhaes@dextra-sw.com"));
		
		List<Member> members = aprovisionamento.googleAPI().group().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 1);
		assertEquals("rafael.mantellatto@dextra-sw.com", members.get(0));
		
	}

	@Test
	public void testaRemoverMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		Group group = criarGrupoGoogle(emailGrupo);
		String rafael = "rafael.mantellatto@dextra-sw.com";
		String rodrigo = "rodrigo.magalhaes@dextra-sw.com";
		List<String> emailMembros = Arrays.asList(rafael, rodrigo);
        adicionarMembroGrupoGoogle(emailMembros, emailGrupo);
		
		aprovisionamento.removerMembrosGrupoGoogle(emailGrupo, Arrays.asList(rafael));
		List<Member> members = aprovisionamento.googleAPI().group().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 1);
		assertEquals(rodrigo, members.get(0).getEmail());
	}
	
	@Test
	public void testaAdicionarMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		criarGrupoGoogle(emailGrupo);
		String rafael = "rafael.mantellatto@dextra-sw.com";
		String rodrigo = "rodrigo.magalhaes@dextra-sw.com";
		aprovisionamento.adicionarMembrosGrupo(emailGrupo, Arrays.asList(rafael, rodrigo));
		Group group = aprovisionamento.googleAPI().group().getGroup(emailGrupo);
		List<Member> members = aprovisionamento.googleAPI().group().getMembersGroup(group).getMembers();
		assertTrue(members.size() == 2);
		assertTrue(membrosContains(members, rodrigo));
		assertTrue(membrosContains(members, rafael));
	}

	private Boolean membrosContains(List<Member> membros, String emailRodrigo) {
		for (Member member : membros) {
			if (member.getEmail().equals(emailRodrigo)) {
				return true;
			}
		}
		return false;
	}

	private Group criarGrupoComMembros(String emailGrupo, List<String> emailsMembros)
			throws IOException, GeneralSecurityException, URISyntaxException {
		GoogleAPI googleAPI = aprovisionamento.googleAPI();
		Group group = criarGrupoGoogle(emailGrupo);
		
		String emailRodrigo = "rodrigo.magalhaes@dextra-sw.com";
		String emailRafael = "rafael.mantellatto@dextra-sw.com";
		
		Member membroRodrigo = new Member();
		membroRodrigo.setEmail(emailRodrigo);
		Member membroRafael = new Member();
		membroRafael.setEmail(emailRafael);
		
		List<Member> membrosAdd = Arrays.asList(membroRodrigo, membroRafael);
		for (Member membro : membrosAdd) {
			googleAPI.group().addMemberGroup(group, membro); 
		}
		return group;
	}
}
