package br.com.dextra.dextranet.banner;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;

// a heranca eh para poder testar os objetos envolvendo entity
public class BannerTest extends TesteIntegracaoBase {

	private TimeMachine timeMachine = new TimeMachine();

	private Date dataAtual = timeMachine.dataAtual();

	@Test
	public void testeConstrutor() {
		Entity bannerEntity = new Banner("titulo", "link", dataAtual, dataAtual, "usuario").toEntity();
		Banner banner = new Banner(bannerEntity);

		Assert.assertEquals(bannerEntity.getProperty(BannerFields.id.toString()), banner.getId());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.titulo.toString()), banner.getTitulo());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.link.toString()), banner.getLink());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.dataInicio.toString()), banner.getDataInicio());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.dataFim.toString()), banner.getDataFim());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.usuario.toString()), banner.getUsuario());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.dataAtualizacao.toString()),
				banner.getDataDeAtualizacao());
	}

	@Test
	public void testeToEntity() {
		Banner banner = new Banner("titulo", "link", dataAtual, dataAtual, "usuario");
		Entity bannerEntity = banner.toEntity();

		Assert.assertEquals(banner.getId(), bannerEntity.getProperty(BannerFields.id.toString()));
		Assert.assertEquals(banner.getTitulo(), bannerEntity.getProperty(BannerFields.titulo.toString()));
		Assert.assertEquals(banner.getLink(), bannerEntity.getProperty(BannerFields.link.toString()));
		Assert.assertEquals(banner.getDataInicio(), bannerEntity.getProperty(BannerFields.dataInicio.toString()));
		Assert.assertEquals(banner.getDataFim(), bannerEntity.getProperty(BannerFields.dataFim.toString()));
		Assert.assertEquals(banner.getUsuario(), bannerEntity.getProperty(BannerFields.usuario.toString()));
		Assert.assertEquals(banner.getDataDeAtualizacao(),
				bannerEntity.getProperty(BannerFields.dataAtualizacao.toString()));
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
