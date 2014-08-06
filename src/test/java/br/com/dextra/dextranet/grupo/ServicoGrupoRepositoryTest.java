package br.com.dextra.dextranet.grupo;

import static org.junit.Assert.*;
import static br.com.dextra.dextranet.persistencia.DadosUtils.*;

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
		Usuario usuario1 = criaUsuario("Usuario1", true);
		Usuario usuario2 = criaUsuario("Usuario2", true);
		Usuario usuario3 = criaUsuario("Usuario3", true);
		Usuario usuario4 = criaUsuario("Usuario4", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario2, usuario3, usuario4);
		Grupo grupo2 = criaGrupoComOsIntegrantes(false, "Grupo 2", usuario1, usuario2, usuario3, usuario4);
		Servico servico = servicoRepository.persiste(new Servico("Google Grupos"));
		ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getId(), grupo.getId(), "grupo@email.com");
		servicoGrupoRepository.persiste(servicoGrupo);
		ServicoGrupo servicoGrupo1 = new ServicoGrupo(servico.getId(), grupo2.getId(), "grupo@email.com");
		servicoGrupoRepository.persiste(servicoGrupo1);
		
		List<ServicoGrupo> servicosGrupo = servicoGrupoRepository.obtemPorIdGrupo(grupo.getId());
		
		assertEquals(1, servicosGrupo.size());
	}
	
	@Test
	public void testarObtemPorIdServico() throws EntityNotFoundException {
		Usuario usuario1 = criaUsuario("Usuario1", true);
		Usuario usuario2 = criaUsuario("Usuario2", true);
		Usuario usuario3 = criaUsuario("Usuario3", true);
		Usuario usuario4 = criaUsuario("Usuario4", true);
		Grupo grupo = criaGrupoComOsIntegrantes(false, "Grupo 1", usuario1, usuario2, usuario3, usuario4);
		Grupo grupo2 = criaGrupoComOsIntegrantes(false, "Grupo 2", usuario1, usuario2, usuario3, usuario4);
		
		Servico servico = servicoRepository.persiste(new Servico("Google Grupos"));
		ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getId(), grupo.getId(), "grupo@email.com");
		servicoGrupoRepository.persiste(servicoGrupo);
		ServicoGrupo servicoGrupo1 = new ServicoGrupo(servico.getId(), grupo2.getId(), "grupo@email.com");
		servicoGrupoRepository.persiste(servicoGrupo1);
		
		assertNotNull(servicoGrupoRepository.obtemPor(servicoGrupo.getIdServico(), grupo.getId()));
	}
}
