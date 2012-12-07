package br.com.dextra.teste;

import br.com.dextra.gae.GAETestHelper;

import com.googlecode.mycontainer.kernel.boot.ContainerBuilder;

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

		ContainerBuilder container = gaeHelper.bootMycontainer();
		container.waitFor();
	}

	public void stop() {
		gaeHelper.shutdownMycontainer();
	}

}
