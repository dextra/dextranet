package br.com.dextra.dextranet.banner;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Banner extends Entidade {

	private String titulo;

	private BlobKey blobKey;

	public Banner(String titulo, BlobKey blobKey) {
		this.titulo = titulo;
		this.blobKey = blobKey;
		System.out.println(this.blobKey.getKeyString());
	}

	public Banner(Entity bannerEntity) {
		if (bannerEntity != null) {
			this.id = (String) bannerEntity.getProperty(BannerFields.ID.getField());
			this.titulo = (String) bannerEntity.getProperty(BannerFields.TITULO.getField());
			this.blobKey = (BlobKey) bannerEntity.getProperty(BannerFields.BLOBKEY.getField());
		}
	}

	public Banner() {
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public BlobKey getBlobKey() {
		return blobKey;
	}

	public void setBlobKey(BlobKey blobKey) {
		this.blobKey = blobKey;
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(BannerFields.ID.getField(), this.id);
		entidade.setProperty(BannerFields.TITULO.getField(), this.titulo);
		entidade.setProperty(BannerFields.BLOBKEY.getField(), this.blobKey);

		return entidade;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty(BannerFields.ID.getField(), this.id);
		json.addProperty(BannerFields.TITULO.getField(), this.titulo);
		json.addProperty(BannerFields.BLOBKEY.getField(), this.blobKey.getKeyString());

		return json;
	}
}
