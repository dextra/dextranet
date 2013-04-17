package br.com.dextra.teste.container;

public class MyContainerServer {

	private int port = 8080;

	private MyContainerGAEHelper gaeHelper;

	public MyContainerServer() {
	}

	public MyContainerServer(int port) {
		this.port = port;
	}

	public void start() throws Exception {
		gaeHelper = new MyContainerGAEHelper();
		gaeHelper.setPort(this.port);
		gaeHelper.prepareLocalServiceTestHelper();

		gaeHelper.bootMycontainer();
	}

	public void stop() {
		gaeHelper.shutdownMycontainer();
	}

}