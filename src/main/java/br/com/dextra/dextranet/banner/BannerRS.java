package br.com.dextra.dextranet.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import br.com.dextra.dextranet.rest.config.Application;
import br.com.dextra.dextranet.seguranca.AutenticacaoService;
import br.com.dextra.dextranet.utils.TimeMachine;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonObject;

@Path("/banner")
public class BannerRS {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	private BannerRepository repositorio = new BannerRepository();

	private TimeMachine timeMachine = new TimeMachine();

	@Path("/")
	@POST
	@Produces(Application.JSON_UTF8)
	public Response inserir(@FormParam("titulo") String titulo, @FormParam("dataInicio") String dataInicio,
			@FormParam("dataFim") String dataFim, @FormParam("link") String link, @FormParam("imagem") String imagem, @Context HttpServletRequest request) {

		Banner banner = new Banner(titulo, link, timeMachine.tranformaEmData(dataInicio),
				timeMachine.tranformaEmData(dataFim), AutenticacaoService.identificacaoDoUsuarioLogado());
		banner.adicionaImagem(this.obtemBlobKeyDaImagem(request));

		repositorio.persiste(banner);

		return Response.ok().entity(banner).build();
	}

	@Path("/url-upload")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response uploadURL() throws EntityNotFoundException {
		JsonObject json = new JsonObject();
		json.addProperty("url", blobstoreService.createUploadUrl("/s/banner/"));

		return Response.ok().entity(json).build();
	}

	@Path("/{id}")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response obter(@PathParam("id") String id) throws EntityNotFoundException {
		Banner banner = repositorio.obtemPorId(id);
		return Response.ok().entity(banner).build();
	}

	@Path("/{id}/imagem")
	@GET
	public Response obterImagem(@PathParam("id") String id) throws EntityNotFoundException {
		Banner banner = repositorio.obtemPorId(id);
		// TODO: implementar
		return Response.ok().entity(banner).build();
	}

	@Path("/")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listar() {
		List<Banner> banners = repositorio.lista();
		return Response.ok().entity(banners).build();
	}

	@Path("/vigentes")
	@GET
	@Produces(Application.JSON_UTF8)
	public Response listarVigentes() {
		List<Banner> banners = repositorio.lista();
		List<Banner> bannersVigentes = filtraBannersVigentes(banners);

		return Response.ok().entity(bannersVigentes).build();
	}

	@Path("/{id}")
	@DELETE
	@Produces(Application.JSON_UTF8)
	public Response apagar(@PathParam(value = "id") String id) {
		repositorio.remove(id);
		return Response.ok().build();
	}

	private BlobKey obtemBlobKeyDaImagem(HttpServletRequest request) {
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		List<BlobKey> listBlobs = blobs.get("banner");
		
		BlobKey blobKey = null;
		if (!listBlobs.isEmpty()) {
			blobKey = listBlobs.get(0);
		}
		
		return blobKey;
	}

	protected List<Banner> filtraBannersVigentes(List<Banner> banners) {
		List<Banner> bannersVigentes = new ArrayList<Banner>();
		
		for (Banner banner : banners) {
			if (banner.estaVigente()) {
				bannersVigentes.add(banner);
			}
		}
		return bannersVigentes;
	}

}