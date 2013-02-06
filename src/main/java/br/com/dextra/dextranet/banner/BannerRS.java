package br.com.dextra.dextranet.banner;

import java.io.IOException;
import java.text.ParseException;
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

import br.com.dextra.dextranet.utils.Converters;

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
	
	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String bannersDisponiveis() {
		return Converters.toJson(bannerRepository.getBannerDisponiveis()).toString();		
	}

	@Path("/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public Response getById(@Context HttpServletResponse response, @PathParam("id") String id) throws IOException {
		blobstoreService.serve(bannerRepository.obterPorID(id).getBlobKey(), response);
		
		return Response.ok().build();
	}
	
	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void retornoDoGAE(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
		BlobKey blobKey = blobs.get("banner");

		try {
			bannerRepository.criar(request.getParameter("bannerTitulo"), blobKey, request.getParameter("dataInicio"), request.getParameter("dataFim"));
		} catch (ParseException e) {
			blobstoreService.delete(blobKey);
			setReponseStatus(response, HttpServletResponse.SC_BAD_REQUEST, "Data mal formatada.");
		} catch (DataNaoValidaException e) {
			blobstoreService.delete(blobKey);
			setReponseStatus(response, HttpServletResponse.SC_BAD_REQUEST, "Data invalida.");
		} catch (NullUserException e) {
			blobstoreService.delete(blobKey);
			setReponseStatus(response, HttpServletResponse.SC_FORBIDDEN, "Permissão negada!");
		} catch (NullBlobkeyException e) {
			setReponseStatus(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Falha ao salvar banner.");
		}

		response.sendRedirect("/");
	}

	public void setReponseStatus(HttpServletResponse response, int status, String mensagem) throws IOException {
		response.setStatus(status);
		response.getWriter().write(mensagem);
	}

	@Path("/uploadURL")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String uploadURL() throws EntityNotFoundException {
		JsonObject json = new JsonObject();
		json.addProperty("url", blobstoreService.createUploadUrl("/s/banner/"));
		
		return json.toString();
	}
}