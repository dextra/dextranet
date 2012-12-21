package br.com.dextra.teste;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.dextra.teste.container.MyContainer;

public class TesteIntegracaoBase {

	protected static MyContainer myContainer;


	@BeforeClass
	public static void setup() {
		myContainer = new MyContainer();
		try {
			myContainer.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@AfterClass
	public static void shutdown() {
		myContainer.stop();
	}

}
