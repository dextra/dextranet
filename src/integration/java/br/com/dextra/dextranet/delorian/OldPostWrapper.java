package br.com.dextra.dextranet.delorian;

public class OldPostWrapper {
	private Integer nid;
	private OldPost oldPost;

	public OldPostWrapper() {
	}

	public OldPostWrapper(Integer nid, OldPost oldPost) {
		this.nid = nid;
		this.oldPost = oldPost;
	}

	public Integer getNid() {
		return nid;
	}
	public void setNid(Integer nid) {
		this.nid = nid;
	}
	public OldPost getOldPost() {
		return oldPost;
	}
	public void setOldPost(OldPost oldPost) {
		this.oldPost = oldPost;
	}
}
