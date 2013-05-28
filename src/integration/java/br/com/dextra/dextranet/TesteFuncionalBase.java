package br.com.dextra.dextranet;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.web.PaginaPrincipal;
import br.com.dextra.expertus.EnvironmentFactory;
import br.com.dextra.expertus.environment.Environment;
import br.com.dextra.teste.TesteIntegracaoBase;

public class TesteFuncionalBase extends TesteIntegracaoBase {

	public PaginaPrincipal paginaPrincipal = new PaginaPrincipal(driver);

	private static Environment environment;

	protected static WebDriver driver;

	@BeforeClass
	public static void setup() {
		server.enableAuthentication(true, false);
		server.enableJetty(8080);
		TesteIntegracaoBase.setup();

		environment = EnvironmentFactory.createEnvironment();
		driver = environment.createDriver();
	}

	@AfterClass
	public static void shutdown() throws IOException {
		driver.quit();
		TesteIntegracaoBase.shutdown();
	}
}
