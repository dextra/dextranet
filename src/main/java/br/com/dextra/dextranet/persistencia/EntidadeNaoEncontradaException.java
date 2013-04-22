package br.com.dextra.dextranet.persistencia;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 7272232805180578476L;

	private String entidade;

	private String atributo;

	private String valor;

	public EntidadeNaoEncontradaException(String entidade, String atributo, String valor) {
		super();
		this.entidade = entidade;
		this.atributo = atributo;
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "EntidadeNaoEncontradaException [entidade=" + entidade + ", atributo=" + atributo + ", valor=" + valor
				+ "]";
	}

}
