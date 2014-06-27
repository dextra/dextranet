package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;

public class Grupo extends Entidade {
	private String nome;
	private String descricao;
	private String proprietario;
	private List<Membro> membros;
	private List<ServicoGrupo> servicoGrupos;
	private Boolean infra = false;
	
	public Grupo() {
	}

	public Grupo(String nome, String descricao, String proprietario) {
		super();
		preenche(nome, descricao, proprietario);
	}

	public Grupo(Entity entidade) {
		this.id = (String) entidade.getProperty(GrupoFields.id.name());
		this.nome = (String) entidade.getProperty(GrupoFields.nome.name());
		this.descricao = (String) entidade.getProperty(GrupoFields.descricao.name());
		this.proprietario = (String) entidade.getProperty(GrupoFields.proprietario.name());
		this.infra = (Boolean) entidade.getProperty(GrupoFields.infra.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(Grupo.class));
		entidade.setProperty(GrupoFields.id.name(), this.id);
		entidade.setProperty(GrupoFields.nome.name(), this.nome);
		entidade.setProperty(GrupoFields.descricao.name(), this.descricao);
		entidade.setProperty(GrupoFields.proprietario.name(), this.proprietario);
		entidade.setProperty(GrupoFields.infra.name(), this.infra);
		return entidade;
	}

	public Grupo preenche(String nome, String descricao, String proprietario) {
		ConteudoHTML conteudoHTML = new ConteudoHTML(nome);
		this.nome = conteudoHTML.removeJavaScript();

		conteudoHTML = new ConteudoHTML(descricao);
		this.descricao = conteudoHTML.removeJavaScript();

		conteudoHTML = new ConteudoHTML(proprietario);
		this.proprietario = conteudoHTML.removeJavaScript();

		return this;
	}

	public GrupoJSON getGrupoJSON() {
		GrupoJSON grupojson = new GrupoJSON(this.id, this.nome, this.descricao, getUsuarioJSON(), getServicos());
		grupojson.setProprietario(proprietario);
		grupojson.setInfra(this.infra);
		String username = AutenticacaoService.identificacaoDoUsuarioLogado();
		Boolean isUsuarioGrupoInfra = AutenticacaoService.isUsuarioGrupoInfra();
		
		if (isUsuarioGrupoInfra || proprietario.equals(username)) {
			grupojson.setExcluirGrupo(true);
		} else {
			grupojson.setExcluirGrupo(false);
		}
		
		return grupojson;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getProprietario() {
		return proprietario;
	}

	public List<UsuarioJSON> getUsuarioJSON() {
		UsuarioJSON usuariojson;
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		for (Membro membro : this.membros) {
			usuariojson = new UsuarioJSON(membro.getIdUsuario(), membro.getNomeUsuario(), membro.getEmail());
			usuariosjson.add(usuariojson);
		}
		return usuariosjson;
	}

	public List<GoogleGrupoJSON> getServicos() {
		List<GoogleGrupoJSON> googleGrupoJSONs = new ArrayList<GoogleGrupoJSON>();

		for (ServicoGrupo servicoGrupos : this.servicoGrupos) {
			GoogleGrupoJSON googleGrupoJSON = new GoogleGrupoJSON(servicoGrupos.getId(), servicoGrupos.getIdServico(), null, servicoGrupos.getEmailGrupo(), null, servicoGrupos.getEmailsExternos());
			googleGrupoJSONs.add(googleGrupoJSON);
		}
		return googleGrupoJSONs;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public List<ServicoGrupo> getServicoGrupos() {
		return servicoGrupos;
	}

	public void setServicoGrupos(List<ServicoGrupo> servicoGrupos) {
		this.servicoGrupos = servicoGrupos;
	}

	public Boolean isInfra() {
		return infra;
	}
	
	public void setInfra(Boolean infra) {
		this.infra = infra;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Grupo [")
						.append(GrupoFields.id.name() + "=").append(this.id + ",")
							.append(GrupoFields.nome.name() + "=").append(this.nome + ",")
								.append(GrupoFields.descricao.name() + "=").append(this.descricao + ",")
										.append(GrupoFields.proprietario.name() + "=").append(this.proprietario).append("]").toString();
	}

}