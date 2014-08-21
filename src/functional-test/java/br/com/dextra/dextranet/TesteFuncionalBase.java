package br.com.dextra.dextranet;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.dextra.dextranet.web.PaginaPrincipal;
import br.com.dextra.teste.TesteIntegracaoBase;

public class TesteFuncionalBase extends TesteIntegracaoBase {
	public PaginaPrincipal paginaPrincipal = new PaginaPrincipal(driver);
	protected static WebDriver driver;

	@BeforeClass
	public static void setup() {
		server.enableAuthentication(true, false);
		server.enableJetty(8080);
		TesteIntegracaoBase.setup();
		
		String executable = "";
		if (isWindows()) {
			executable = "target/phantomjs/phantomjs.exe";
		} else {
			executable = "target/phantomjs/phantomjs";
		}
		
		DesiredCapabilities dCaps = new DesiredCapabilities();
		dCaps.setJavascriptEnabled(true);
		dCaps.setCapability("takesScreenshot", false);
		dCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, executable);
		driver = new PhantomJSDriver(dCaps);
		driver.manage().window().setSize(new Dimension(1600, 900));
	}

	@AfterClass
	public static void shutdown() throws IOException {
		driver.quit();
		TesteIntegracaoBase.shutdown();
	}
	
	public static void snapshot(String fileName) {
		File tmpdir = new File(System.getProperty("java.io.tmpdir"));
		File snapdir = new File(tmpdir, "PRINTS");
		snapdir.mkdirs();

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		scrFile.renameTo(new File(snapdir, fileName));
	}
	
	public static boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}
}
