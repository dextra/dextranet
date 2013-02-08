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
	
	private String usuario;

	private Date dataDeAtualizacao;
	
	private Boolean bannerNovo;

	public Banner(String titulo, BlobKey blobKey, Date dataInicio, Date dataFim, Boolean jaComecou, Boolean jaTerminou,
			String usuario, Date dataDeAtualizacao, Boolean bannerNovo) {
		this.titulo = titulo;
		this.blobKey = blobKey;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.jaComecou = jaComecou;
		this.jaTerminou = jaTerminou;
		this.usuario = usuario;
		this.dataDeAtualizacao = dataDeAtualizacao;
		this.bannerNovo = bannerNovo;
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
			this.usuario = (String) bannerEntity.getProperty(BannerFields.USUARIO.getField());
			this.dataDeAtualizacao = (Date) bannerEntity.getProperty(BannerFields.DATA_DE_ATUALIZACAO.getField());
			this.bannerNovo = (Boolean) bannerEntity.getProperty(BannerFields.BANNER_NOVO.getField());
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

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setDataDeAtualizacao(Date dataDeAtualizacao) {
		this.dataDeAtualizacao = dataDeAtualizacao;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Boolean getJaTerminou() {
		return jaTerminou;
	}

	public void setJaComecou(Boolean jaComecou) {
		this.jaComecou = jaComecou;
	}

	public void setJaTerminou(Boolean jaTerminou) {
		this.jaTerminou = jaTerminou;
	}

	public String getUsuario() {
		return usuario;
	}

	public Date getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	public Boolean getBannerNovo() {
		return bannerNovo;
	}
	
	public void setBannerNovo(Boolean bannerNovo) {
		this.bannerNovo = bannerNovo;
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
		entidade.setProperty(BannerFields.USUARIO.getField(), this.usuario);
		entidade.setProperty(BannerFields.DATA_DE_ATUALIZACAO.getField(), this.dataDeAtualizacao);
		entidade.setProperty(BannerFields.BANNER_NOVO.getField(), this.bannerNovo);

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
		json.addProperty(BannerFields.USUARIO.getField(), this.usuario);
		json.addProperty(BannerFields.DATA_DE_ATUALIZACAO.getField(), this.dataDeAtualizacao.toString());
		json.addProperty(BannerFields.BANNER_NOVO.getField(), this.bannerNovo.toString());

		return json;
	}
}
