package br.com.dextra.dextranet.cron;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.dextranet.banner.BannerRepository;

public class BannerCronServlet extends HttpServlet {

	private static final long serialVersionUID = -271337554352471822L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		atualizaFlags();
	}

	public void atualizaFlags() {
		BannerRepository bannerRepository = new BannerRepository();
		System.out.println("Cron executado as " + new Date());
		bannerRepository.atualizaFlags();
	}
}
