package br.com.dextra.dextranet.grupo;

import static org.junit.Assert.*;
import static br.com.dextra.dextranet.persistencia.TesteUtils.*;

import java.util.List;

import org.junit.Test;

import com.google.appengine.api.datastore.EntityNotFoundException;

import br.com.dextra.dextranet.grupo.servico.Servico;
import br.com.dextra.dextranet.grupo.servico.ServicoRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.teste.TesteIntegracaoBase;

public class ServicoGrupoRepositoryTest extends TesteIntegracaoBase {
	ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();
	GrupoRepository grupoRepository = new GrupoRepository();
	ServicoRepository servicoRepository = new ServicoRepository();
	
	@Test
	public void testarObtemPorIdGrupo() throws EntityNotFoundException {
		Usuario usuario1 = criarUsuario("Usuario1", true);
		Usuario usuario2 = criarUsuario("Usuario2", true);
		Usuario usuario3 = criarUsuario("Usuario3", true);
		Usuario usuario4 = criarUsuario("Usuario4", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario2, usuario3, usuario4);
		criarGrupoComOsIntegrantes("grupo2", false, "Grupo 2", true, usuario1, usuario2, usuario3, usuario4);
	
		List<ServicoGrupo> servicosGrupo = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		
		assertEquals(1, servicosGrupo.size());
	}
	
	@Test
	public void testarObtemPorIdServico() throws EntityNotFoundException {
		Usuario usuario1 = criarUsuario("Usuario1", true);
		Usuario usuario2 = criarUsuario("Usuario2", true);
		Usuario usuario3 = criarUsuario("Usuario3", true);
		Usuario usuario4 = criarUsuario("Usuario4", true);
		Grupo grupo = criarGrupoComOsIntegrantes("grupo1", false, "Grupo 1", true, usuario1, usuario2, usuario3, usuario4);
		criarGrupoComOsIntegrantes("grupo2", false, "Grupo 2", true, usuario1, usuario2, usuario3, usuario4);
		
		Servico servico = servicoRepository.lista().get(0);
		assertNotNull(servicoGrupoRepository.obtemPor(servico.getId(), grupo.getId()));
	}
}
