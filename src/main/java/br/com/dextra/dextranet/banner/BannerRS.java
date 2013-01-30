package br.com.dextra.dextranet.banner;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	private BannerRepository bannerRepository = new BannerRepository();

	@Path("/uploadURL")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String uploadURL() throws EntityNotFoundException {
		String url = blobstoreService.createUploadUrl("/s/banner/");
		JsonObject json = new JsonObject();
		json.addProperty("url", url);
		
		return json.toString();
	}
	
//	@Path("/")
//	@GET
//	@Produces("image/*")
//	public Response BannerAtualURL(@Context HttpServletResponse response) {
//		List<Banner> bannerAtual = bannersDisponiveis();
//		
//		for (Banner banner : bannerAtual) { //Dar um jeito da blobstore servir varios banners
//			if (banner == null || banner.getBlobKey() == null)
//				return Response.noContent().build();
//			BlobKey blobKey = banner.getBlobKey();
//			try {
//				BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
//				blobstoreService.serve(blobKey, response);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return Response.status(500).build();
//			}
//		}
//		
//		return Response.ok().build();
//	}

	@Path("/")
	@GET
	public String bannersDisponiveis() {
		return bannerRepository.getBannerDisponiveis().toString();
	}

	@Path("/{id}")
	@GET
	public Response getById(@Context HttpServletResponse response, @PathParam("id") String id) throws IOException {
		blobstoreService.serve(bannerRepository.obterPorID(id).getBlobKey(), response);
		
		return Response.ok().build();
	}
	
	@SuppressWarnings("deprecation")
	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void retornoDoGAE(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
        BlobKey blobKey = blobs.get("banner");
        
        String dataInicio = request.getParameter("dataInicio") + "-" + request.getParameter("horaInicio");
        String dataFim = request.getParameter("dataInicio") + "-" + request.getParameter("horaFim");

        if (blobKey != null) {
            bannerRepository.criar(new Banner(request.getParameter("bannerTitulo"), blobKey, dataInicio , dataFim));
        } else 
        	response.setStatus(500);

    	response.sendRedirect("/");
	}
	
}