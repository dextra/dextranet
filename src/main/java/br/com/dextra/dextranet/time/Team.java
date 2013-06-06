package br.com.dextra.dextranet.time;

import java.util.List;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;

public class Team extends Entidade {
	private String nome;
	private String emailGrupo;
	private List<Usuario> usuarios;

	public Team(String nome, String emailGrupo) {
		super();
		preenche(nome, emailGrupo);
	}

	public Team(Entity entidade) {
		this.id = (String) entidade.getProperty(TeamFields.id.name());
		this.nome = (String) entidade.getProperty(TeamFields.nome.name());
		this.emailGrupo = (String) entidade.getProperty(TeamFields.emailGrupo.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(TeamFields.id.name(), this.id);
		entidade.setProperty(TeamFields.nome.name(), this.nome);
		entidade.setProperty(TeamFields.emailGrupo.name(), this.emailGrupo);

		return entidade;
	}

	public Team preenche(String nome, String emailGrupo) {
		ConteudoHTML conteudoHTML = new ConteudoHTML(nome);
		this.nome = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(emailGrupo);
		this.emailGrupo = conteudoHTML.removeJavaScript();

		return this;
	}

	public String getNome() {
		return nome;
	}

	public String getEmailGrupo() {
		return emailGrupo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}