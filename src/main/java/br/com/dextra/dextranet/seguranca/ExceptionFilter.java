package br.com.dextra.dextranet.seguranca;

import java.io.IOException;
import java.util.regex.Pattern;

/*import javax.persistence.PersistenceContext;*/
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;

/*import org.jboss.resteasy.plugins.server.servlet.ServletUtil;

import br.com.dextra.dextranet.utils.JsonUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;*/

/*import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;*/

public class ExceptionFilter implements Filter {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(InitialFilter.class);
	private static final String SERVICE_URL_PATTERN = "";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = HttpServletRequest.class.cast(request);
		HttpServletResponse resp = HttpServletResponse.class.cast(response);

		// Executa antes de ir para o servidor
		try {
			if (isServiceRequest(req)) {
				doTransaction(chain, req, resp);
				return;
			}
			chain.doFilter(req, resp);
		} catch (Exception e) {
			doException(req, resp, e,
					HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

		// Executa na volta do servidor
	}

	private boolean isServiceRequest(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return Pattern.compile(SERVICE_URL_PATTERN)
				.matcher(req.getRequestURI()).find();
	}

	private void doException(HttpServletRequest request,
			HttpServletResponse resp, Throwable cause, int code)
			throws IOException {
		// TODO Auto-generated method stub
		if (resp.isCommitted()) {
			throw new RuntimeException(
					"OMG: browser response is committed. We can not send http code",
					cause);
		}

		resp.setStatus(code);
		// Código de teste

		JsonElement jsonData = null;
		while (cause != null /* && !(cause instanceof MarcacaoException) */) {
			cause = cause.getCause();
		}
		if (cause != null /* && cause instanceof MarcacaoException */) {
			/* jsonData = MarcacaoException.class.cast(cause).getData(); */
		}

		if (cause != null) {
			/*
			 * JsonObject jsonErro = JsonUtil.create("erro", code, "clazz",
			 * cause .getClass().getName(), "msg", cause.getMessage());
			 */
			if (jsonData != null) {
				/* jsonErro.add("data", jsonData); */
			} else {
				/* LOG.error("Error", cause); */
			}

			/* ServletUtil.write(resp, jsonErro); */
		}
	}

	private void doTransaction(FilterChain chain, HttpServletRequest req,
			HttpServletResponse resp) {
		// TODO Auto-generated method stub
		/*
		 * TransactionFilter transactionFilter = new TransactionFilter(chain,
		 * req, resp); transactionFilter.execute();
		 */
	}

	@Override
	public void destroy() {
	}

}
