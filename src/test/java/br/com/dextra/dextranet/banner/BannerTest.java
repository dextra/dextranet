package br.com.dextra.dextranet.banner;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;

public class BannerTest {

	private TimeMachine timeMachine = new TimeMachine();

	@Test
	public void testaConstrutor() {
		Date agora = timeMachine.dataAtual();
		Banner banner = new Banner("titulo", "link", agora, agora, "usuario");

		Assert.assertEquals(timeMachine.formataData(agora), timeMachine.formataData(banner.getDataDeAtualizacao()));
	}

	@Test
	public void testaEstaVigente() {
		Date cincoDiasPraTras = timeMachine.diasParaAtras(5);
		Date dezDiasPraFrente = timeMachine.diasParaFrente(10);
		Banner banner = new Banner("titulo", "link", cincoDiasPraTras, dezDiasPraFrente, "usuario");

		Assert.assertTrue(banner.estaVigente());
	}

	@Test
	public void testaEstaVigenteComInicioTerminoFuturo() {
		Date cincoDiasPraFrente = timeMachine.diasParaFrente(5);
		Date dezDiasPraFrente = timeMachine.diasParaFrente(10);
		Banner banner = new Banner("titulo", "link", cincoDiasPraFrente, dezDiasPraFrente, "usuario");

		Assert.assertFalse(banner.estaVigente());
	}

	@Test
	public void testaEstaVigenteComInicioTerminoPassado() {
		Date cincoDiasPraTras = timeMachine.diasParaAtras(5);
		Date dezDiasPraTras = timeMachine.diasParaAtras(10);
		Banner banner = new Banner("titulo", "link", cincoDiasPraTras, dezDiasPraTras, "usuario");

		Assert.assertFalse(banner.estaVigente());
	}

	@Test
	public void testaEstaVigenteComInicioHojeTerminoFuturo() {
		Date hoje = timeMachine.dataAtual();
		Date dezDiasPraFrente = timeMachine.diasParaFrente(10);
		Banner banner = new Banner("titulo", "link", hoje, dezDiasPraFrente, "usuario");

		Assert.assertTrue(banner.estaVigente());
	}

	@Test
	public void testaEstaVigenteComInicioPassadoTerminoHoje() {
		Date hoje = timeMachine.dataAtual();
		Date dezDiasPraTras = timeMachine.diasParaAtras(10);
		Banner banner = new Banner("titulo", "link", dezDiasPraTras, hoje, "usuario");

		Assert.assertTrue(banner.estaVigente());
	}

}
