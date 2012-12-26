package br.com.dextra.dextranet.web.post;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.expertus.page.PageObject;

public class PaginaDextraNet extends PageObject {
	public PaginaDextraNet(WebDriver driver) {
		super(driver);
	}

	public void verificaSeTemLogoHomePage() {
		Assert.assertTrue(this.getElement("span.icon-header-logo") != null);
	}

	public void verificaSeApareceuPostIncluido() {
		Assert.assertEquals("KaiqueVaiNoCineminha", this
				.getElementContent("p.list-stories-lead"));
	}

	public void verificaSeApareceuPostBuscado() {
		Assert.assertEquals("KaiqueVaiNoCineminha", this
				.getElementContent("p.list-stories-lead"));
	}

	public void writeCKEditor(String text) {
		WebElement iframe = driver.findElement(By.tagName("iframe"));
		final WebDriver frame = driver.switchTo().frame(iframe);
		((JavascriptExecutor) driver).executeScript("document.body.innerHTML='"
				+ text + "'");
		driver.switchTo().defaultContent();
	}
}