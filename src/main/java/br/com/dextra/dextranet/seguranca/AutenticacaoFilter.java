package br.com.dextra.dextranet.seguranca;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dextra.dextranet.persistencia.EntidadeNaoEncontradaException;
import br.com.dextra.dextranet.usuario.Usuario;
import br.com.dextra.dextranet.usuario.UsuarioRepository;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoFilter implements Filter {

	protected String excludePatterns = "";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		UserService userService = UserServiceFactory.getUserService();
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String thisURI = httpRequest.getRequestURI();

		User usuarioLogado = userService.getCurrentUser();
		if (usuarioLogado != null) {

			if (acessoNaPaginaPrincipal(thisURI)) {
				this.verificaExistenciaDoUsuarioLogado(usuarioLogado);
			}

			filterChain.doFilter(request, response);
		} else if (urlDeveSerIgnorada(thisURI)) {
			filterChain.doFilter(request, response);
		} else {
			String loginUrl = userService.createLoginURL(thisURI);
			httpResponse.sendRedirect(loginUrl);
		}
	}

	protected boolean urlDeveSerIgnorada(String thisURI) {
		String[] urlsIgnorar = excludePatterns.split(";");
		for (String url : urlsIgnorar) {
			if (thisURI.contains(url)) {
				return true;
			}
		}

		return false;
	}

	private boolean acessoNaPaginaPrincipal(String thisURI) {
		return "/index.html".equals(thisURI) || "/".equals(thisURI);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.excludePatterns = filterConfig.getInitParameter("excludePatterns");
	}

	@Override
	public void destroy() {
	}

	private void verificaExistenciaDoUsuarioLogado(User usuarioLogado) {
		UsuarioRepository usuarioRepositorio = new UsuarioRepository();
		String username = usuarioLogado.getNickname();

		try {
			usuarioRepositorio.obtemPorUsername(username);
		} catch (EntidadeNaoEncontradaException e) {
			usuarioRepositorio.persiste(new Usuario(username));
		}

	}

}
