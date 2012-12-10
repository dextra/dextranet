package br.com.dextra.teste;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class TesteFuncionalBase {

	private static MyContainer myContainer;

	protected static WebDriver driver;

	@BeforeClass
	public static void setup() {
		myContainer = new MyContainer();
		try {
			myContainer.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		driver = new HtmlUnitDriver(true);
	}

	@AfterClass
	public static void shutdown() {
		myContainer.stop();

		driver.close();
	}

}
