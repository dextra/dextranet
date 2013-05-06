package br.com.dextra.dextranet.banner;

import java.util.Date;

import br.com.dextra.dextranet.persistencia.Entidade;
import br.com.dextra.dextranet.utils.ConteudoHTML;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

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
		ConteudoHTML conteudoHTML = new ConteudoHTML(titulo);
		this.titulo = conteudoHTML.removeJavaScript();

		conteudoHTML.setConteudo(link);
		this.link = conteudoHTML.removeJavaScript();
		this.usuario = usuario;

		TimeMachine timeMachine = new TimeMachine();
		this.dataInicio = timeMachine.inicioDoDia(dataInicio);
		this.dataFim = timeMachine.fimDoDia(dataFim);
		this.dataDeAtualizacao = timeMachine.dataAtual();
	}

	public Banner(Entity bannerEntity) {
		this.id = (String) bannerEntity.getProperty(BannerFields.id.name());
		this.titulo = (String) bannerEntity.getProperty(BannerFields.titulo.name());
		this.link = (String) bannerEntity.getProperty(BannerFields.link.name());
		this.dataInicio = (Date) bannerEntity.getProperty(BannerFields.dataInicio.name());
		this.dataFim = (Date) bannerEntity.getProperty(BannerFields.dataFim.name());
		this.usuario = (String) bannerEntity.getProperty(BannerFields.usuario.name());
		this.dataDeAtualizacao = (Date) bannerEntity.getProperty(BannerFields.dataAtualizacao.name());
		this.imagem = (BlobKey) bannerEntity.getProperty(BannerFields.imagem.name());
		this.imagemUrl = (String) bannerEntity.getProperty(BannerFields.imagemUrl.name());
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

		entidade.setProperty(BannerFields.id.name(), this.id);
		entidade.setProperty(BannerFields.titulo.name(), this.titulo);
		entidade.setProperty(BannerFields.link.name(), this.link);
		entidade.setProperty(BannerFields.dataInicio.name(), this.dataInicio);
		entidade.setProperty(BannerFields.dataFim.name(), this.dataFim);
		entidade.setProperty(BannerFields.usuario.name(), this.usuario);
		entidade.setProperty(BannerFields.dataAtualizacao.name(), this.dataDeAtualizacao);
		entidade.setProperty(BannerFields.imagem.name(), this.imagem);
		entidade.setProperty(BannerFields.imagemUrl.name(), this.imagemUrl);

		return entidade;
	}

	@Override
	public String toString() {
		return "Banner [imagem=" + imagem + ", imagemUrl=" + imagemUrl + ", titulo=" + titulo + ", link=" + link
				+ ", dataInicio=" + dataInicio + ", dataFim=" + dataFim + ", usuario=" + usuario
				+ ", dataDeAtualizacao=" + dataDeAtualizacao + "]";
	}

}
