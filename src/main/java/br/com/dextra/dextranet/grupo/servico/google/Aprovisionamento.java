package br.com.dextra.dextranet.grupo.servico.google;

import gapi.GoogleAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;

public class Aprovisionamento {
	private GoogleAPI googleAPI;
	
	public Aprovisionamento() {
		googleAPI = new GoogleAPI(Arrays.asList(DirectoryScopes.ADMIN_DIRECTORY_GROUP));
	}
	
	public Group criarGrupoGoogle(String nome, String email, String descricao) throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = new Group();
		grupo.setName(nome);
		grupo.setEmail(email);
		grupo.setDescription(descricao);
		return googleAPI.group().create(grupo);
	}
	
	public Group criarGrupo(String nome, String emailGrupo, String descricao, List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = criarGrupoGoogle(nome, emailGrupo, descricao);

		for (String email : emailMembros) {
			Member membro = new Member();
			membro.setEmail(email);
			googleAPI.group().addMemberGroup(grupo, membro); 
		}
		
		return grupo;
	}
	
	public void removerMembrosGrupoGoogle(String emailGrupo, List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		Group group = new Group();
		group.setEmail(emailGrupo);
		
		for (String email : emailMembros) {
			googleAPI.group().deleteMemberGroup(group, email);
		}
	}
	
	public GoogleAPI googleAPI() {
		return googleAPI;
	}
	
	public void adicionarMembrosGrupo(String emailGrupo, List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		Group group = googleAPI.group().getGroup(emailGrupo);
		
		for (String email : emailMembros) {
			Member membro = new Member();
			membro.setEmail(email);
			googleAPI.group().addMemberGroup(group, membro);
		}
	}

	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}