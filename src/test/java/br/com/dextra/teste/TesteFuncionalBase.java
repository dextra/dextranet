package br.com.dextra.teste;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import br.com.dextra.expertus.EnvironmentFactory;
import br.com.dextra.expertus.environment.Environment;

public class TesteFuncionalBase extends TesteIntegracaoBase {

	private static Environment environment;

	protected static WebDriver driver;

	@BeforeClass
	public static void setup() {
		TesteIntegracaoBase.setup();

		environment = EnvironmentFactory.createEnvironment();
		driver = environment.createDriver();
	}

	@AfterClass
	public static void shutdown() {
		driver.close();

		TesteIntegracaoBase.shutdown();
	}

	public String getUrlApplication() {
		return "http://" + environment.getEnvironmentUrlBase();
	}

}
