package br.com.dextra.dextranet.grupo.servico.google;

import static br.com.dextra.dextranet.persistencia.TesteUtils.criarGrupoGoogle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;

public class AprovisionamentoTest extends TesteIntegracaoBase {

	private Aprovisionamento aprovisionamento = new Aprovisionamento();
	private String emailGrupo = "grupo@dextra-sw.com";
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Before
	@After
	public void initial() throws IOException, GeneralSecurityException, URISyntaxException {
		aprovisionamento.removerGrupo(emailGrupo);
	}

	@Test
	public void testaCriarGrupo() throws IOException, GeneralSecurityException, URISyntaxException,
			InterruptedException {
		String nomeGrupo = "Grupo 1";
		String descricaoGrupo = "Grupo 1";

		aprovisionamento.criarGrupo(nomeGrupo, emailGrupo, descricaoGrupo);
		Group group = aprovisionamento.obterGrupo(emailGrupo);
		assertEquals(emailGrupo, group.getEmail());
	}

	@Test
	public void testarCriarGrupoComMembros() throws IOException, GeneralSecurityException, URISyntaxException {
		String nomeGrupo = "Grupo 1";
		String descricaoGrupo = "Grupo 1";
		String emailusuario1 = "usuario.1@dextra-sw.com";
		String emailusuario2 = "usuario.2@dextra-sw.com";

		List<String> emailMembros = Arrays.asList(emailusuario1, emailusuario2);

		Group grupo = aprovisionamento.criarGrupo(nomeGrupo, emailGrupo, descricaoGrupo);
		aprovisionamento.adicionarMembros(emailMembros, grupo);
		Group grupoGoogle = aprovisionamento.obterGrupo(emailGrupo);
		List<Member> membrosSearch = aprovisionamento.obterMembros(grupoGoogle);

		assertTrue(membrosContains(membrosSearch, emailusuario1));
		assertTrue(membrosContains(membrosSearch, emailusuario2));
	}

	public void testarExcluirMembrosDoGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		List<String> emailMembros = Arrays.asList("usuario.1@dextra-sw.com", "usuario.2@dextra-sw.com");
		Group group = criarGrupoComMembros(emailGrupo, emailMembros);

		group = aprovisionamento.obterGrupo(emailGrupo);
		aprovisionamento.removerMembros(Arrays.asList("usuario.1@dextra-sw.com"), group);

		List<Member> members = aprovisionamento.obterMembros(group);
		assertTrue(members.size() == 1);
		assertEquals("usuario.2@dextra-sw.com", members.get(0));

	}

	@Test
	public void testaRemoverMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		String usuario1 = "usuario.1@dextra-sw.com";
		String usuario2 = "usuario.2@dextra-sw.com";
		List<String> emailMembros = Arrays.asList(usuario1, usuario2);
		
		criarGrupoComMembros(emailGrupo, emailMembros);

		Group grupo = aprovisionamento.obterGrupo(emailGrupo);

		aprovisionamento.removerMembros(Arrays.asList(usuario1), grupo);
		List<Member> members = aprovisionamento.obterMembros(grupo);
		assertTrue(members.size() == 1);
		assertEquals(usuario2, members.get(0).getEmail());
	}

	@Test
	public void testaAdicionarMembrosGrupo() throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = criarGrupoGoogle(emailGrupo);
		aprovisionamento.adicionarMembros(Arrays.asList("usuario.1@dextra-sw.com", "usuario.2@dextra-sw.com"), grupo);

		List<Member> members = aprovisionamento.obterMembros(grupo);
		assertTrue(members.size() == 2);
	}
	
	@Test
	public void testAdicionarMembroEmailInvalido() throws IOException, GeneralSecurityException, URISyntaxException {
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("renan.silva() is not a valid email address.");
		
		Group grupo = criarGrupoGoogle(emailGrupo);
		
		aprovisionamento.adicionarMembros(Arrays.asList("renan.silva()"), grupo);
	}

	private Boolean membrosContains(List<Member> membros, String email) {
		for (Member member : membros) {
			if (member.getEmail().equals(email)) {
				return true;
			}
		}
		return false;
	}

	private Group criarGrupoComMembros(String emailGrupo, List<String> emailsMembros) throws IOException,
			GeneralSecurityException, URISyntaxException {
		Group group = criarGrupoGoogle(emailGrupo);

		aprovisionamento.adicionarMembros(emailsMembros, group);

		return group;
	}
	
}
