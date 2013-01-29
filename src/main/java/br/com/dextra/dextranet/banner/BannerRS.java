package br.com.dextra.dextranet.banner;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonObject;


@Path("/banner")
public class BannerRS {

	private static final Logger log = Logger.getLogger(BannerRS.class.getName());

	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	@Path("/uploadURL")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String upload() throws EntityNotFoundException {
		String url = blobstoreService.createUploadUrl("/banner/upload");
		JsonObject json = new JsonObject();
		json.addProperty("url", url);
		
		return json.toString();
	}
	
	@Path("/bannerAtual")
	@GET
	@Produces("image/*")
	public void BannerAtualURL(@Context HttpServletResponse response) {
		BannerRepository bannerRepository = new BannerRepository();

		log.info("id do banner atual: " + bannerRepository.getBannerAtual().getId());
		log.info("blobkey do banner atual : " + bannerRepository.getBannerAtual().getBlobKey().getKeyString());

		BlobKey blobKey = bannerRepository.getBannerAtual().getBlobKey();

		log.info("Tentara servir blob do banco.");

		log.info("blobstore key construido: " + blobKey.getKeyString());
		
		try {
			BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
			blobstoreService.serve(blobKey, response);
			log.info("quase sucesso.");
			return;
		} catch (IOException e) {
			log.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}