package br.com.dextra.dextranet.banner;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query.SortDirection;

public class BannerRepositoryTest extends TesteIntegracaoBase {

	private BannerRepository repositorio = new BannerRepository();

	private TimeMachine timeMachine = new TimeMachine();
	private Date dataAtual = timeMachine.dataAtual();
	private Date cincoDiasParaFrente = timeMachine.diasParaFrente(5);
	private Date dezDiasParaFrente = timeMachine.diasParaFrente(5);
	private Date cincoDiasParaAtras = timeMachine.diasParaAtras(5);

	@Test
	public void testaListaTodosComOrdenacao() {
		EntidadeOrdenacao dataInicioCrescente = new EntidadeOrdenacao(BannerFields.dataInicio.name(),
				SortDirection.ASCENDING);

		Banner banner01 = new Banner("titulo 01", "link", cincoDiasParaFrente, dezDiasParaFrente, "usuario");
		Banner banner02 = new Banner("titulo 02", "link", dataAtual, dezDiasParaFrente, "usuario");
		Banner banner03 = new Banner("titulo 02", "link", cincoDiasParaAtras, dataAtual, "usuario");

		repositorio.persiste(banner01);
		repositorio.persiste(banner02);
		repositorio.persiste(banner03);

		List<Banner> bannersOrdenados = repositorio.lista(dataInicioCrescente);
		Assert.assertEquals(banner03, bannersOrdenados.get(0));
		Assert.assertEquals(banner01, bannersOrdenados.get(2));
	}

	@Test
	public void testaRemocao() {
		Banner novaBanner = new Banner("titulo", "link", dataAtual, dataAtual, "usuario");

		Banner bannerCriado = repositorio.persiste(novaBanner);

		String idDoBannerCriado = bannerCriado.getId();
		repositorio.remove(idDoBannerCriado);

		try {
			repositorio.obtemPorId(idDoBannerCriado);
			Assert.fail();
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

}