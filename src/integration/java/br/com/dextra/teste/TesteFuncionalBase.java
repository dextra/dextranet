package br.com.dextra.teste;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import br.com.dextra.dextranet.web.PaginaPrincipal;
import br.com.dextra.expertus.EnvironmentFactory;
import br.com.dextra.expertus.environment.Environment;

public class TesteFuncionalBase extends TesteIntegracaoBase {

	public PaginaPrincipal paginaPrincipal = new PaginaPrincipal(driver);

	private static Environment environment;

	protected static WebDriver driver;

	@BeforeClass
	public static void setup() {
		TesteIntegracaoBase.setup();

		environment = EnvironmentFactory.createEnvironment();
		driver = environment.createDriver();
	}

	@AfterClass
	public static void shutdown() throws IOException {
		driver.quit();

		TesteIntegracaoBase.shutdown();
	}

	protected void dadoQueUsuarioAcessaPaginaPrincipal() {
		paginaPrincipal.acesso();
	}

}
