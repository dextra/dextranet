package br.com.dextra.teste.container;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.googlecode.mycontainer.gae.web.LocalServiceTestHelperFilter;
import com.googlecode.mycontainer.kernel.boot.ContainerBuilder;
import com.googlecode.mycontainer.web.ContextWebServer;
import com.googlecode.mycontainer.web.FilterDesc;
import com.googlecode.mycontainer.web.WebServerDeployer;
import com.googlecode.mycontainer.web.jetty.JettyServerDeployer;

public class GAETestServer {

	private LocalServiceTestHelper gaeTestHelper;

	private List<LocalServiceTestConfig> gaeConfigurations = new ArrayList<LocalServiceTestConfig>();
	
	private MyContainerGAEHelper myContainerGAEHelper = new MyContainerGAEHelper();

	private boolean authenticationEnabled = true;
	private boolean userLoggedIn = true;
	private boolean userIsAdmin = false;

	public void start() {
		gaeTestHelper = new LocalServiceTestHelper(
				gaeConfigurations.toArray(new LocalServiceTestConfig[gaeConfigurations.size()]));

		if (authenticationEnabled) {
			gaeTestHelper.setEnvIsLoggedIn(this.userLoggedIn);
			gaeTestHelper.setEnvIsAdmin(this.userIsAdmin);
		}
		
		gaeTestHelper.setEnvEmail("login.google@example.com");
		gaeTestHelper.setEnvAuthDomain("example.com");
		gaeTestHelper.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		gaeTestHelper.setUp();
	}

	public void stop() {
		gaeTestHelper.tearDown();
		myContainerGAEHelper.shutdownMycontainer();
	}

	public void enableDatastore(boolean noStorage) {
		LocalDatastoreServiceTestConfig localDatastoreServiceTestConfig = new LocalDatastoreServiceTestConfig();
		localDatastoreServiceTestConfig.setNoStorage(noStorage);
		localDatastoreServiceTestConfig.setNoIndexAutoGen(noStorage);

		gaeConfigurations.add(localDatastoreServiceTestConfig);
	}

	public void enableAuthentication(boolean userLoggedIn, boolean userIsAdmin) {
		gaeConfigurations.add(new LocalUserServiceTestConfig());

		authenticationEnabled = true;
		this.userIsAdmin = userIsAdmin;
		this.userLoggedIn = userLoggedIn;
	}

	public void enableSearch() {
		LocalSearchServiceTestConfig localSearchServiceTestConfig = new LocalSearchServiceTestConfig();
		gaeConfigurations.add(localSearchServiceTestConfig);
	}
	

	public void enableJetty() {
		
		ContainerBuilder builder;
		try {
			builder = new ContainerBuilder();
			builder.deployVMShutdownHook();
	
			WebServerDeployer server = builder.createDeployer(JettyServerDeployer.class);
			server.setName("WebServer");
			server.bindPort(8380);
	
			ContextWebServer web = server.createContextWebServer();
			web.setContext("/");
			web.setResources("src/main/webapp");
	
			LocalServiceTestHelperFilter gae = new LocalServiceTestHelperFilter(gaeTestHelper);
			web.getFilters().add(new FilterDesc(gae, "/*"));
			
			server.deploy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
