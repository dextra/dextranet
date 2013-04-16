package br.com.dextra.dextranet.persistencia;

import com.google.appengine.api.datastore.Query.SortDirection;

public class EntidadeOrdenacao {

	private String atributo;

	private SortDirection ordenacao;

	public EntidadeOrdenacao(String atributo, SortDirection ordenacao) {
		this.atributo = atributo;
		this.ordenacao = ordenacao;
	}

	public String getAtributo() {
		return atributo;
	}

	public SortDirection getOrdenacao() {
		return ordenacao;
	}

}
