package br.com.dextra.dextranet.grupo.servico.google;

import gapi.GoogleAPI;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import br.com.dextra.dextranet.seguranca.AutenticacaoService;

import com.google.api.services.admin.directory.DirectoryScopes;
import com.google.api.services.admin.directory.model.Group;
import com.google.api.services.admin.directory.model.Member;

public class Aprovisionamento {
	private GoogleKeyRepository repo;
	List<GoogleKey> googleKey;
	String key;
	private GoogleAPI googleAPI;
	
	public Aprovisionamento() {
		repo = new GoogleKeyRepository();
		googleKey = repo.lista();
		if (googleKey != null && !googleKey.isEmpty()) {
			key = googleKey.get(0).getKey().toString();
		}
		googleAPI = new GoogleAPI(Arrays.asList(DirectoryScopes.ADMIN_DIRECTORY_GROUP));
	}
	
	public Group criarGrupo(String nome, String email, String descricao) throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = new Group();
		grupo.setName(nome);
		grupo.setEmail(email);
		grupo.setDescription(descricao);
		return googleAPI.group().create(grupo);
	}
	
	public Group criarGrupo(String nome, String emailGrupo, String descricao, List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		Group grupo = criarGrupo(nome, emailGrupo, descricao);

		for (String email : emailMembros) {
			Member membro = new Member();
			membro.setEmail(email);
			googleAPI.group().addMemberGroup(grupo, membro); 
		}
		
		return grupo;
	}
	
	public void removerMembrosGrupo(String emailGrupo, List<String> emailMembros) throws IOException, GeneralSecurityException, URISyntaxException {
		Group group = new Group();
		group.setEmail(emailGrupo);
		
		for (String email : emailMembros) {
			googleAPI.group().deleteMemberGroup(group, email);
		}
	}
	
	
	public GoogleAPI googleAPI() {
		return googleAPI;
	}
	
	public void doPost(String acao, String nomeEmail, String grupoEmail, List<String> emails) throws IOException {
		URL url = new URL(
				"https://script.google.com/macros/s/AKfycbxG9oiWD1a4TvGuJg0QxTCs8FrazbUx8jga1gBZJUnsjSEU6wA/exec?");
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setDoInput(true);
		urlConn.setDoOutput(true);
		urlConn.setUseCaches(false);
		urlConn.setRequestMethod("POST");
		urlConn.setReadTimeout(0);
		urlConn.connect();

		DataOutputStream output = new DataOutputStream(urlConn.getOutputStream());
		String listString = "";
		if (emails != null) {
			for (String s : emails) {
				listString += s + ",";
			}
			listString = listString.substring(0, listString.length() - 1);
		}

		String content = "key=" + URLEncoder.encode(key, "UTF-8") + "&funcao=" + URLEncoder.encode(acao, "UTF-8")
				+ "&emailgrupo=" + URLEncoder.encode(grupoEmail, "UTF-8") + "&nomegrupo="
				+ URLEncoder.encode(nomeEmail, "UTF-8") + "&emailmembro=" + URLEncoder.encode(listString, "UTF-8")
				+ "&autorRequisicao=" + URLEncoder.encode(obtemUsuarioLogado(), "UTF-8");

		output.writeBytes(content);
		output.flush();
		output.close();

		DataInputStream input = new DataInputStream(urlConn.getInputStream());
		input.close();
	}

	public String doGet(String acao, String email) throws IOException {
		HttpURLConnection connection = null;
		BufferedReader rd = null;
		StringBuilder sb = null;
		String line = null;
		URL serverAddress = null;
		serverAddress = new URL(
				"https://script.google.com/macros/s/AKfycbxG9oiWD1a4TvGuJg0QxTCs8FrazbUx8jga1gBZJUnsjSEU6wA/exec?key="
						+ key + "&funcao=" + acao + "&emailgrupo=" + email + "&autorRequisicao=" + obtemUsuarioLogado());
		connection = null;
		connection = (HttpURLConnection) serverAddress.openConnection();
		connection.setRequestMethod("GET");
		connection.setDoOutput(true);
		connection.setReadTimeout(0);
		connection.connect();
		rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		sb = new StringBuilder();

		while ((line = rd.readLine()) != null) {
			sb.append(line + '\n');
		}

		String result = sb.toString();
		connection.disconnect();
		rd = null;
		sb = null;
		connection = null;
		return result;
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