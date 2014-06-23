package br.com.dextra.teste.container;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.dextra.dextranet.area.Area;
import br.com.dextra.dextranet.area.AreaRepository;
import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.ServicoGrupo;
import br.com.dextra.dextranet.grupo.ServicoGrupoRepository;
import br.com.dextra.dextranet.grupo.servico.Servico;
import br.com.dextra.dextranet.grupo.servico.ServicoRepository;
import br.com.dextra.dextranet.unidade.Unidade;
import br.com.dextra.dextranet.unidade.UnidadeRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioFields;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
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
		
		//Usuarios infra
		Usuario usuario = criarUsuario("login.google", "login.google", "Desenv", "Campinas", "111", "193322211");
		Usuario usuarioInfra = criarUsuario("usuario.infra", "usuario.infra", "Desenv", "Campinas", "222", "1932215544");
		Usuario usuario1 = criarUsuario("rodrigo.magalhaes", "rodrigo.magalhaes", "Desenv", "Campinas", "333", "1932222544");
		Usuario usuario2 = criarUsuario("build-continua", "build-continua", "Desenv", "Campinas", "212", "1922215544");
		
		Grupo grupoInfra = criarGrupo("Grupo Infra", "Grupo Infraestrutura", "login.google", true);
		Grupo grupo = criarGrupo("Grupo 1", "Grupo", "build-continua", false);

		ServicoGrupo servico = criarServicoGrupo(grupo, "teste-grupo");
		ServicoGrupo servicoInfra = criarServicoGrupo(grupoInfra, "grupo-infra");
		
		Membro membro = criarMembro(grupo, "Rodrigo Teste", "rodrigo.magalhaes", usuario1.getId());
		Membro membro1 = criarMembro(grupo, "Build Continua", "build-continua", usuario2.getId());
		Membro membro2 = criarMembro(grupoInfra, "Google ", "login.google", usuario.getId());
		Membro membroInfra = criarMembro(grupoInfra, "Usuario Infra", "usuario.infra", usuarioInfra.getId());
		
		List<Membro> membros = new ArrayList<Membro>();
		membros.add(membro);
		membros.add(membro1);
		membros.add(membro2);
		grupo.setMembros(membros);
		
		List<ServicoGrupo> servicos = new ArrayList<ServicoGrupo>();
		servicos.add(servico);
		grupo.setServicoGrupos(servicos);
		persitirGrupo(grupo);
		
		List<Membro> membrosInfra = new ArrayList<Membro>();
		membrosInfra.add(membroInfra);
		grupoInfra.setMembros(membrosInfra);
		
		List<ServicoGrupo> servicosInfra = new ArrayList<ServicoGrupo>();
		servicosInfra.add(servicoInfra);
		grupoInfra.setServicoGrupos(servicosInfra);
		persitirGrupo(grupoInfra);
	}

	private ServicoGrupo criarServicoGrupo(Grupo grupo, String emailGrupo) {
		Servico servico = new Servico("Grupo de email");
		ServicoRepository servicoRepository = new ServicoRepository();
		servicoRepository.persiste(servico);
		
		ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getId(), grupo.getId(), emailGrupo);
		ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();
		
		servicoGrupo = servicoGrupoRepository.persiste(servicoGrupo);
		return servicoGrupo;
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
		repository.persiste(usuario);
		return usuario;
	}
	
	private Membro criarMembro(Grupo grupo, String nome, String email, String id) {
		MembroRepository membroRepository = new MembroRepository();
		Membro membro = new Membro(id, grupo.getId(), nome, email);
		
		membroRepository.persiste(membro);
		return membro;
	}

	private Grupo criarGrupo(String nome, String descricao, String emailProprietario, boolean isInfra) {
		Grupo grupo = null;
		grupo = new Grupo(nome, descricao, emailProprietario);
		grupo.setInfra(isInfra);
		grupo = persitirGrupo(grupo);
		return grupo;
	}

	private Grupo persitirGrupo(Grupo grupo) {
		GrupoRepository grupoRepository = new GrupoRepository();
		grupo = grupoRepository.persiste(grupo);
		return grupo;
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

}
