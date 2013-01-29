package br.com.dextra.teste.container;



public class MyContainer {

	private int port = 8080;

	private GAETestHelper gaeHelper;

	public MyContainer() {
	}

	public MyContainer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		gaeHelper = new GAETestHelper();
		gaeHelper.setPort(this.port);
		gaeHelper.prepareLocalServiceTestHelper();

		gaeHelper.bootMycontainer();
	}

	public void stop() {
		gaeHelper.shutdownMycontainer();
	}

}
