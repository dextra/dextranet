package br.com.dextra.teste.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dextra.dextranet.area.Area;
import br.com.dextra.dextranet.area.AreaRepository;
import br.com.dextra.dextranet.perfil.Perfil;
import br.com.dextra.dextranet.perfil.PerfilRepository;
import br.com.dextra.dextranet.unidade.Unidade;
import br.com.dextra.dextranet.unidade.UnidadeRepository;

import com.google.appengine.api.blobstore.dev.ServeBlobFilter;
import com.google.appengine.api.blobstore.dev.UploadBlobServlet;
import com.google.appengine.api.images.dev.LocalBlobImageServlet;
import com.google.appengine.api.search.dev.LocalSearchService;
import com.google.appengine.tools.development.testing.LocalBlobstoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.mycontainer.gae.web.LocalServiceTestHelperFilter;
import com.googlecode.mycontainer.kernel.ShutdownCommand;
import com.googlecode.mycontainer.kernel.boot.ContainerBuilder;
import com.googlecode.mycontainer.web.ContextWebServer;
import com.googlecode.mycontainer.web.FilterDesc;
import com.googlecode.mycontainer.web.ServletDesc;
import com.googlecode.mycontainer.web.WebServerDeployer;
import com.googlecode.mycontainer.web.jetty.JettyServerDeployer;

public class GAETestHelper {

	private static final Logger LOG = LoggerFactory.getLogger(GAETestHelper.class);

	protected LocalServiceTestHelper helper;

	private LocalDatastoreServiceTestConfig ds;
	private LocalSearchServiceTestConfig fts;

	private int port = 8380;

	public void setPort(int port) {
		this.port = port;
	}

	public void prepareLocalServiceTestHelper() throws Exception {
		ds = new LocalDatastoreServiceTestConfig();
		ds.setDefaultHighRepJobPolicyUnappliedJobPercentage(0);
		fts = new LocalSearchServiceTestConfig();

		List<LocalServiceTestConfig> list = new ArrayList<LocalServiceTestConfig>();
		list.add(ds);
		list.add(fts);
		LocalBlobstoreServiceTestConfig localBlobstoreServiceTestConfig = new LocalBlobstoreServiceTestConfig();
		localBlobstoreServiceTestConfig.setNoStorage(true);
		list.add(localBlobstoreServiceTestConfig);

		helper = new LocalServiceTestHelper(list.toArray(new LocalServiceTestConfig[0]));
		Map<String, Object> envs = new HashMap<String, Object>();
		envs.put("com.google.appengine.api.users.UserService.user_id_key", "10");
		helper.setEnvAttributes(envs);
		helper.setEnvIsLoggedIn(true);
		helper.setEnvIsAdmin(false);
		helper.setEnvEmail("login.google@example.com");
		helper.setEnvAuthDomain("example.com");
	}

	public void prepareSearchServiceTestHelper() throws Exception {
		new LocalSearchService();
	}

	public LocalServiceTestHelper getHelper() {
		return helper;
	}

	public LocalDatastoreServiceTestConfig getDs() {
		return ds;
	}

	public void bootLocalServiceTestHelper() throws Exception {
		helper.setUp();
	}

	public void shutdownLocalServiceTestHelper() {
		try {
			if (helper != null) {
				helper.tearDown();
			}
		} catch (Exception e) {
			LOG.info("error", e);
		}
	}

	public ContainerBuilder bootMycontainer() throws Exception {
		ContainerBuilder builder = new ContainerBuilder();
		builder.deployVMShutdownHook();

		WebServerDeployer server = builder.createDeployer(JettyServerDeployer.class);
		server.setName("WebServer");
		server.bindPort(port);

		ContextWebServer web = server.createContextWebServer();
		web.setContext("/");
		web.setResources("src/main/webapp");

		LocalServiceTestHelperFilter gae = new LocalServiceTestHelperFilter(helper);
		web.getFilters().add(new FilterDesc(gae, "/*"));

		web.getFilters().add(new FilterDesc(ServeBlobFilter.class, "/*"));
		web.getServlets().add(new ServletDesc(UploadBlobServlet.class, "/_ah/upload/*"));
		web.getServlets().add(new ServletDesc(LocalBlobImageServlet.class, "/_ah/img/*"));

		System.setProperty("env", "local");

		server.deploy();

		// adicionar data
		insertData();

		return builder;
	}

	private void insertData() {

		PerfilRepository perfilRepository = new PerfilRepository();
		AreaRepository areaRepository = new AreaRepository();
		UnidadeRepository unidadeRepository = new UnidadeRepository();

		perfilRepository.novo(new Perfil("10", "testName", "testNickName", "Desenvolvimento", "Campo Grande", "00",
				"testSkype", "gTalk", "testPhoneResidence", "testPhoneMobile",
				"https://fbcdn-sphotos-e-a.akamaihd.net/hphotos-ak-ash4/309981_344485208954613_952944866_n.jpg", "githubUserFake"));

		areaRepository.inserir(new Area(""));
		areaRepository.inserir(new Area("Diretoria"));
		areaRepository.inserir(new Area("Administrativo"));
		areaRepository.inserir(new Area("Financeiro"));
		areaRepository.inserir(new Area("Marketing"));
		areaRepository.inserir(new Area("RH"));
		areaRepository.inserir(new Area("Desenvolvimento"));
		areaRepository.inserir(new Area("Desenvolvimento de Negócios"));
		areaRepository.inserir(new Area("Treinamento"));

		unidadeRepository.inserir(new Unidade(""));
		unidadeRepository.inserir(new Unidade("Campinas"));
		unidadeRepository.inserir(new Unidade("Campo Grande"));

	}

	public void shutdownMycontainer() {
		try {
			ShutdownCommand shutdown = new ShutdownCommand();
			shutdown.setContext(new InitialContext());
			shutdown.shutdown();
		} catch (Exception e) {
			LOG.error("error", e);
		}
	}

	public void tearDown() {
		if (helper != null) {
			helper.tearDown();
		}
	}

	public void setUp() {
		if (helper != null) {
			helper.setUp();
		}
	}

	public LocalServiceTestHelper getGaeHelper() {
		return helper;
	}

}
