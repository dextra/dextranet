package br.com.dextra.dextranet.grupo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import br.com.dextra.dextranet.grupos.GrupoRS;
import br.com.dextra.dextranet.grupos.GrupoRepository;
import br.com.dextra.dextranet.grupos.MembroRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;
import br.com.dextra.teste.TesteIntegracaoBase;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.JsonArray;
import com.googlecode.mycontainer.commons.util.JsonUtil;

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
		UsuarioMembro uMembro = new UsuarioMembro(usuario.getId(), usuario.getNome());

		List<UsuarioMembro> uMembros = new ArrayList<UsuarioMembro>();
		uMembros.add(uMembro);

		rest.adicionar(nome, descricao, uMembros);
		JsonArray jsonArray = JsonUtil.parse(rest.listar().getEntity().toString()).getAsJsonArray();

		Assert.assertEquals(jsonArray.size(), 1);
	}

	public class GrupoRSFake extends GrupoRS {
		@Override
		protected String obtemUsuarioLogado() {
			return USUARIO_LOGADO;
		}
	}
}
