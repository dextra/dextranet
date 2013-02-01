package br.com.dextra.dextranet.banner;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.JsonObject;

public class Banner extends Entidade {

	private String titulo;

	private BlobKey blobKey;

	private Date dataInicio;	

	private Date dataFim;
	
	private Boolean jaComecou;
	

	private Boolean jaTerminou;

	public Banner(String titulo, BlobKey blobKey, Date dataInicio, Date dataFim, Boolean jaComecou, Boolean jaTerminou) {
		this.titulo = titulo;
		this.blobKey = blobKey;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.jaComecou = jaComecou;
		this.jaTerminou = jaTerminou;
	}

	public Banner(Entity bannerEntity) {
		if (bannerEntity != null) {
			this.id = (String) bannerEntity.getProperty(BannerFields.ID.getField());
			this.titulo = (String) bannerEntity.getProperty(BannerFields.TITULO.getField());
			this.blobKey = (BlobKey) bannerEntity.getProperty(BannerFields.BLOBKEY.getField());
			this.dataInicio = (Date) bannerEntity.getProperty(BannerFields.DATA_INICIO.getField());
			this.dataFim = (Date) bannerEntity.getProperty(BannerFields.DATA_FIM.getField());
			this.jaComecou = (Boolean) bannerEntity.getProperty(BannerFields.JA_COMECOU.getField());
			this.jaTerminou = (Boolean) bannerEntity.getProperty(BannerFields.JA_TERMINOU.getField());
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
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}

	public Boolean getJaComecou() {
		return jaComecou;
	}
	
	public Boolean getJaTerminou() {
		return jaTerminou;
	}
	
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(BannerFields.ID.getField(), this.id);
		entidade.setProperty(BannerFields.TITULO.getField(), this.titulo);
		entidade.setProperty(BannerFields.BLOBKEY.getField(), this.blobKey);
		entidade.setProperty(BannerFields.DATA_INICIO.getField(), this.dataInicio);
		entidade.setProperty(BannerFields.DATA_FIM.getField(), this.dataFim);
		entidade.setProperty(BannerFields.JA_COMECOU.getField(), this.jaComecou);
		entidade.setProperty(BannerFields.JA_TERMINOU.getField(), this.jaTerminou);

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
		json.addProperty(BannerFields.JA_COMECOU.getField(), this.jaComecou.toString());
		json.addProperty(BannerFields.JA_TERMINOU.getField(), this.jaTerminou.toString());
		
		return json;
	}
}
