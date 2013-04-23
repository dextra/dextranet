package br.com.dextra.teste;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.dextra.teste.container.GAETestServer;

public class TesteIntegracaoBase {

	protected static GAETestServer server = new GAETestServer();

	private static final boolean noStorage = true;

	@BeforeClass
	public static void setup() {
		server.enableDatastore(noStorage);
		server.start();
	}

	@AfterClass
	public static void shutdown() {
		server.stop();
	}
}
