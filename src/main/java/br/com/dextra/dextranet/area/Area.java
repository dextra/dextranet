package br.com.dextra.dextranet.area;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.ConteudoHTML;

import com.google.appengine.api.datastore.Entity;

public class Area extends Entidade {

	public String nome;

	public Area(String nome) {
		this.nome = new ConteudoHTML(nome).removeJavaScript();
	}

	public Area(Entity entity) {
		this.id = (String) entity.getProperty(AreaFields.id.name());
		this.nome = (String) entity.getProperty(AreaFields.nome.name());
	}

	public String getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", name=" + nome + "]";
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));
		entidade.setProperty(AreaFields.id.name(), this.id);
		entidade.setProperty(AreaFields.nome.name(), this.nome);
		return entidade;
	}

}
