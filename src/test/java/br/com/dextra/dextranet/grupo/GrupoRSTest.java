package br.com.dextra.dextranet.grupo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import static junit.framework.Assert.*;

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
	private GrupoRS rest = new GrupoRSFake();

	private static final String USUARIO_LOGADO = "login.google";

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
		Response response = rest.adicionar(grupojson);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	public void testaAlterarGrupoInfra() throws EntityNotFoundException {
		Usuario usuario = this.criaUsuario(USUARIO_LOGADO);
		this.criaGrupoComOsIntegrantes(true, "Grupo Infra", usuario);

		Usuario usuario1 = this.criaUsuario("usuario1");
		Grupo grupo = this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1);
		GrupoJSON grupoJSON = (GrupoJSON) rest.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));
		
		rest.atualizar(grupo.getId(), grupoJSON);
		
		Response grupoResponse = rest.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(2, grupoAtualizado.getUsuarios().size());
	}

	@Test
	public void testaNaoPodeAlterarGrupoInfra() throws EntityNotFoundException {
		Usuario usuario = this.criaUsuario("usuarioX");
		this.criaGrupoComOsIntegrantes(true, "Grupo X", usuario);

		Usuario usuario1 = this.criaUsuario("usuario1");
		Grupo grupo = this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1);
		GrupoJSON grupoJSON = (GrupoJSON) rest.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));
		
		rest.atualizar(grupo.getId(), grupoJSON);
		
		Response grupoResponse = rest.obter(grupo.getId());
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
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) rest.obter(grupo.getId())
				.getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado,
				email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		rest.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = rest.obter(grupo.getId());
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
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) rest.obter(grupo.getId())
				.getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado,
				email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		rest.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = rest.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nomeAlterado);
		assertEquals(grupojson.getDescricao(), descricaoAlterada);
		assertEquals(grupojson.getUsuarios().get(0).getNome(),
				nomeMembroAlterado);
	}
	
	@Test
	public void testaObterGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		Response response = rest.obter(grupo.getId());
		assertNotNull(response.getEntity());
		GrupoJSON grupojson = (GrupoJSON) response.getEntity();
		assertEquals(grupojson.getNome(), nome);
		assertEquals(grupojson.getDescricao(), descricao);
		assertEquals(grupojson.getUsuarios().get(0).getNome(),
				usuario.getNome());
	}

	@Test
	public void testaListar() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		Response response = rest.listar();
		@SuppressWarnings("unchecked")
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		for (GrupoJSON grupojson : gruposjson) {
			assertEquals(grupojson.getNome(), nome);
			assertEquals(grupojson.getDescricao(), descricao);
			assertEquals(grupojson.getUsuarios().get(0).getNome(),
					usuario.getNome());
		}
	}

	@Test
	public void testaRemoverGrupo() throws EntityNotFoundException, IOException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		new ServicoRepository().persiste(new Servico("Google Grupos"));
		rest.deletar(grupo.getId());

		try {
			repositorioGrupo.obtemPorId(grupo.getId());
		} catch (EntityNotFoundException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testaPopularFlag() throws EntityNotFoundException, IOException {
		Usuario usuario = this.criaUsuario(USUARIO_LOGADO);
		Grupo infra = this.criaGrupoComOsIntegrantes(false, "Infra", usuario, usuario);
		
		Usuario usuario1 = this.criaUsuario("Usuario 1");
		Grupo grupo1 = this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Usuario usuario2 = this.criaUsuario("Usuario 1");
		Grupo grupo2 = this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario2, usuario2);
		
		Usuario usuario3 = this.criaUsuario("Usuario 1");
		Grupo grupo3 = this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario3, usuario3);
		
		Usuario usuario4 = this.criaUsuario("Usuario 1");
		Grupo grupo4 = this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario4, usuario);
		
		rest.popularFlag();
		
		infra = repositorioGrupo.obtemPorId(infra.getId());
		grupo1 = repositorioGrupo.obtemPorId(grupo1.getId());
		grupo2 = repositorioGrupo.obtemPorId(grupo2.getId());
		grupo3 = repositorioGrupo.obtemPorId(grupo3.getId());
		grupo4 = repositorioGrupo.obtemPorId(grupo4.getId());
		
		assertTrue(infra.isInfra());
		assertFalse(grupo1.isInfra());
		assertFalse(grupo2.isInfra());
		assertFalse(grupo3.isInfra());
		assertFalse(grupo4.isInfra());
	}
	
	@Test
	public void testaListagemDeGrupoComIntegranteRemovido()
			throws EntityNotFoundException {
		Usuario lulao = this.criaUsuario("lulao");
		Usuario dudi = this.criaUsuario("dudi");
		this.criaGrupoComOsIntegrantes(false, "VamoBugrao", lulao, dudi);

		this.removeUmDosUsuariosDoGrupo(lulao);

		assertEquals(200, rest.listar().getStatus());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoNaoPodeExcluirGrupo() throws EntityNotFoundException {
		this.criaUsuario(USUARIO_LOGADO);
		Usuario usuario1 = this.criaUsuario("usuario1");
		this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = rest.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		assertEquals(false, gruposjson.get(0).getExcluirGrupo().booleanValue());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoPodeExcluirGrupo() throws EntityNotFoundException {
		Usuario usuario = this.criaUsuario(USUARIO_LOGADO);
		this.criaGrupoComOsIntegrantes(true, "Grupo Infra", usuario, usuario);

		Usuario usuario1 = this.criaUsuario("usuario1");
		this.criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = rest.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		
		assertEquals(true, gruposjson.get(0).getExcluirGrupo().booleanValue());
	}
	
	private void removeUmDosUsuariosDoGrupo(Usuario usuario) {
		usuarioRepository.remove(usuario.getId());
	}

	private Usuario criaUsuario(String username) {
		Usuario novoUsuario = new Usuario(username);
		novoUsuario = usuarioRepository.persiste(novoUsuario);
		return novoUsuario;
	}
	
	private Grupo criaGrupoComOsIntegrantes(Boolean isInfra, String nomeDoGrupo,
			Usuario... integrantes) {
		Grupo novoGrupo = new Grupo(nomeDoGrupo, nomeDoGrupo,
				integrantes[0].getUsername());
		novoGrupo.setInfra(isInfra);
		novoGrupo = repositorioGrupo.persiste(novoGrupo);

		for (Usuario integrante : integrantes) {
			repositorioMembro.persiste(new Membro(integrante.getId(), novoGrupo
					.getId(), integrante.getNome(), "email"));
		}

		return novoGrupo;
	}

	public class GrupoRSFake extends GrupoRS {
		@Override
		protected String obtemUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}
}
