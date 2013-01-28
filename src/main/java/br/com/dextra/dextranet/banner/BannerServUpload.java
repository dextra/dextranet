package br.com.dextra.dextranet.banner;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class BannerServUpload extends HttpServlet {
	
	private static final long serialVersionUID = 6534542020903358150L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("bla");
		
//		@SuppressWarnings("deprecation")
//		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
//		BlobKey blobKey = blobs.get("fileInput");
//		JsonObject json = new JsonObject();
//
//		json.addProperty("blobkey", blobKey.getKeyString());
//		json.addProperty("temp", true);
//		json.addProperty("_self", "/s/blobstore");
//		resp.setContentType("text/html");
//		resp.getWriter().print(json);
		
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("myFile");

        if (blobKey == null) {
            resp.sendRedirect("/");
        } else {
            blobstoreService.serve(blobKey, resp);
        }
	}
}
