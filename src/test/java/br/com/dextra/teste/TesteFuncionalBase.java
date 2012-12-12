package br.com.dextra.teste;

import java.net.MalformedURLException;
import java.net.URL;

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

		try {
			driver = new RemoteWebDriver(
					new URL(
							"http://cloudbees_dextra-con:b6f5241c-0038-404a-81d6-7bd848b8e56b@ondemand.saucelabs.com:80/wd/hub"),
					sauceCapabilities);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

	}

	@AfterClass
	public static void shutdown() {
		myContainer.stop();

		driver.close();
	}

}
