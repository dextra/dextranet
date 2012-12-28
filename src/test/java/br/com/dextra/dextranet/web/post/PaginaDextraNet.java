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
		Assert.assertTrue(this.getElement("span.icon_header_logo") != null);
	}

	public void verificaSeIncluiuPost() {
		Assert.assertEquals("Seu post foi inserido com sucesso.", this
				.getElementContent("li.success"));
	}

	public void verificaSeApareceuPostBuscado() {
		Assert.assertEquals("Kaique vai no cineminha", this
				.getElementContent("div.list_stories_lead"));
	}

	public void writeCKEditor(String text) {
		WebElement iframe = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(iframe);
		((JavascriptExecutor) driver).executeScript("document.body.innerHTML='"
				+ text + "'");
		driver.switchTo().defaultContent();
	}
}