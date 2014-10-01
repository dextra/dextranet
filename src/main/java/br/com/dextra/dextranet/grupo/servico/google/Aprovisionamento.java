package br.com.dextra.dextranet.grupo.servico.google;

import gapi.GoogleAPI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;

public class Aprovisionamento {
	private GoogleAPI googleAPI;
	private Group group;
	
	public Aprovisionamento() {
		googleAPI = new GoogleAPI(Arrays.asList(DirectoryScopes.ADMIN_DIRECTORY_GROUP));
	}
	
	public GoogleAPI googleAPI() {
		return googleAPI;
	}

	public Aprovisionamento criarGrupo(String nome, String emailgrupo, String descricao) throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = new Group();
		grupo.setName(nome);
		grupo.setEmail(emailgrupo);
		grupo.setDescription(descricao);
		group = googleAPI.group().create(grupo);
		return this;
	}
	
	public void eAdicionarMembros(List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		for (String email : emailMembros) {
			Member membro = new Member();
			membro.setEmail(email);
			googleAPI.group().addMemberGroup(group, membro);
		}
	}

	public void removerGrupo(String emailGrupo) throws IOException, GeneralSecurityException, URISyntaxException {
		try {
			googleAPI.group().delete(emailGrupo);
		} catch(GoogleJsonResponseException e) {
			if (!e.getMessage().contains("404")) {
				throw e;
			}
		}
	}
	
	public Aprovisionamento obterGrupo(String emailGrupo) throws IOException, GeneralSecurityException, URISyntaxException {
		group = googleAPI.group().getGroup(emailGrupo);
		
		return this;
	}

	public void eRemoverMembros(List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		for (String email : emailMembros) {
			googleAPI.group().deleteMemberGroup(group, email);
		}
	}
	
	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}