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
	
	private GoogleAPI googleAPI;
	private Group group;
	
	public Aprovisionamento() {
		googleAPI = new GoogleAPI();
	}
	
	public GoogleAPI googleAPI() {
		return googleAPI;
	}

	public Aprovisionamento criarGrupo(String nome, String emailgrupo, String descricao) throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = new Group();
		grupo.setName(nome);
		grupo.setEmail(emailgrupo);
		grupo.setDescription(descricao);
		group = googleAPI.directory().create(grupo);
		return this;
	}
	
	public void eAdicionarMembros(List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		for (String email : emailMembros) {
			Member membro = new Member();
			membro.setEmail(email);
			googleAPI.directory().addMemberGroup(group, membro);
		}
	}

	public void removerGrupo(String emailGrupo) throws IOException, GeneralSecurityException, URISyntaxException {
			googleAPI.directory().delete(emailGrupo);
	}
	
	public Aprovisionamento obterGrupo(String emailGrupo) throws IOException, GeneralSecurityException, URISyntaxException {
		group = googleAPI.directory().getGroup(emailGrupo);
		
		return this;
	}

	public void eRemoverMembros(List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		for (String email : emailMembros) {
			googleAPI.directory().deleteMemberGroup(group, email);
		}
	}
	
	protected String obtemUsuarioLogado() {
		return AutenticacaoService.identificacaoDoUsuarioLogado();
	}

}