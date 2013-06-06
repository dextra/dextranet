package br.com.dextra.dextranet.persistencia;

import java.util.Date;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class EntidadeBusca {
	Date data;
	String clazz;
	String campo;
	SortDirection direcaoOrdenacao;
	FilterOperator filtro;
	Integer limite;

	public Date getData() {
		return data;
	}
	public String getClazz() {
		return clazz;
	}
	public String getCampo() {
		return campo;
	}
	public SortDirection getDirecaoOrdenacao() {
		return direcaoOrdenacao;
	}
	public FilterOperator getFiltro() {
		return filtro;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public void setDirecaoOrdenacao(SortDirection direcaoOrdenacao) {
		this.direcaoOrdenacao = direcaoOrdenacao;
	}
	public void setFiltro(FilterOperator filtro) {
		this.filtro = filtro;
	}

	public void setLimite(Integer limite) {
		this.limite = limite;
	}

	public int getLimite() {
		return limite;
	}

	public boolean isLimite() {
		return (limite != null);
	}

}
