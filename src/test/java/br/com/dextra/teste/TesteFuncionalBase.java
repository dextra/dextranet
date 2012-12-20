package br.com.dextra.teste;

//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.saucerest.SauceREST;

public class TesteFuncionalBase {

	private static final String SAUCELABS_USERNAME = "cloudbees_dextra-con";

	private static final String SAUCELABS_KEY = "b6f5241c-0038-404a-81d6-7bd848b8e56b";

	private static MyContainer myContainer;

	protected static WebDriver driver;

	protected static SauceREST sauceClient;

	@BeforeClass
	public static void setup() {
		myContainer = new MyContainer();
		try {
			myContainer.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		StringBuilder sauceURL = new StringBuilder("http://");
		sauceURL.append(SAUCELABS_USERNAME);
		sauceURL.append(":");
		sauceURL.append(SAUCELABS_KEY);
		sauceURL.append("@ondemand.saucelabs.com:80/wd/hub");

		DesiredCapabilities sauceCapabillities = DesiredCapabilities.chrome();
		sauceCapabillities.setPlatform(Platform.LINUX);

//		try {
//			driver = new RemoteWebDriver(new URL(sauceURL.toString()), sauceCapabillities);
//		} catch (MalformedURLException e) {
//			throw new RuntimeException(e);
//		}
		driver = new FirefoxDriver();

//		Map<String, Object> sauceJob = new HashMap<String, Object>();
//		sauceJob.put("name", "Teste dextranet");
//
//		sauceClient = new SauceREST(SAUCELABS_USERNAME, SAUCELABS_KEY);
//		try {
//			sauceClient.updateJobInfo(((RemoteWebDriver) driver).getSessionId().toString(), sauceJob);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}

	@AfterClass
	public static void shutdown() {
		driver.close();
//		try {
//			sauceClient.jobPassed(((RemoteWebDriver) driver).getSessionId().toString());
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
		myContainer.stop();
	}

}
