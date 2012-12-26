package br.com.dextra.dextranet.web.post;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;

import br.com.dextra.expertus.page.PageObject;

public class PaginaDextraNet extends PageObject {
	public PaginaDextraNet(WebDriver driver) {
		super(driver);
	}

	public void verificaSeTemLogoHomePage() {
		Assert.assertTrue(this.getElement("span.icon-header-logo") != null);
	}

	public void verificaSeApareceuPostIncluido() {
		Assert.assertEquals("KaiqueVaiNoCineminha", this.getElementContent("p.list-stories-lead"));
	}

	public void verificaSeApareceuPostBuscado()
	{
		Assert.assertEquals("KaiqueVaiNoCineminha", this.getElementContent("p.list-stories-lead"));
	}
}