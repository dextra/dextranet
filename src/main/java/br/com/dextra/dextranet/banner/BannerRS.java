package br.com.dextra.dextranet.banner;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

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
	
	@Path("/")
	@GET
	@Produces("image/*")
	public Response BannerAtualURL(@Context HttpServletResponse response) {
		BannerRepository bannerRepository = new BannerRepository();
		Banner bannerAtual = bannerRepository.getBannerAtual();

		if (bannerAtual == null || bannerAtual.getBlobKey() == null)
			return Response.noContent().build();

		BlobKey blobKey = bannerAtual.getBlobKey();

		try {
			BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
			blobstoreService.serve(blobKey, response);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}

		return Response.ok().build();
	}
}