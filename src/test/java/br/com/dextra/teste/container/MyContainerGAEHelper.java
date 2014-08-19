package br.com.dextra.teste.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dextra.dextranet.area.Area;
import br.com.dextra.dextranet.area.AreaRepository;
import br.com.dextra.dextranet.persistencia.DadosUtils;
import br.com.dextra.dextranet.unidade.Unidade;
import br.com.dextra.dextranet.unidade.UnidadeRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioFields;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
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
import com.googlecode.mycontainer.web.WebServerDeployer;
import com.googlecode.mycontainer.web.jetty.JettyServerDeployer;

public class MyContainerGAEHelper {
	private static final Logger LOG = LoggerFactory.getLogger(MyContainerGAEHelper.class);

	protected LocalServiceTestHelper helper;

	private LocalDatastoreServiceTestConfig ds;
	private LocalSearchServiceTestConfig fts;
	private int port = 8080;

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
		helper.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

	public void criaMassaDeDados() {
		UnidadeRepository repositorioUnidades = new UnidadeRepository();
		repositorioUnidades.persiste(new Unidade("Campo Grande"));
		repositorioUnidades.persiste(new Unidade("Campinas"));

		AreaRepository repositorioAreas = new AreaRepository();
		repositorioAreas.persiste(new Area("Diretoria"));
		repositorioAreas.persiste(new Area("Administrativo"));
		repositorioAreas.persiste(new Area("Financeiro"));
		repositorioAreas.persiste(new Area("Marketing"));
		repositorioAreas.persiste(new Area("RH"));
		repositorioAreas.persiste(new Area("Desenvolvimento"));
		repositorioAreas.persiste(new Area("Desenvolvimento de Negócios"));
		repositorioAreas.persiste(new Area("Treinamento"));
		
		Usuario loginGoogle = criarUsuario("login.google", "login.google", "Desenv", "Campinas", "111", "193322211");
		Usuario usuarioInfra = criarUsuario("usuario.infra", "usuario.infra", "Desenv", "Campinas", "222", "1932215544");
		Usuario rodrigoMagalhaes = criarUsuario("rodrigo.magalhaes", "rodrigo.magalhaes", "Desenv", "Campinas", "333", "1932222544");
		Usuario buildContinua = criarUsuario("build-continua", "build-continua", "Desenv", "Campinas", "212", "1922215544");
		Usuario joseSilva = criarUsuario("jose-silva", "jose-silva", "Desenv", "Campinas", "212", "1922215544");
		Usuario joaoMarcos = criarUsuario("joao-marcos", "joao-marcos", "Desenv", "Campinas", "212", "1922215544");
		Usuario flaviaSoarez = criarUsuario("flavia-soarez", "flavia-soarez", "Desenv", "Campinas", "212", "1922215544");
		Usuario marcosGomes = criarUsuario("marcos-gomes", "marcos-gomes", "Desenv", "Campinas", "212", "1922215544");
		Usuario alberto = criarUsuario("alberto", "alberto", "Desenv", "Campinas", "212", "1922215544");
		
		DadosUtils.criaGrupoComOsIntegrantes(true, "Grupo Infra", usuarioInfra, buildContinua);
		DadosUtils.criaGrupoComOsIntegrantes(false, "Grupo A", rodrigoMagalhaes, buildContinua, flaviaSoarez);
		DadosUtils.criaGrupoComOsIntegrantes(false, "Grupo B", buildContinua, usuarioInfra, loginGoogle, rodrigoMagalhaes);
		DadosUtils.criaGrupoComOsIntegrantes(false, "Grupo C", loginGoogle, marcosGomes, joaoMarcos, joseSilva);
		DadosUtils.criaGrupoComOsIntegrantes(false, "Grupo C", loginGoogle, alberto);
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

		server.deploy();
		return builder;
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

	private Usuario criarUsuario(String nome, String apelido, String area, String unidade, String ramal, String telefoneResidencial) {
		UsuarioRepository repository = new UsuarioRepository();
		Usuario usuario = new Usuario(nome);
		repository.persiste(usuario);
		
		Entity ent = new Entity(KeyFactory.createKey(Usuario.class.getName(), usuario.getId()));
		ent.setProperty(UsuarioFields.nome.name(), nome);
		ent.setProperty(UsuarioFields.id.name(), usuario.getId());
		ent.setProperty(UsuarioFields.username.name(), nome);
		ent.setProperty(UsuarioFields.apelido.name(), apelido);
		ent.setProperty(UsuarioFields.area.name(), area);
		ent.setProperty(UsuarioFields.unidade.name(), unidade);
		ent.setProperty(UsuarioFields.ramal.name(), ramal);
		ent.setProperty(UsuarioFields.telefoneResidencial.name(), telefoneResidencial);
		
		usuario = new Usuario(ent);
		usuario.setAtivo(true);
		repository.persiste(usuario);
		return usuario;
	}

}
