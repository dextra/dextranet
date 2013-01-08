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

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {


        UserService userService = UserServiceFactory.getUserService();

        String thisURI = ((HttpServletRequest)request).getRequestURI();
        if(userService.getCurrentUser() != null){
    		filterChain.doFilter(request, response);

        }else{

            String loginUrl = userService.createLoginURL(thisURI);
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.sendRedirect(loginUrl);
    		filterChain.doFilter(request, response);
        }


	}

	private boolean uriExcludedFromFilter(String uri) {
		if(uri.startsWith("/_ah")){
			return true;
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
