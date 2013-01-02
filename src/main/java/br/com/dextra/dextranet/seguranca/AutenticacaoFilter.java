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

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {


        UserService userService = UserServiceFactory.getUserService();

        String thisURI = ((HttpServletRequest)request).getRequestURI();


        try{
        	User user = userService.getCurrentUser();
    		filterChain.doFilter(request, response);

        }catch(NullPointerException e){
            String loginUrl = userService.createLoginURL(thisURI);
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.sendRedirect(loginUrl);
        	return ;
        }


	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
