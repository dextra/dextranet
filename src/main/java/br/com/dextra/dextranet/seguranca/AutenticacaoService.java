package br.com.dextra.dextranet.seguranca;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoService {

	public static String identificacaoDoUsuarioLogado() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser().getNickname();
	}

}
