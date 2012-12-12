package br.com.dextra.teste;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

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

		DesiredCapabilities sauceCapabilities = DesiredCapabilities.firefox();
		sauceCapabilities.setCapability("version", "17");
		sauceCapabilities.setPlatform(Platform.WINDOWS);

		driver = new RemoteWebDriver(sauceCapabilities);
	}

	@AfterClass
	public static void shutdown() {
		myContainer.stop();

		driver.close();
	}

}
