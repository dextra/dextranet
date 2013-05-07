package br.com.dextra.teste.container;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;

public class GAETestServer {

	private LocalServiceTestHelper gaeTestHelper;

	private List<LocalServiceTestConfig> gaeConfigurations = new ArrayList<LocalServiceTestConfig>();

	private boolean authenticationEnabled = false;
	private boolean userLoggedIn = false;
	private boolean userIsAdmin = false;

	public void start() {
		gaeTestHelper = new LocalServiceTestHelper(
				gaeConfigurations.toArray(new LocalServiceTestConfig[gaeConfigurations.size()]));

		if (authenticationEnabled) {
			gaeTestHelper.setEnvIsLoggedIn(this.userLoggedIn);
			gaeTestHelper.setEnvIsAdmin(this.userIsAdmin);
		}

		gaeTestHelper.setUp();
	}

	public void stop() {
		gaeTestHelper.tearDown();
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

}
