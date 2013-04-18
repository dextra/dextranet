package br.com.dextra.dextranet.banner;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.dextra.dextranet.persistencia.EntidadeOrdenacao;
import br.com.dextra.dextranet.utils.TimeMachine;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.Entity;
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
	public void testeConstrutor() {
		Entity bannerEntity = new Banner("titulo", "link", dataAtual, dataAtual, "usuario").toEntity();
		Banner banner = new Banner(bannerEntity);

		Assert.assertEquals(bannerEntity.getProperty(BannerFields.id.toString()), banner.getId());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.titulo.toString()), banner.getTitulo());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.link.toString()), banner.getLink());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.dataInicio.toString()), banner.getDataInicio());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.dataFim.toString()), banner.getDataFim());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.usuario.toString()), banner.getUsuario());
		Assert.assertEquals(bannerEntity.getProperty(BannerFields.dataAtualizacao.toString()), banner.getDataDeAtualizacao());
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
		Assert.assertEquals(banner.getDataDeAtualizacao(), bannerEntity.getProperty(BannerFields.dataAtualizacao.toString()));
	}

	@Test
	public void testaListaTodosComOrdenacao() {
		EntidadeOrdenacao dataInicioCrescente = new EntidadeOrdenacao(BannerFields.dataInicio.toString(), SortDirection.ASCENDING);

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