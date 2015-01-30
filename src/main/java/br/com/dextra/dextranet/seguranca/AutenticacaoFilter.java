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
	private UsuarioRepository usuarioRepositorio = new UsuarioRepository();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		UserService userService = UserServiceFactory.getUserService();
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String thisURI = httpRequest.getRequestURI();

		User usuarioLogado = userService.getCurrentUser();
		Usuario usuario = null;
		if (usuarioLogado != null) {
			usuario = verificarAcessoNaPrimeiraPagina(thisURI, usuarioLogado);
			redirecionaUsuario(httpRequest, httpResponse, filterChain, usuario);
		} else if (urlDeveSerIgnorada(thisURI)) {
			filterChain.doFilter(httpRequest, httpResponse);
		} else {
			String loginUrl = userService.createLoginURL(thisURI);
			httpResponse.sendRedirect(loginUrl);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.excludePatterns = filterConfig.getInitParameter("excludePatterns");
	}

	@Override
	public void destroy() {
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

	private Usuario verificaExistenciaDoUsuarioLogado(User usuarioLogado) {
		String username = usuarioLogado.getNickname();

		try {
			return usuarioRepositorio.obtemPorUsername(username);
		} catch (EntidadeNaoEncontradaException e) {
			return usuarioRepositorio.persiste(new Usuario(username));
		}

	}

	private void redirecionaUsuario(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Usuario usuario)
	        throws IOException, ServletException {
		if (usuario.isAtivo() == null || usuario.isAtivo()) {
			filterChain.doFilter(request, response);
		} else {
			response.sendRedirect("/403.html");
		}
	}

	private Usuario verificarAcessoNaPrimeiraPagina(String thisURI, User usuarioLogado) {
		Usuario usuario;
		if (acessoNaPaginaPrincipal(thisURI)) {
			usuario = this.verificaExistenciaDoUsuarioLogado(usuarioLogado);
		} else {
			usuario = usuarioRepositorio.obtemPorUsername(usuarioLogado.getNickname());
		}
		return usuario;
	}

	private boolean acessoNaPaginaPrincipal(String thisURI) {
		return "/index.html".equals(thisURI) || "/".equals(thisURI);
	}

}
