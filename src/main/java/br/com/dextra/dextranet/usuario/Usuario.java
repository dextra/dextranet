package br.com.dextra.dextranet.usuario;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.MD5;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Usuario extends Entidade {

	private static final String DEFAULT_DOMAIN = "@dextra-sw.com";

	private String username;

	private String md5;

	private String nome;

	private String apelido;

	private String area;

	private String unidade;

	private String ramal;

	private String telefoneResidencial;

	private String telefoneCelular;

	private String gitHub;

	public Usuario(String username) {
		this.username = username.trim();
		this.md5 = MD5.hash(this.getEmail());
	}

	public Usuario(Entity entidade) {
		this.id = (String) entidade.getProperty(UsuarioFields.id.toString());
		this.username = (String) entidade.getProperty(UsuarioFields.username.toString());
		this.md5 = (String) entidade.getProperty(UsuarioFields.md5.toString());
		this.nome = (String) entidade.getProperty(UsuarioFields.nome.toString());
		this.apelido = (String) entidade.getProperty(UsuarioFields.apelido.toString());
		this.area = (String) entidade.getProperty(UsuarioFields.area.toString());
		this.unidade = (String) entidade.getProperty(UsuarioFields.unidade.toString());
		this.ramal = (String) entidade.getProperty(UsuarioFields.ramal.toString());
		this.telefoneResidencial = (String) entidade.getProperty(UsuarioFields.telefoneResidencial.toString());
		this.telefoneCelular = (String) entidade.getProperty(UsuarioFields.telefoneCelular.toString());
		this.gitHub = (String) entidade.getProperty(UsuarioFields.gitHub.toString());
	}

	public String getEmail() {
		return this.username + DEFAULT_DOMAIN;
	}

	public String getUsername() {
		return username;
	}

	public String getMD5() {
		return md5;
	}

	public String getNome() {
		return nome;
	}

	public String getApelido() {
		return apelido;
	}

	public String getArea() {
		return area;
	}

	public String getUnidade() {
		return unidade;
	}

	public String getRamal() {
		return ramal;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public String getGitHub() {
		return gitHub;
	}

	public Usuario preenchePerfil(String nome, String apelido, String area, String unidade, String ramal,
			String telefoneResidencial, String telefoneCelular, String gitHub) {
		this.nome = nome;
		this.apelido = apelido;
		this.area = area;
		this.unidade = unidade;
		this.ramal = ramal;
		this.telefoneResidencial = telefoneResidencial;
		this.telefoneCelular = telefoneCelular;
		this.gitHub = gitHub;

		return this;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(UsuarioFields.id.toString(), this.id);
		entidade.setProperty(UsuarioFields.username.toString(), this.username);
		entidade.setProperty(UsuarioFields.md5.toString(), this.md5);
		entidade.setProperty(UsuarioFields.nome.toString(), this.nome);
		entidade.setProperty(UsuarioFields.apelido.toString(), this.apelido);
		entidade.setProperty(UsuarioFields.area.toString(), this.area);
		entidade.setProperty(UsuarioFields.unidade.toString(), this.unidade);
		entidade.setProperty(UsuarioFields.ramal.toString(), this.ramal);
		entidade.setProperty(UsuarioFields.telefoneResidencial.toString(), this.telefoneResidencial);
		entidade.setProperty(UsuarioFields.telefoneCelular.toString(), this.telefoneCelular);
		entidade.setProperty(UsuarioFields.gitHub.toString(), this.gitHub);

		return entidade;
	}

	@Override
	public JsonObject toJson() {
		throw new UnsupportedOperationException();
	}

}
