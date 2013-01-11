package br.com.dextra.teste;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.dextra.expertus.page.PageObject;
import br.com.dextra.expertus.page.TimedOutException;

public class PaginaBase extends PageObject {

	public PaginaBase(WebDriver driver) {
		super(driver);
	}

	public void writeCKEditor(String text) {
		WebElement iframe = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(iframe);
		((JavascriptExecutor) driver).executeScript("document.body.innerHTML='" + text + "'");
		driver.switchTo().defaultContent();
	}

	public void waitingForLoading() {
		String loadingCssSeletor = ".isLoading";

		// faz um sleep inicial para o carregando aparecer
		this.waitToLoad(TIME_TO_WAIT);

		int tentativas = 1;

		while (tentativas < MAX_ATTEMPT_TO_WAIT) {
			try {
				this.driver.findElement(By.cssSelector(loadingCssSeletor));
			} catch (NoSuchElementException e) {
				break;
			}

			tentativas++;
			this.waitToLoad(TIME_TO_WAIT);
		}

		if (tentativas >= MAX_ATTEMPT_TO_WAIT) {
			throw new TimedOutException(loadingCssSeletor + " nao desapareceu no tempo esperado.");
		}
	}

}
