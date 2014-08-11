package br.com.dextra.dextranet.grupo;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static br.com.dextra.dextranet.persistencia.DadosUtils.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

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
	private GrupoRS grupoRs = new GrupoRSFake();

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
		Response response = grupoRs.adicionar(grupojson);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	public void testaAlterarGrupoInfra() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criaUsuario(USUARIO_LOGADO, true);
		criaGrupoComOsIntegrantes(true, "Grupo Infra", usuario);

		Usuario usuario1 = criaUsuario("usuario1", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1);
		GrupoJSON grupoJSON = (GrupoJSON) grupoRs.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));
		
		grupoRs.atualizar(grupo.getId(), grupoJSON);
		
		Response grupoResponse = grupoRs.obter(grupo.getId());
		GrupoJSON grupoAtualizado = (GrupoJSON) grupoResponse.getEntity();
		assertEquals(2, grupoAtualizado.getUsuarios().size());
		assertEquals(usuario1.getUsername(), grupoAtualizado.getProprietario());
	}
	
	@Test
	public void testaNaoPodeAlterarGrupoInfra() throws EntityNotFoundException {
		Usuario usuario = criaUsuario("usuarioX", true);
		criaGrupoComOsIntegrantes(true, "Grupo X", usuario);

		Usuario usuario1 = criaUsuario("usuario1", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1);
		GrupoJSON grupoJSON = (GrupoJSON) grupoRs.obter(grupo.getId()).getEntity();
		List<UsuarioJSON> usuarios = grupoJSON.getUsuarios();
		usuarios.add(new UsuarioJSON(null, "Usuario 2", "usuario@dextra-sw.com"));
		
		grupoRs.atualizar(grupo.getId(), grupoJSON);
		
		Response grupoResponse = grupoRs.obter(grupo.getId());
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
		Grupo grupo = new Grupo(nome, descricao, grupoRs.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) grupoRs.obter(grupo.getId())
				.getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado,
				email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		grupoRs.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = grupoRs.obter(grupo.getId());
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
		Grupo grupo = new Grupo(nome, descricao, grupoRs.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));

		GrupoJSON grupojsonAtualizar = (GrupoJSON) grupoRs.obter(grupo.getId())
				.getEntity();
		grupojsonAtualizar.setNome(nomeAlterado);
		grupojsonAtualizar.setDescricao(descricaoAlterada);
		UsuarioJSON usuariojson = new UsuarioJSON(null, nomeMembroAlterado,
				email);
		List<UsuarioJSON> usuariosjson = new ArrayList<UsuarioJSON>();
		usuariosjson.add(usuariojson);
		grupojsonAtualizar.setUsuarios(usuariosjson);

		grupoRs.atualizar(grupojsonAtualizar.getId(), grupojsonAtualizar);

		Response response = grupoRs.obter(grupo.getId());
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
		Grupo grupo = new Grupo(nome, descricao, grupoRs.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		Response response = grupoRs.obter(grupo.getId());
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
		
		Response response = grupoRs.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		for (GrupoJSON grupojson : gruposjson) {
			assertEquals(grupojson.getNome(), nome);
			assertEquals(grupojson.getUsuarios().get(0).getNome(), usuario.getNome());
		}
	}

	@Test
	public void testaRemoverGrupo() throws EntityNotFoundException, IOException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		String email = "teste@dextra-sw.com";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, grupoRs.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(),
				usuario.getNome(), email));
		new ServicoRepository().persiste(new Servico("Google Grupos"));
		grupoRs.deletar(grupo.getId());

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

		assertEquals(200, grupoRs.listar().getStatus());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoNaoPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		criaUsuario(USUARIO_LOGADO, true);
		Usuario usuario1 = criaUsuario("usuario1", true);
		criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = grupoRs.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		assertEquals(false, gruposjson.get(0).getExcluirGrupo().booleanValue());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testaUsuarioLogadoPodeExcluirGrupo() throws EntityNotFoundException {
		limpaUsuariosInseridos(usuarioRepository);
		Usuario usuario = criaUsuario(USUARIO_LOGADO, true);
		Usuario usuarioMembroInfra = criaUsuario("membroInfra", true);
		criaGrupoComOsIntegrantes(true, "Grupo Infra", usuarioMembroInfra, usuario);

		Usuario usuario1 = criaUsuario("usuario1", true);
		criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario1);
		
		Response response = grupoRs.listar();
		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		
		assertEquals(true, gruposjson.get(0).getExcluirGrupo().booleanValue());
	}
	
	private void removeUmDosUsuariosDoGrupo(Usuario usuario) {
		usuarioRepository.remove(usuario.getId());
	}


	public class GrupoRSFake extends GrupoRS {
		@Override
		protected String obtemUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}
}
