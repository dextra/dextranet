package br.com.dextra.dextranet.seguranca;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoService {
	private static GrupoRepository grupoRepository = new GrupoRepository();
	private static UsuarioRepository usuarioRepository = new UsuarioRepository();
	
	public static String identificacaoDoUsuarioLogado() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser().getNickname();
	}
	
	public static Boolean isUsuarioGrupoInfra() {
		Usuario usuario = usuarioRepository.obtemPorUsername(identificacaoDoUsuarioLogado());
		List<Grupo> grupos = new ArrayList<Grupo>();
		try {
			grupos = grupoRepository.obtemPorIdIntegrante(usuario.getId());
			for (Grupo grupo : grupos) {
				if (isInfra(grupo)) {
					return true;
				}
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	private static boolean isInfra(Grupo grupo) {
		return grupo.isInfra() != null && grupo.isInfra();
	}
}
