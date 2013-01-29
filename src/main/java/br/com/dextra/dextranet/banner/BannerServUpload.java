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
	private BannerRepository bannerRepository = new BannerRepository();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		@SuppressWarnings("deprecation")
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("banner");

        if (blobKey == null) {
            resp.sendRedirect("/");
        } else {
        	Banner banner = new Banner(req.getParameter("bannerTitle"), blobKey.getKeyString());
        	bannerRepository.criar(banner);
        	blobstoreService.serve(blobKey, resp);
        }
	}
}
