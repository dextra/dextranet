package br.com.dextra.dextranet.usuario;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.ConteudoHTML;
import br.com.dextra.dextranet.utils.MD5;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.datastore.Entity;

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

	private String skype;

	private Date ultimaAtualizacao;

	public Usuario(String username) {
		this.username = username.trim();
		this.md5 = geraMD5(this.username);
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
		this.skype = (String) entidade.getProperty(UsuarioFields.skype.toString());
		this.ultimaAtualizacao = (Date) entidade.getProperty(UsuarioFields.ultimaAtualizacao.toString());
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

	public String getSkype() {
		return skype;
	}

	public Date getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public Usuario preenchePerfil(String nome, String apelido, String area, String unidade, String ramal,
			String telefoneResidencial, String telefoneCelular, String gitHub, String skype) {
		ConteudoHTML conteudoHTML = new ConteudoHTML(nome);
		this.nome = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(apelido);
		this.apelido = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(area);
		this.area = conteudoHTML.removeJavaScript();
		
		conteudoHTML.setConteudo(unidade);
		this.unidade = conteudoHTML.removeJavaScript();
		
		conteudoHTML.setConteudo(ramal);
		this.ramal = conteudoHTML.removeJavaScript();
		
		conteudoHTML.setConteudo(telefoneResidencial);
		this.telefoneResidencial = conteudoHTML.removeJavaScript();
		
		conteudoHTML.setConteudo(telefoneCelular);
		this.telefoneCelular = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(gitHub);
		this.gitHub = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(skype);
		this.skype = conteudoHTML.removeJavaScript();
		this.ultimaAtualizacao = new TimeMachine().dataAtual();

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
		entidade.setProperty(UsuarioFields.skype.toString(), this.skype);
		entidade.setProperty(UsuarioFields.ultimaAtualizacao.toString(), this.ultimaAtualizacao);

		return entidade;
	}

	public static String geraMD5(String username) {
		return MD5.hash(username + DEFAULT_DOMAIN);
	}

}
