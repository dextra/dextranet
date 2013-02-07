package br.com.dextra.dextranet.banner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Test;

import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;

public class BannerRepositoryTest extends TesteIntegracaoBase {

	BannerRepository bannerRepository = new BannerRepository();

	@Test
	public void criarUmBanner() {

		Banner banner = criarBanner("banner", new BlobKey("asdf"), "02/02/2013", "25/02/2013");

		Banner bannerEncontrado = null;
		try {
			bannerEncontrado = bannerRepository.obterPorID(banner.getId());
		} catch (TooManyResultsException e) {
			fail("Mais de um banner com o mesmo ID");
		}

		assertEquals(banner.getTitulo(), bannerEncontrado.getTitulo());
		assertEquals(banner.getBlobKey(), bannerEncontrado.getBlobKey());
		assertEquals(banner.getDataInicio(), bannerEncontrado.getDataInicio());
		assertEquals(banner.getDataFim(), bannerEncontrado.getDataFim());
		assertEquals(banner.getJaComecou(), bannerEncontrado.getJaComecou());
		assertEquals(banner.getJaTerminou(), bannerEncontrado.getJaTerminou());
	}

	public Banner criarBanner(String titulo, BlobKey blobKey, String dataInicioFormatada, String dataFimFormatada) {
		try {
			return bannerRepository.criar(titulo, blobKey, dataInicioFormatada, dataFimFormatada);
		} catch (ParseException e) {
			fail("data mal formatada.");
		} catch (DataNaoValidaException e) {
			fail("data invalida");
		} catch (NullBlobkeyException e) {
			fail("blobkey invalida");
		} catch (NullUserException e) {
			fail("permissão negada");
		}
		return null;
	}

	@Test
	public void getBannerDisponiveisTeste() {

		int quantidadeDeBanners = 10;

		for (int i = 0; i < quantidadeDeBanners; i++) {
			// TODO data para string para melhorar o teste
			criarBanner("banner" + i, new BlobKey("bla" + i), "02/02/2013", "25/02/2200");
		}

		assertEquals(quantidadeDeBanners, bannerRepository.getBannerDisponiveis().size());
	}

	@Test
	public void atualizaFlagJaComecouTeste() {

		criaBanners(5, Calendar.getInstance(), false);
		bannerRepository.atualizaFlagJaComecou();
		assertEquals(3, bannerRepository.getBannerDisponiveis().size());
	}

	@Test
	public void atualizaFlagJaTerminou() {

		criaBanners(5, Calendar.getInstance(), true);
		bannerRepository.atualizaFlagJaTerminou();
		assertEquals(3, bannerRepository.getBannerDisponiveis().size());
	}

	private void criaBanners(int quantidaDeDeBanners, Calendar dataInicio, Boolean statusInicio) {

		Calendar c = Calendar.getInstance();

		for (int i = -2; i < quantidaDeDeBanners - 2; i++) {
			c.set(dataInicio.get(Calendar.YEAR), dataInicio.get(Calendar.MONTH), dataInicio.get(Calendar.DAY_OF_MONTH)
					+ i, 0, 0, 0);
			c.set(Calendar.MILLISECOND, 0);
			bannerRepository.criar(new Banner("baner", new BlobKey(c.toString()), c.getTime(), c.getTime(),
					statusInicio, false, "login.google", Calendar.getInstance().getTime()));
		}
	}
}
