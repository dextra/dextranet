package br.com.dextra.dextranet.grupo.servico;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;

public class Servico extends Entidade {

	public String nome;

	public Servico(String nome) {
		this.nome = new ConteudoHTML(nome).removeJavaScript();
	}

	public Servico(Entity entity) {
		this.id = (String) entity.getProperty(ServicoFields.id.name());
		this.nome = (String) entity.getProperty(ServicoFields.nome.name());
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "Servico [id=" + id + ", name=" + nome + "]";
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(ServicoFields.id.name(), this.id);
		entidade.setProperty(ServicoFields.nome.name(), this.nome);
		return entidade;
	}

}
