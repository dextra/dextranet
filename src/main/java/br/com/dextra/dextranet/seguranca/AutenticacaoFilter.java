package br.com.dextra.dextranet.seguranca;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class AutenticacaoFilter implements Filter {

	Logger log = LoggerFactory.getLogger(AutenticacaoFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {


        UserService userService = UserServiceFactory.getUserService();

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String thisURI = httpRequest.getRequestURI();

        if(userService.getCurrentUser() != null){
    		filterChain.doFilter(request, response);

        }else{
            String loginUrl = userService.createLoginURL(thisURI);
            httpRequest.getRequestDispatcher(loginUrl).forward(request, response);
        }


	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
