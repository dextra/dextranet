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
	}

	private void doTransaction(FilterChain chain, HttpServletRequest req,
			HttpServletResponse resp) {
	}

	@Override
	public void destroy() {
	}

}
