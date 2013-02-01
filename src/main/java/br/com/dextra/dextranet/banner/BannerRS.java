package br.com.dextra.dextranet.banner;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
import br.com.dextra.dextranet.utils.Data;

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
		List<JsonObject> json = Converters.toJson(bannerRepository.getBannerDisponiveis());
		return json.toString();		
	}

	@Path("/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public Response getById(@Context HttpServletResponse response, @PathParam("id") String id) throws IOException {
		blobstoreService.serve(bannerRepository.obterPorID(id).getBlobKey(), response);
		
		return Response.ok().build();
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void retornoDoGAE(@Context HttpServletRequest request, @Context HttpServletResponse response) throws IOException {
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
        BlobKey blobKey = blobs.get("banner");
        
        System.out.println("Aqui a blobKey: " + blobKey.toString());
        
        //Criar metodo no Data utils para retornar data formatada
        Date dataInicio = null;
        Date dataFim = null;
		try {
			dataInicio = Data.stringParaData(request.getParameter("dataInicio"));
			dataFim = Data.stringParaData(request.getParameter("dataInicio"));
		} catch (ParseException e) {
			response.setStatus(500);
			e.printStackTrace();
		}

        if (blobKey != null) {
        	if (dataBannerNaoEhValida(dataInicio, dataFim)) {
        		blobstoreService.delete(blobKey);
        		response.setStatus(500);
        	} else
        		bannerRepository.criar(new Banner(request.getParameter("bannerTitulo"), blobKey, dataInicio , dataFim, Data.igualAHoje(dataInicio), false));
        } else 
        	response.setStatus(500);

    	response.sendRedirect("/");
	}

	private boolean dataBannerNaoEhValida(Date dataInicio, Date dataFim) {
		return Data.anteriorAHoje(dataInicio) || Data.anteriorAHoje(dataFim) || dataFim.before(dataInicio);
	}
	
	@Path("/uploadURL")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String uploadURL() throws EntityNotFoundException {
		System.out.println("Entrou no rest");
		String url = blobstoreService.createUploadUrl("/s/banner/");
		JsonObject json = new JsonObject();
		json.addProperty("url", url);
		
		System.out.println("Aqui a Json com a URL: " + json);
		return json.toString();
	}
}