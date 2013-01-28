package br.com.dextra.dextranet.banner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

}