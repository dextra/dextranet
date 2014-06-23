package br.com.dextra.dextranet.seguranca;

import java.util.ArrayList;
import java.util.List;

import br.com.dextra.dextranet.grupo.Grupo;
import br.com.dextra.dextranet.grupo.GrupoRepository;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoService {
	private static GrupoRepository grupoRepository = new GrupoRepository();
	
	public static String identificacaoDoUsuarioLogado() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser().getNickname();
	}
	
	public static Boolean isUsuarioGrupoInfra() {
		String logado = identificacaoDoUsuarioLogado();
		List<Grupo> grupos = new ArrayList<Grupo>();
		try {
			grupos = grupoRepository.obtemPorNickUsuario(logado);
			for (Grupo grupo : grupos) {
				if (grupo.isInfra()) {
					return true;
				}
			}
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
