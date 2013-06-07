package br.com.dextra.dextranet.grupos;

import java.util.List;
import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.utils.ConteudoHTML;
import com.google.appengine.api.datastore.Entity;

public class Grupo extends Entidade {
	private String nome;
	private List<Usuario> usuarios;
	private List<GoogleGrupo> googleGrupos;
	private List<Projeto> projetos;

	public Grupo(String nome) {
		super();
		preenche(nome);
	}

	public Grupo(Entity entidade) {
		this.id = (String) entidade.getProperty(GrupoFields.id.name());
		this.nome = (String) entidade.getProperty(GrupoFields.nome.name());
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(GrupoFields.id.name(), this.id);
		entidade.setProperty(GrupoFields.nome.name(), this.nome);

		return entidade;
	}

	public Grupo preenche(String nome) {
		ConteudoHTML conteudoHTML = new ConteudoHTML(nome);
		this.nome = conteudoHTML.removeJavaScript();
		return this;
	}

	public String getNome() {
		return nome;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<GoogleGrupo> getGoogleGrupos() {
		return this.googleGrupos;
	}

	public List<Projeto> getProjetos() {
		return this.projetos;
	}
}