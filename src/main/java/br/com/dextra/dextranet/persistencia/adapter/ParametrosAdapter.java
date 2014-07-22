package br.com.dextra.dextranet.persistencia.adapter;

import java.util.List;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;

public class ParametrosAdapter<T> {
	private Class<T> entidade;
	private Integer registrosPorPagina;
	private Integer numeroDaPagina;
	private EntidadeOrdenacao[] ordenacao;
	private List<String> filtroCampos;
	private List<Object> filtroValores;
	
	public ParametrosAdapter<T> comEntidade(Class<T> entidade) {
		this.entidade = entidade;
		return this;
	}
	
	public ParametrosAdapter<T> comRegistrosPorPagina(Integer registroPorPagina) {
		this.registrosPorPagina = registroPorPagina;
		return this;
	}
	
	public ParametrosAdapter<T> comNumeroDaPagina(Integer numeroDaPagina) {
		this.numeroDaPagina = numeroDaPagina;
		return this;
	}
	
	public ParametrosAdapter<T> comOrdenacao(EntidadeOrdenacao[] ordenacao) {
		this.ordenacao = ordenacao;
		return this;
	}
	
	public ParametrosAdapter<T> comCampos(List<String> campos) {
		this.filtroCampos = campos;
		return this;
	}
	
	public ParametrosAdapter<T> comValores(List<Object> valores) {
		this.filtroValores = valores;
		return this;
	}
	
	public Class<T> getEntidade() {
		return entidade;
	}
	
	public Integer getRegistrosPorPagina() {
		return registrosPorPagina;
	}
	
	public Integer getNumeroDaPagina() {
		return numeroDaPagina;
	}
	
	public EntidadeOrdenacao[] getOrdenacao() {
		return ordenacao;
	}
	
	public List<String> getFiltroCampos() {
		return filtroCampos;
	}
	
	public List<Object> getFiltroValores() {
		return filtroValores;
	}
}
