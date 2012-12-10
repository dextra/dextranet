package br.com.dextra.teste;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaginaBase {

	protected WebDriver driver;

	public PaginaBase(WebDriver driver) {
		this.driver = driver;
	}

	public void navegarNaPagina(String url) {
		this.driver.navigate().to(url);
		this.waitForPageToLoad();
	}

	public PaginaBase clica(String cssSelector) {
		this.driver.findElement(By.cssSelector(cssSelector)).click();
		this.waitForPageToLoad();
		return this;
	}

	public String obtemValorDoElemento(String cssSelector) {
		return this.driver.findElement(By.cssSelector(cssSelector)).getAttribute("value");
	}

	public String obtemAtributoDoElemento(String cssSelector, String atributo) {
		return this.driver.findElement(By.cssSelector(cssSelector)).getAttribute(atributo);
	}

	public String obtemConteudoDoElemebto(String cssSelector) {
		return this.driver.findElement(By.cssSelector(cssSelector)).getText();
	}

	public PaginaBase preencheInputText(String cssSelector, String valorDoElemento) {
		WebElement elemento = this.driver.findElement(By.cssSelector(cssSelector));
		elemento.clear();
		elemento.sendKeys(valorDoElemento);

		return this;
	}

	public PaginaBase preencheTextArea(String cssSelector, String valorDoElemento) {
		return this.preencheInputText(cssSelector, valorDoElemento);
	}

	protected void waitForPageToLoad() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
