package br.com.dextra.dextranet.usuario;

import java.util.Date;
import java.util.List;

import br.com.dextra.dextranet.grupo.GrupoJSON;
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
	private String blog;
	private List<GrupoJSON> grupos;

	public Usuario(String username) {
		this.username = username.trim();
		this.md5 = geraMD5(this.username);
	}

	public Usuario(Entity entidade) {
		this.id = (String) entidade.getProperty(UsuarioFields.id.name());
		this.username = (String) entidade.getProperty(UsuarioFields.username.name());
		this.md5 = (String) entidade.getProperty(UsuarioFields.md5.name());
		this.nome = (String) entidade.getProperty(UsuarioFields.nome.name());
		this.apelido = (String) entidade.getProperty(UsuarioFields.apelido.name());
		this.area = (String) entidade.getProperty(UsuarioFields.area.name());
		this.unidade = (String) entidade.getProperty(UsuarioFields.unidade.name());
		this.ramal = (String) entidade.getProperty(UsuarioFields.ramal.name());
		this.telefoneResidencial = (String) entidade.getProperty(UsuarioFields.telefoneResidencial.name());
		this.telefoneCelular = (String) entidade.getProperty(UsuarioFields.telefoneCelular.name());
		this.gitHub = (String) entidade.getProperty(UsuarioFields.gitHub.name());
		this.skype = (String) entidade.getProperty(UsuarioFields.skype.name());
		this.ultimaAtualizacao = (Date) entidade.getProperty(UsuarioFields.ultimaAtualizacao.name());
		this.blog = (String) entidade.getProperty(UsuarioFields.blog.name());

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

	public String getBlog() {
		return blog;
	}

	public Usuario preenchePerfil(String nome, String apelido, String area, String unidade, String ramal,
			String telefoneResidencial, String telefoneCelular, String gitHub, String skype, String blog) {
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

		conteudoHTML.setConteudo(blog);
		this.blog = conteudoHTML.removeJavaScript();

		return this;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(UsuarioFields.id.name(), this.id);
		entidade.setProperty(UsuarioFields.username.name(), this.username);
		entidade.setProperty(UsuarioFields.md5.name(), this.md5);
		entidade.setProperty(UsuarioFields.nome.name(), this.nome);
		entidade.setProperty(UsuarioFields.apelido.name(), this.apelido);
		entidade.setProperty(UsuarioFields.area.name(), this.area);
		entidade.setProperty(UsuarioFields.unidade.name(), this.unidade);
		entidade.setProperty(UsuarioFields.ramal.name(), this.ramal);
		entidade.setProperty(UsuarioFields.ultimaAtualizacao.name(), this.ultimaAtualizacao);
		entidade.setProperty(UsuarioFields.telefoneResidencial.name(), this.telefoneResidencial);
		entidade.setProperty(UsuarioFields.telefoneCelular.name(), this.telefoneCelular);
		entidade.setProperty(UsuarioFields.skype.name(), this.skype);
		entidade.setProperty(UsuarioFields.gitHub.name(), this.gitHub);
		entidade.setProperty(UsuarioFields.blog.name(), this.blog);

		return entidade;
	}

	public static String geraMD5(String username) {
		return MD5.hash(username + DEFAULT_DOMAIN);
	}

	public List<GrupoJSON> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<GrupoJSON> grupos) {
		this.grupos = grupos;
	}


}
