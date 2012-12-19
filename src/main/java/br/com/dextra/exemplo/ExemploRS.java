package br.com.dextra.exemplo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;

@Path("/teste")
public class ExemploRS {

	@Path("/retorno")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String retorna() {
		StringBuilder json = new StringBuilder();
		json.append("{'retorno':'Hello World'}");
		return json.toString();

	}

	@Path("/retorno/{id}")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String retorna(@PathParam(value = "id") String valor) {

		return "Hello World " + valor;

	}

	@Path("/retorno/param-na-url")
	@GET
	@Produces("application/json;charset=UTF-8")
	public String retorna(@QueryParam(value = "x") String valorX,
			@QueryParam(value = "y") String valorY) {

		String retorno = "Hello World ";
		if (StringUtils.isNotEmpty(valorX)) {
			retorno += " x = " + valorX;
		}
		if (StringUtils.isNotEmpty(valorY)) {
			retorno += " y = " + valorY;
		}

		return retorno;
	}

}
