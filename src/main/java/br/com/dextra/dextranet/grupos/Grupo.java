package br.com.dextra.dextranet.grupos;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;

public class Grupo extends Entidade {
	private String nome;
	private String descricao;
	private String proprietario;
	private List<Membro> membros;

	public Grupo(String nome, String descricao, String proprietario) {
		super();
		preenche(nome, descricao, proprietario);
	}

	public Grupo(Entity entidade) {
		this.id = (String) entidade.getProperty(GrupoFields.id.name());
		this.nome = (String) entidade.getProperty(GrupoFields.nome.name());
		this.descricao = (String) entidade.getProperty(GrupoFields.descricao.name());
		this.proprietario = (String) entidade.getProperty(GrupoFields.proprietario.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(Grupo.class));
		entidade.setProperty(GrupoFields.id.name(), this.id);
		entidade.setProperty(GrupoFields.nome.name(), this.nome);
		entidade.setProperty(GrupoFields.descricao.name(), this.descricao);
		entidade.setProperty(GrupoFields.proprietario.name(), this.proprietario);

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

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getProprietario() {
		return proprietario;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("Grupo [id=", this.id)
						.append("nome=", this.nome)
							.append("descricao=", this.descricao)
								.append("proprietario=", this.proprietario).append("]").toString();
	}
}