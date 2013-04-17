package br.com.dextra.dextranet.banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;

public class BannerRSTest {

	private TimeMachine timeMachine = new TimeMachine();

	private Date hoje = timeMachine.dataAtual();
	private Date cincoDiasAtras = timeMachine.diasPraTras(5);
	private Date cincoDiasPraFrente = timeMachine.diasPraFrente(5);

	@Test
	public void testaFiltraBannersVigentes() {
		Banner banner01 = new Banner("titulo", "link", cincoDiasAtras, cincoDiasAtras, "usuario");
		Banner banner02 = new Banner("titulo", "link", cincoDiasAtras, hoje, "usuario");
		Banner banner03 = new Banner("titulo", "link", hoje, hoje, "usuario");
		Banner banner04 = new Banner("titulo", "link", hoje, cincoDiasPraFrente, "usuario");
		Banner banner05 = new Banner("titulo", "link", cincoDiasPraFrente, cincoDiasPraFrente, "usuario");

		List<Banner> todosOsBanners = new ArrayList<Banner>();
		todosOsBanners.add(banner01);
		todosOsBanners.add(banner02);
		todosOsBanners.add(banner03);
		todosOsBanners.add(banner04);
		todosOsBanners.add(banner05);

		List<Banner> bannersVigentes = new BannerRS().filtraBannersVigentes(todosOsBanners);
		Assert.assertEquals(3, bannersVigentes.size());
		Assert.assertEquals(banner02, bannersVigentes.get(0));
		Assert.assertEquals(banner03, bannersVigentes.get(1));
		Assert.assertEquals(banner04, bannersVigentes.get(2));
	}

}
