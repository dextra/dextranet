package br.com.dextra.dextranet.delorian;

public class ComentarioWrapper {
	private Integer nid;
	private Comentario comentario;

	public ComentarioWrapper() {
	}

	public ComentarioWrapper(Integer nid, Comentario comentario) {
		this.nid = nid;
		this.comentario = comentario;
	}

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public Comentario getComentario() {
		return comentario;
	}

	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}

}
