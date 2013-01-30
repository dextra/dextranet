package br.com.dextra.dextranet.banner;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Banner extends Entidade {

	private String titulo;

	private BlobKey blobKey;

	private String dataInicio;	

	private String dataFim;

	public Banner(String titulo, BlobKey blobKey, String dataInicio, String dataFim) {
		this.titulo = titulo;
		this.blobKey = blobKey;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
	}

	public Banner(Entity bannerEntity) {
		if (bannerEntity != null) {
			this.id = (String) bannerEntity.getProperty(BannerFields.ID.getField());
			this.titulo = (String) bannerEntity.getProperty(BannerFields.TITULO.getField());
			this.blobKey = (BlobKey) bannerEntity.getProperty(BannerFields.BLOBKEY.getField());
			this.dataInicio = (String) bannerEntity.getProperty(BannerFields.DATA_INICIO.getField());
			this.dataFim = (String) bannerEntity.getProperty(BannerFields.DATA_FIM.getField());
		}
	}

	public Banner() {
	}

	public String getTitulo() {
		return this.titulo;
	}

	public BlobKey getBlobKey() {
		return blobKey;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}
	
	public String getDataFim() {
		return dataFim;
	}

	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(BannerFields.ID.getField(), this.id);
		entidade.setProperty(BannerFields.TITULO.getField(), this.titulo);
		entidade.setProperty(BannerFields.BLOBKEY.getField(), this.blobKey);
		entidade.setProperty(BannerFields.DATA_INICIO.getField(), this.dataInicio);
		entidade.setProperty(BannerFields.DATA_FIM.getField(), this.dataFim);

		return entidade;
	}

	@Override
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty(BannerFields.ID.getField(), this.id);
		json.addProperty(BannerFields.TITULO.getField(), this.titulo);
		json.addProperty(BannerFields.BLOBKEY.getField(), this.blobKey.getKeyString());
		json.addProperty(BannerFields.DATA_INICIO.getField(), this.dataInicio.toString());
		json.addProperty(BannerFields.DATA_FIM.getField(), this.dataFim.toString());

		return json;
	}
}
