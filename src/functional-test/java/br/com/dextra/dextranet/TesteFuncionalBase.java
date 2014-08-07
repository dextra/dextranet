package br.com.dextra.dextranet;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.dextra.dextranet.web.PaginaPrincipal;
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

		// FIXME: In windows env, change the file to phantom.exe. Implements
		// with System.getProperty
		 PhantomJSDriverService service = new PhantomJSDriverService.Builder().usingPhantomJSExecutable(
		 new File("target/phantomjs/phantomjs")).build();
		
		 driver = new PhantomJSDriver(service,
		 DesiredCapabilities.phantomjs());

//		System.setProperty("expertus.environment.browser","CHROME");
//		System.setProperty("webdriver.chrome.driver","/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
//		environment = EnvironmentFactory.createEnvironment();
//		driver = environment.createDriver();
	}

	@AfterClass
	public static void shutdown() throws IOException {
		driver.quit();
		TesteIntegracaoBase.shutdown();
	}

	protected void snapshot(String fileName) {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		File snapdir = new File(tmpdir, "snapshots");
		snapdir.mkdirs();

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		scrFile.renameTo(new File(snapdir, fileName));
	}
}
