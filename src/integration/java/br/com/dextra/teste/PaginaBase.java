package br.com.dextra.teste;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import br.com.dextra.expertus.page.PageObject;
import br.com.dextra.expertus.page.TimedOutException;

public class PaginaBase extends PageObject {

	public PaginaBase(WebDriver driver) {
		super(driver);
	}

	public void writeCKEditor(String text, String form) {
		((JavascriptExecutor) driver).executeScript("CKEDITOR.instances." + form + ".setData(\"" + text + "\");");
	}

	public void waitingForLoading() {
		String loadingCssSeletor = "div.loading";

		// faz um sleep inicial para o carregando aparecer
		this.waitToLoad(TIME_TO_WAIT);

		int tentativas = 1;

		while (tentativas < MAX_ATTEMPT_TO_WAIT) {
			boolean loadingInativo = Boolean.valueOf(this.getElementAttribute(loadingCssSeletor, "style").matches("display: none;"));

			if (loadingInativo) {
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
