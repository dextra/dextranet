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
	public void BannerAtualURL(@Context HttpServletResponse response) throws IOException {
		BannerRepository bannerRepository = new BannerRepository();
		String blobKeyAtual = bannerRepository.getBannerAtual().getBlobKey();
		BlobKey blobKey = new BlobKey(blobKeyAtual);
		response.setContentType("image/*");
		BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
	    blobstoreService.serve(blobKey, response);
	}
}