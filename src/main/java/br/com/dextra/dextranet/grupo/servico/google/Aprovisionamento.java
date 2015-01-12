package br.com.dextra.dextranet.grupo.servico.google;

import gapi.GoogleAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;

public class Aprovisionamento {

	private GoogleAPI googleAPI = new GoogleAPI();

	public Group criarGrupo(String nome, String emailgrupo, String descricao) throws IOException,
			GeneralSecurityException, URISyntaxException {
		Group grupo = new Group();
		grupo.setName(nome);
		grupo.setEmail(emailgrupo);
		grupo.setDescription(descricao);
		return googleAPI.directory().create(grupo);

	}

	public void adicionarMembros(List<String> emailMembros, Group group) throws IOException, GeneralSecurityException,
			URISyntaxException {
		for (String email : emailMembros) {
			Member membro = new Member();
			membro.setEmail(email);
			googleAPI.directory().addMemberGroup(group, membro);
		}
	}

	public void removerGrupo(String emailGrupo) throws IOException, GeneralSecurityException, URISyntaxException {
		googleAPI.directory().delete(emailGrupo);
	}

	public Group obterGrupo(String emailGrupo) throws IOException, GeneralSecurityException, URISyntaxException {
		return googleAPI.directory().getGroup(emailGrupo);
	}

	public void removerMembros(List<String> emailMembros, Group group) throws IOException, GeneralSecurityException,
			URISyntaxException {
		for (String email : emailMembros) {
			googleAPI.directory().deleteMemberGroup(group, email);
		}
	}

	public List<Group> obterGrupos(String dominio) {
		return googleAPI.directory().getGroupsByDomain(dominio).getGroups();
	}

	public List<Member> obterMembros(Group grupo) {
		return googleAPI.directory().getMembersGroup(grupo).getMembers();
	}

	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}