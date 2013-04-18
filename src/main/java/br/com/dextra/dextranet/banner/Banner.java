package br.com.dextra.dextranet.banner;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.JsonObject;

public class Banner extends Entidade {

	private BlobKey imagem;

	private String imagemUrl;

	private String titulo;

	private String link;

	private Date dataInicio;

	private Date dataFim;

	private String usuario;

	private Date dataDeAtualizacao;

	public Banner(String titulo, String link, Date dataInicio, Date dataFim, String usuario) {
		this.titulo = titulo;
		this.link = link;
		this.usuario = usuario;

		TimeMachine timeMachine = new TimeMachine();
		this.dataInicio = timeMachine.inicioDoDia(dataInicio);
		this.dataFim = timeMachine.fimDoDia(dataFim);
		this.dataDeAtualizacao = timeMachine.dataAtual();
	}

	public Banner(Entity bannerEntity) {
		this.id = (String) bannerEntity.getProperty(BannerFields.id.toString());
		this.titulo = (String) bannerEntity.getProperty(BannerFields.titulo.toString());
		this.link = (String) bannerEntity.getProperty(BannerFields.link.toString());
		this.dataInicio = (Date) bannerEntity.getProperty(BannerFields.dataInicio.toString());
		this.dataFim = (Date) bannerEntity.getProperty(BannerFields.dataFim.toString());
		this.usuario = (String) bannerEntity.getProperty(BannerFields.usuario.toString());
		this.dataDeAtualizacao = (Date) bannerEntity.getProperty(BannerFields.dataAtualizacao.toString());
		this.imagem = (BlobKey) bannerEntity.getProperty(BannerFields.imagem.toString());
		this.imagemUrl = (String) bannerEntity.getProperty(BannerFields.imagemUrl.toString());
	}

	public void adicionaImagem(BlobKey imagem) {
		this.imagem = imagem;

		ImagesService imageService = ImagesServiceFactory.getImagesService();
		this.imagemUrl = imageService.getServingUrl(ServingUrlOptions.Builder.withBlobKey(this.imagem));
	}

	public boolean estaVigente() {
		Date agora = new TimeMachine().dataAtual();
		return agora.after(this.dataInicio) && agora.before(this.dataFim);
	}

	public BlobKey getImagem() {
		return imagem;
	}

	public String getImagemUrl() {
		return imagemUrl;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getLink() {
		return link;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public String getUsuario() {
		return usuario;
	}

	public Date getDataDeAtualizacao() {
		return dataDeAtualizacao;
	}

	@Override
	public Entity toEntity() {
		Entity entidade = new Entity(this.getKey(this.getClass()));

		entidade.setProperty(BannerFields.id.toString(), this.id);
		entidade.setProperty(BannerFields.titulo.toString(), this.titulo);
		entidade.setProperty(BannerFields.link.toString(), this.link);
		entidade.setProperty(BannerFields.dataInicio.toString(), this.dataInicio);
		entidade.setProperty(BannerFields.dataFim.toString(), this.dataFim);
		entidade.setProperty(BannerFields.usuario.toString(), this.usuario);
		entidade.setProperty(BannerFields.dataAtualizacao.toString(), this.dataDeAtualizacao);
		entidade.setProperty(BannerFields.imagem.toString(), this.imagem);
		entidade.setProperty(BannerFields.imagemUrl.toString(), this.imagemUrl);

		return entidade;
	}

	@Override
	public JsonObject toJson() {
		throw new UnsupportedOperationException();
	}
}
