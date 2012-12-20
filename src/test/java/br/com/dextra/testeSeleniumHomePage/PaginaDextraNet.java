package br.com.dextra.testeSeleniumHomePage;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import br.com.dextra.teste.PaginaBase;

public class PaginaDextraNet extends PaginaBase
{
	public PaginaDextraNet(WebDriver driver)
	{
		super(driver);
	}

	public void verificaSeTemLogoHomePage()
	{
		Assert.assertTrue(this.obtemElemento("span.icon-header-logo") != null);
	}

	public void verificaSeApareceuPostIncluido()
	{
		Assert.assertEquals("Corpo teeste",this.obtemValorDoElemento("p.list-stories-lead"));
	}
}
