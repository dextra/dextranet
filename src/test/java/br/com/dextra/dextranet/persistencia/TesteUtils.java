package br.com.dextra.dextranet.persistencia;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.grupo.Membro;
import br.com.dextra.dextranet.grupo.MembroRepository;
import br.com.dextra.dextranet.grupo.ServicoGrupo;
import br.com.dextra.dextranet.grupo.ServicoGrupoRepository;
import br.com.dextra.dextranet.grupo.servico.Servico;
import br.com.dextra.dextranet.grupo.servico.ServicoRepository;
import br.com.dextra.dextranet.grupo.servico.google.Aprovisionamento;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.api.services.admin.directory.model.Group;

public class TesteUtils {

	private static GrupoRepository grupoRepositorio = new GrupoRepository();
	private static UsuarioRepository usuarioRepositorio = new UsuarioRepository();
	private static MembroRepository membroRepository = new MembroRepository();
	private static ServicoRepository servicoRepository = new ServicoRepository();
	private static ServicoGrupoRepository servicoGrupoRepository = new ServicoGrupoRepository();
	private static Aprovisionamento aprovisionamento = new Aprovisionamento();

	private static Servico getServico() {
		List<Servico> servicos = servicoRepository.lista();
		if (servicos.size() == 0) {
			Servico servico = new Servico("Grupo Email");
			servico = servicoRepository.persiste(servico);
			return servico;
		}
		return servicos.get(0);
	}

	public static Grupo criarGrupoComOsIntegrantes(String nomeEmailGrupo, Boolean isInfra, String nomeDoGrupo,
			Boolean addProprietarioComoMembro, Usuario... integrantes) {
		Grupo novoGrupo = new Grupo(nomeDoGrupo, nomeDoGrupo, integrantes[0].getUsername());
		novoGrupo.setInfra(isInfra);
		novoGrupo = grupoRepositorio.persiste(novoGrupo);

		Servico servico = getServico();
		ServicoGrupo servicoGrupo = new ServicoGrupo(servico.getId(), novoGrupo.getId(), nomeEmailGrupo);
		servicoGrupo = servicoGrupoRepository.persiste(servicoGrupo);

		novoGrupo.setServicoGrupos(Arrays.asList(servicoGrupo));

		int cont = 0;
		for (Usuario integrante : integrantes) {
			if (!addProprietarioComoMembro && cont == 0) {
				cont++;
				continue;
			} else {
				membroRepository.persiste(new Membro(integrante.getId(), novoGrupo.getId(), integrante.getNome(),
						integrante
								.getUsername()));
				cont++;
			}
		}

		return novoGrupo;
	}

	public static void removerUsuario(String username) {
		usuarioRepositorio.remove(buscarUsuario(username).getId());
	}

	public static Usuario buscarUsuario(String username) {
		return usuarioRepositorio.obtemPorUsername(username);
	}

	public static Usuario criarUsuario(String username, Boolean isAtivo) {
		try {
			Usuario usuario = usuarioRepositorio.obtemPorUsername(username);
			return usuario;
		} catch (EntidadeNaoEncontradaException e) {
			Usuario novoUsuario = new Usuario(username);
			novoUsuario.setAtivo(isAtivo);
			novoUsuario = usuarioRepositorio.persiste(novoUsuario);
			return novoUsuario;
		}
	}

	public static Group buscarGrupoGoogle(String emailGrupo) throws IOException, GeneralSecurityException,
			URISyntaxException {
		return aprovisionamento.obterGrupo(emailGrupo);
	}

	public static Group criarGrupoGoogle(String emailGrupo) throws IOException, GeneralSecurityException,
			URISyntaxException {
		String nomeGrupo = "Grupo 1";
		String descricaoGrupo = "Grupo descrição";

		return aprovisionamento.criarGrupo(nomeGrupo, emailGrupo, descricaoGrupo);
	}

	public static void adicionarMembroGrupoGoogle(List<String> emailMembros, String emailGrupo) throws IOException,
			GeneralSecurityException, URISyntaxException {

		Group group = aprovisionamento.obterGrupo(emailGrupo);

		aprovisionamento.adicionarMembros(emailMembros, group);
	}
}
