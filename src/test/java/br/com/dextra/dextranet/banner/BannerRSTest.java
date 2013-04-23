package br.com.dextra.dextranet.banner;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

public class BannerRSTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	private Date hoje = timeMachine.dataAtual();
	private Date cincoDiasAtras = timeMachine.diasParaAtras(5);
	private Date cincoDiasPraFrente = timeMachine.diasParaFrente(5);

	private BannerRS rest = new BannerRS();
	private BannerRepository repositorio = new BannerRepository();

	@After
	public void removeBannersInseridos() {
		List<Banner> bannersCadastrados = repositorio.lista();
		for (Banner banner : bannersCadastrados) {
			repositorio.remove(banner.getId());
		}
	}

	@Test
	public void testaListarOrdenacao() {
		Banner banner01 = new Banner("titulo", "link", cincoDiasAtras, cincoDiasAtras, "usuario");
		Banner banner02 = new Banner("titulo", "link", cincoDiasAtras, hoje, "usuario");
		Banner banner03 = new Banner("titulo", "link", hoje, hoje, "usuario");
		Banner banner04 = new Banner("titulo", "link", hoje, cincoDiasPraFrente, "usuario");
		Banner banner05 = new Banner("titulo", "link", cincoDiasPraFrente, cincoDiasPraFrente, "usuario");

		repositorio.persiste(banner01);
		repositorio.persiste(banner04);
		repositorio.persiste(banner03);
		repositorio.persiste(banner05);
		repositorio.persiste(banner02);

		List<Banner> bannersVigentes = rest.listarBannersOrdenados();

		Assert.assertEquals(5, bannersVigentes.size());
		Assert.assertEquals(banner05, bannersVigentes.get(0));
		Assert.assertEquals(banner04, bannersVigentes.get(1));
		Assert.assertEquals(banner03, bannersVigentes.get(2));
		Assert.assertEquals(banner02, bannersVigentes.get(3));
		Assert.assertEquals(banner01, bannersVigentes.get(4));
	}

	@Test
	public void testaListarVigentes() {
		Banner banner01 = new Banner("titulo", "link", cincoDiasAtras, cincoDiasAtras, "usuario");
		Banner banner02 = new Banner("titulo", "link", cincoDiasAtras, hoje, "usuario");
		Banner banner03 = new Banner("titulo", "link", hoje, hoje, "usuario");
		Banner banner04 = new Banner("titulo", "link", hoje, cincoDiasPraFrente, "usuario");
		Banner banner05 = new Banner("titulo", "link", cincoDiasPraFrente, cincoDiasPraFrente, "usuario");

		repositorio.persiste(banner05);
		repositorio.persiste(banner04);
		repositorio.persiste(banner03);
		repositorio.persiste(banner02);
		repositorio.persiste(banner01);

		List<Banner> bannersVigentes = rest.listarBannersVigentesOrdenados(2);

		Assert.assertEquals(2, bannersVigentes.size());
		Assert.assertEquals(banner02, bannersVigentes.get(0));
		Assert.assertEquals(banner03, bannersVigentes.get(1));
	}

}
