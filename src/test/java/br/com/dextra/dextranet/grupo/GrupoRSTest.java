package br.com.dextra.dextranet.grupo;

import static br.com.dextra.dextranet.persistencia.DadosUtils.criaGrupoComOsIntegrantes;
import static br.com.dextra.dextranet.persistencia.DadosUtils.criaUsuario;
import static junit.framework.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.grupo.servico.Servico;
import br.com.dextra.dextranet.grupo.servico.ServicoRepository;
import br.com.dextra.dextranet.grupo.servico.google.GoogleGrupoJSON;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoRSTest extends TesteIntegracaoBase {
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	private UsuarioRepository usuarioRepository = new UsuarioRepository();
	private GrupoRS grupoRS = new GrupoRSFake();

	private static String USUARIO_LOGADO = "login.google";

	@After
	public void removeDadosInseridos() {
		this.limpaGrupoInseridos(repositorioGrupo);
		this.limpaMembroInseridos(repositorioMembro);
	}

	@Test
	public void testaAdicionarGrupo() throws EntityNotFoundException,	
			ParseException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		UsuarioJSON uMembro = new UsuarioJSON(usuario.getId(),
				usuario.getNome(), email);
		List<GoogleGrupoJSON> googleGrupoJSON = null;

		List<UsuarioJSON> uMembros = new ArrayList<UsuarioJSON>();
		uMembros.add(uMembro);
		GrupoJSON grupojson = new GrupoJSON(null, nome, descricao, uMembros,
				googleGrupoJSON);
		Response response = grupoRS.adicionar(grupojson);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	public void testaUsuarioInfraAdicionarNovoMembroAoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criaUsuario(USUARIO_LOGADO, true);
		criaGrupoComOsIntegrantes(true, "Grupo Infra", usuario);
		Usuario usuario1 = criaUsuario("usuario1", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1);
		
		GrupoJSON grupoJSON = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));
		
		grupoRS.atualizar(grupo.getId(), grupoJSON);
		
		Response grupoResponse = grupoRS.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(2, grupoAtualizado.getUsuarios().size());
		assertEquals(usuario1.getUsername(), grupoAtualizado.getProprietario());
	}
	
	@Test
	public void testaRemoverMembroProprietarioPeloUsuarioInfra() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criaUsuario("Usuario 1", true);
		Usuario usuario2 = criaUsuario("Usuario 2", true);
		Usuario proprietario = criaUsuario("Usuario 3", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo1", proprietario, usuario2, usuario1);
		
		Usuario usuarioInfra = criaUsuario(USUARIO_LOGADO, true);
		criaGrupoComOsIntegrantes(true, "Grupo Infra", usuarioInfra);
		
		GrupoJSON grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(3, grupojson.getUsuarios().size());
		
		grupojson = removerMembrodoGrupo(proprietario, grupo);
				
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());
		assertFalse(grupojson.getProprietario().equals(proprietario.getUsername()));
	}
	
	@Test
	public void testaNaoPodeAlterarGrupoInfra() throws EntityNotFoundException {
		Usuario usuario = criaUsuario("usuarioX", true);
		criaGrupoComOsIntegrantes(true, "Grupo X", usuario);

		Usuario usuario1 = criaUsuario("usuario1", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1);
		GrupoJSON grupoJSON = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));
		
		grupoRS.atualizar(grupo.getId(), grupoJSON);
		
		Response grupoResponse = grupoRS.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(1, grupoAtualizado.getUsuarios().size());
	}
	
	@Test
	public void testaAlterarGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String nomeAlterado = "Grupo alterado";
		String descricao = "Grupo teste";
		String descricaoAlterada = "Grupo teste alterado";
		String nomeMembro = "JoaoDextrano";
		String nomeMembroAlterado = "JoseDextrano";
		String email = "teste@dextra-sw.com";

		Usuario usuario = new Usuario(nomeMembro);
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRS.obtemUsernameUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) grupoRS.obter(grupo.getId())
				.getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado,
				email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		grupoRS.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = grupoRS.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nomeAlterado);
		assertEquals(grupojson.getDescricao(), descricaoAlterada);
		assertEquals(grupojson.getUsuarios().get(0).getNome(),
				nomeMembroAlterado);
	}

	@Test
	public void testaAlterarGrupoPeloUsuarioInfra() throws EntityNotFoundException {
		String nome = "Grupo A";
		String nomeAlterado = "Grupo alterado";
		String descricao = "Grupo teste";
		String descricaoAlterada = "Grupo teste alterado";
		String nomeMembro = "JoaoDextrano";
		String nomeMembroAlterado = "JoseDextrano";
		String email = "teste@dextra-sw.com";

		Usuario usuario = new Usuario(nomeMembro);
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRS.obtemUsernameUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) grupoRS.obter(grupo.getId())
				.getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado,
				email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		grupoRS.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = grupoRS.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nomeAlterado);
		assertEquals(grupojson.getDescricao(), descricaoAlterada);
		assertEquals(grupojson.getUsuarios().get(0).getNome(),
				nomeMembroAlterado);
	}

	@Test
	public void testaNaoPodeRemoverMembroGrupoPorMembrodeOutroGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criaUsuario("Usuario 1", true);
		Usuario usuario2 = criaUsuario("Usuario 2", true);
		Usuario proprietario = criaUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo1", proprietario, usuario2);
		criaGrupoComOsIntegrantes(false, "OutroGrupo", usuario1);
		
		USUARIO_LOGADO = usuario1.getUsername();
		GrupoJSON grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(2, grupojson.getUsuarios().size());
		
		List<UsuarioJSON> usuarios = grupojson.getUsuarios();
		UsuarioJSON usuarioExcluir = null;
		for (UsuarioJSON usuarioJSON : usuarios) {
			if (usuarioJSON.getId().equals(usuario2.getId())) {
				usuarioExcluir = usuarioJSON;
			}
		}
		usuarios.remove(usuarioExcluir);
		grupojson.setUsuarios(usuarios);
		
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());
		
		USUARIO_LOGADO = "login.google";
	}

	@Test
	public void testaRemoverProprietarioGrupoPorOutroMembrodoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario proprietario = criaUsuario("Usuario 1", true);
		Usuario usuario2 = criaUsuario("Usuario 2", true);
		Usuario usuario1 = criaUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo1", proprietario, usuario1, usuario2);
		
		GrupoJSON grupojson = removerMembrodoGrupo(proprietario, grupo);
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());
		assertFalse(grupojson.getProprietario().equals(proprietario.getUsername()));
	}
	
	@Test
	public void testaRemoverMembroGrupoPorOutroMembrodoGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criaUsuario("Usuario 1", true);
		Usuario usuario2 = criaUsuario("Usuario 2", true);
		Usuario proprietario = criaUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo1", proprietario, usuario1, usuario2);

		USUARIO_LOGADO = usuario1.getUsername();
		GrupoJSON grupojson = removerMembrodoGrupo(usuario2, grupo);
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(2, grupojson.getUsuarios().size());
		
		USUARIO_LOGADO = "login.google";
	}

	@Test
	public void testaRemoverMembroGrupoPorUsuarioInfra() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario1 = criaUsuario("Usuario 1", true);
		Usuario usuario2 = criaUsuario("Usuario 2", true);
		Usuario proprietario = criaUsuario(USUARIO_LOGADO, true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo1", usuario1, usuario2);
		criaGrupoComOsIntegrantes(true, "Grupo2", proprietario);

		GrupoJSON grupojson = removerMembrodoGrupo(usuario2, grupo);
		Response response = grupoRS.atualizar(grupo.getId(), grupojson);
		grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals(1, grupojson.getUsuarios().size());
	}
	
	@Test
	public void testaObterGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRS.obtemUsernameUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		Response response = grupoRS.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nome);
		assertEquals(grupojson.getDescricao(), descricao);
		assertEquals(grupojson.getUsuarios().get(0).getNome(),
				usuario.getNome());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testaListar() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		String nome = "Grupo A";
		Usuario usuario = criaUsuario(USUARIO_LOGADO, true);
		criaGrupoComOsIntegrantes(false, "Grupo A", usuario);
		
		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		for (GrupoJSON grupojson : gruposjson) {
			assertEquals(grupojson.getNome(), nome);
			assertEquals(grupojson.getUsuarios().get(0).getNome(), usuario.getNome());
		}
	}

	@Test
	public void testaNaoPodeRemoverGrupoPorMembrodoGrupoNaoProprietario() throws EntityNotFoundException, IOException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario membro = criaUsuario(USUARIO_LOGADO, true);
		Usuario proprietario = criaUsuario("proprietario", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", proprietario, membro);
		
		Response response = grupoRS.deletar(grupo.getId());
		
		assertEquals(Status.FORBIDDEN.getStatusCode(), response.getStatus());
	}
	
	@Test
	public void testaRemoverGrupo() throws EntityNotFoundException, IOException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRS.obtemUsernameUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		new ServicoRepository().persiste(new Servico("Google Grupos"));
		grupoRS.deletar(grupo.getId());

		try {
			repositorioGrupo.obtemPorId(grupo.getId());
		} catch (EntityNotFoundException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testaListagemDeGrupoComIntegranteRemovido()
			throws EntityNotFoundException {
		Usuario lulao = criaUsuario("lulao", true);
		Usuario dudi = criaUsuario("dudi", true);
		criaGrupoComOsIntegrantes(false, "VamoBugrao", lulao, dudi);

		this.removeUmDosUsuariosDoGrupo(lulao);

		assertEquals(200, grupoRS.listar().getStatus());
	}

	@Test
	public void testaUsuarioLogadoNaoPodeEditarGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		criaUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criaUsuario("usuario1", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = grupoRS.obter(grupo.getId());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(false, grupojson.isEditarGrupo().booleanValue());
	}
	
	@Test
	public void testaUsuarioLogadoPodeEditarGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criaUsuario(USUARIO_LOGADO, true);
		Usuario usuarioMembro = criaUsuario("membroInfra", true);
		criaGrupoComOsIntegrantes(true, "Grupo Infra", usuarioMembro, usuario);

		Usuario usuario1 = criaUsuario("usuario1", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = grupoRS.obter(grupo.getId());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		
		assertEquals(true, grupojson.isEditarGrupo().booleanValue());
	}
	
	@Test
	public void testaMembrodoGrupoTemAcessoParaEditarGrupo() throws EntityNotFoundException {
		System.out.println("----->>>testaMembrodoGrupoTemAcessoParaEditarGrupo");
		limpaUsuariosInseridos(usuarioRepository);
		Usuario proprietario = criaUsuario("usuario", true);
		Usuario membro = criaUsuario(USUARIO_LOGADO, true);

		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", proprietario, membro);
		
		Response response = grupoRS.obter(grupo.getId());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		System.out.println("Editargrupo finalmente??? ---->> " + grupojson.isEditarGrupo());
		assertEquals(Boolean.TRUE, grupojson.isEditarGrupo());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testaMembroNaoPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario membro = criaUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criaUsuario("usuario1", true);
		criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, membro);
		
		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		assertEquals(false, gruposjson.get(0).isExcluirGrupo().booleanValue());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoNaoPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		criaUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criaUsuario("usuario1", true);
		criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		assertEquals(false, gruposjson.get(0).isExcluirGrupo().booleanValue());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoInfraPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criaUsuario(USUARIO_LOGADO, true);
		Usuario usuarioMembroInfra = criaUsuario("membroInfra", true);
		criaGrupoComOsIntegrantes(true, "Grupo Infra", usuarioMembroInfra, usuario);

		Usuario usuario1 = criaUsuario("usuario1", true);
		criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = grupoRS.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		
		assertEquals(true, gruposjson.get(0).isExcluirGrupo().booleanValue());
	}
	
	private GrupoJSON removerMembrodoGrupo(Usuario membro, Grupo grupo) throws EntityNotFoundException {
		GrupoJSON grupojson = (GrupoJSON) grupoRS.obter(grupo.getId()).getEntity();
		
		List<UsuarioJSON> usuarios = grupojson.getUsuarios();
		UsuarioJSON usuarioExcluir = null;
		for (UsuarioJSON usuarioJSON : usuarios) {
			if (usuarioJSON.getId().equals(membro.getId())) {
				usuarioExcluir = usuarioJSON;
			}
		}
		usuarios.remove(usuarioExcluir);
		grupojson.setUsuarios(usuarios);
		return grupojson;
	}

	private void removeUmDosUsuariosDoGrupo(Usuario usuario) {
		usuarioRepository.remove(usuario.getId());
	}


	public class GrupoRSFake extends GrupoRS {
		@Override
		protected String obtemUsernameUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}
}
