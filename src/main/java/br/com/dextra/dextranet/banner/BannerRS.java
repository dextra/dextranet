package br.com.dextra.dextranet.banner;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.repackaged.org.apache.commons.logging.Log;
import com.google.appengine.repackaged.org.apache.commons.logging.LogFactory;
import com.google.gson.JsonObject;

@Path("/banner")
public class BannerRS {
	
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
	public void BannerAtualURL(@Context HttpServletResponse response) {
		Log log = LogFactory.getLog(Log.class);
		BannerRepository bannerRepository = new BannerRepository();

		log.info("id no banco do banner atual: ");
		log.info(bannerRepository.getBannerAtual().getId());

		String blobKeyAtual = bannerRepository.getBannerAtual().getBlobKey();

		log.info("Tentara servir blob do banco.");

		BlobKey blobKey = new BlobKey(blobKeyAtual);

		log.info("blobstore key");
		log.info(blobKey.getKeyString());
		
		try {
			blobstoreService.serve(blobKey, response);
			return;
		} catch (IOException e) {
			log.error(e);
		}

		log.info("Blob mocado, vai dar errado.");

		blobKey = new BlobKey("abc");
		log.info("blobstore key mocado");
		log.info(blobKey.getKeyString());
		try {
			blobstoreService.serve(blobKey, response);
		} catch (IOException e) {
			log.error(e);
		}
	}
}