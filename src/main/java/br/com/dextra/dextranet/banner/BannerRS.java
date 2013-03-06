package br.com.dextra.dextranet.banner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import br.com.dextra.dextranet.utils.Converters;
import br.com.dextra.dextranet.utils.Data;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.JsonObject;

@Path("/banner")
public class BannerRS {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	private BannerRepository bannerRepository = new BannerRepository();

	@Path("/")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String bannersDisponiveis(@QueryParam("atuais") Boolean bannerAtuais) {
		return Converters.converterListaDeBannerParaListaDeJson(
				bannerRepository.getBannerDisponiveis(bannerAtuais)).toString();
	}
//		GET http://dextranet-desenvolvimento.appspot.com/s/banner
//		?atuais=true

	@Path("/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public Response getById(@Context HttpServletResponse response,
			@PathParam("id") String id) throws IOException {

		Image imagem = bannerRepository.transformaImagem(id);

		byte[] imgBytes = imagem.getImageData();
		String formatoDaImagem = imagem.getFormat().toString();

		ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
		ServletOutputStream outputStream = response.getOutputStream();

//		blobstoreService.serve(imagem.getBlobKey(), response);
		// ta comentada essa linha de cima ?????????????????????????????????????????????????????????????????????

		response.setContentType("image/" + formatoDaImagem); // "image/jpg"
		response.setContentLength(imgBytes.length);

		try {
			IOUtils.copy(bais, outputStream);
		} finally {
			outputStream.close();
			bais.close();
		}

		return Response.ok().build();
	}

	@Path("/")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void retornoDoGAE(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws IOException {
		Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
		List<BlobKey> listBlobs = blobs.get("banner");
		BlobKey blobKey = null;
		if (!listBlobs.isEmpty())
			blobKey = listBlobs.get(0);

		try {
			bannerRepository.criar(request.getParameter("bannerTitulo"),
					blobKey, request.getParameter("dataInicio"),
					request.getParameter("dataFim"),
					request.getParameter("link"));
		} catch (ParseException e) {
			blobstoreService.delete(blobKey);
			setReponseStatus(response, HttpServletResponse.SC_BAD_REQUEST,
					"Data mal formatada.");
		} catch (DataNaoValidaException e) {
			blobstoreService.delete(blobKey);
			setReponseStatus(response, HttpServletResponse.SC_BAD_REQUEST,
					"Data invalida.");
		} catch (NullUserException e) {
			blobstoreService.delete(blobKey);
			setReponseStatus(response, HttpServletResponse.SC_FORBIDDEN,
					"Permissão negada!");
		} catch (NullBlobkeyException e) {
			setReponseStatus(response,
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Falha ao salvar banner.");
		}

		response.sendRedirect("/");
	}

	@Path("/editar")
	@POST
	@Produces("application/json;charset=UTF-8")
	public void editarBanner(
			@FormParam("id") String id,
			@FormParam("titulo") String titulo,
			@FormParam("dataInicio") String dataInicio,
			@FormParam("dataFim") String dataFim,
			@FormParam("link") String link,
			@Context HttpServletResponse response) throws IOException {

		Banner banner = bannerRepository.obterPorID(id);


		try {
			banner.setTitulo(titulo);
			banner.setDataInicio(Data.primeiroSegundo(Data
					.stringParaData(dataInicio)));
			banner.setDataFim(Data.ultimoSegundo(Data.stringParaData(dataFim)));
			banner.setUsuario(UserServiceFactory.getUserService()
					.getCurrentUser().getNickname());
			banner.setDataDeAtualizacao(new Date());
			banner.setBannerNovo(false);
			banner.setLink(link);

			banner = bannerRepository.atualizaFlagsDepoisDaEdicao(banner);

			bannerRepository.criar(banner);

			bannerRepository.atualizaFlags();
		} catch (ParseException e) {
			setReponseStatus(response, HttpServletResponse.SC_BAD_REQUEST,
					"Data mal formatada.");
		}
	}

	public void setReponseStatus(HttpServletResponse response, int status,
			String mensagem) throws IOException {
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