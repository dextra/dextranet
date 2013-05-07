package br.com.dextra.dextranet.delorian;

public class OldAnexo {
	private Integer postId;
	private String anexo;
	private String tituloDoPost;

	public OldAnexo() {

	}

	public OldAnexo(Integer postId, String anexo, String titulo) {
		this.postId = postId;
		this.anexo = anexo;
		this.tituloDoPost =  titulo;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public String getTituloDoPost() {
		return tituloDoPost;
	}

	public void setTitulo(String titulo) {
		this.tituloDoPost = titulo;
	}
}
