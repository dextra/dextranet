package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;

public class GrupoRSTest extends TesteIntegracaoBase {
	private GrupoRepository repositorioGrupo = new GrupoRepository();
	private MembroRepository repositorioMembro = new MembroRepository();
	private UsuarioRepository usuarioRepository = new UsuarioRepository();
	private GrupoRS rest = new GrupoRSFake();

	private static final String USUARIO_LOGADO = "usuarioDextranet";

	@After
	public void removeDadosInseridos() {
		this.limpaGrupoInseridos(repositorioGrupo);
		this.limpaMembroInseridos(repositorioMembro);
	}

	@Test
	public void testeAdicionarGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		UsuarioJSON uMembro = new UsuarioJSON(usuario.getId(), usuario.getNome());

		List<UsuarioJSON> uMembros = new ArrayList<UsuarioJSON>();
		uMembros.add(uMembro);
		GrupoJSON grupojson = new GrupoJSON(null, nome, descricao, uMembros);
		Response response = rest.adicionar(grupojson);

		Assert.assertEquals(response.getStatus(), 200);
	}

	@Test
	public void testeObterGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(), usuario.getNome()));
		Response obter = rest.obter(grupo.getId());
		Assert.assertNotNull(obter.getEntity());
		GrupoJSON grupojson = (GrupoJSON)obter.getEntity();
		Assert.assertEquals(grupojson.getNome(), nome);
		Assert.assertEquals(grupojson.getDescricao(), descricao);
		Assert.assertEquals(grupojson.getUsuarios().get(0).getNome(), usuario.getNome());
	}

	@Test
	public void testeListar() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(), usuario.getNome()));

		Response response = rest.listar();

		List<GrupoJSON> gruposjson = (List<GrupoJSON>) response.getEntity();
		for (GrupoJSON grupojson : gruposjson) {
			Assert.assertEquals(grupojson.getNome(), nome);
			Assert.assertEquals(grupojson.getDescricao(), descricao);
			Assert.assertEquals(grupojson.getUsuarios().get(0).getNome(), usuario.getNome());
		}
	}

	@Test
	public void testeRemoverGrupo() throws EntityNotFoundException {
		String nome = "Grupo A";
		String descricao = "Grupo teste";
		Usuario usuario = new Usuario("JoaoDextrano");
		usuario = usuarioRepository.persiste(usuario);
		Grupo grupo = new Grupo(nome, descricao, rest.obtemUsuarioLogado());
		grupo = repositorioGrupo.persiste(grupo);
		repositorioMembro.persiste(new Membro(usuario.getId(), grupo.getId(), usuario.getNome()));
		rest.deletar(grupo.getId());

		try {
			repositorioGrupo.obtemPorId(grupo.getId());
		} catch (EntityNotFoundException e) {
			Assert.assertTrue(true);
		}
	}

	public class GrupoRSFake extends GrupoRS {
		@Override
		protected String obtemUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}
}
