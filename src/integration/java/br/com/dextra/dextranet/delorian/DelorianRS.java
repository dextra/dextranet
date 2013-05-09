package br.com.dextra.dextranet.delorian;

import java.io.InputStream;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;

public class DelorianRS {
	private final String PROXY_HOST = "10.16.129.1";
	private final int PROXY_PORT = 3128;
	private final String PROXY_USERNAME = "bruno.delgado";
	private final String PROXY_PASSWORD = "321mudar";

	public String enviarPost(OldPost oldPost) {
		String url = "http://www.google.com/";
		InputStream in = null;

		try {
			PostMethod method = new PostMethod(url);
			HttpClient client = new HttpClient();

			//Configura HttpClient para atuar com proxy da Dextra
			client.getHostConfiguration().setProxy(PROXY_HOST, PROXY_PORT);
			Credentials credentials = new UsernamePasswordCredentials(PROXY_USERNAME, PROXY_PASSWORD);
			AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
			client.getState().setProxyCredentials(authScope, credentials);

//			method.addParameter("data", oldPost.getData());
			method.addParameter("usuario", oldPost.getUsuario());
			method.addParameter("titulo", oldPost.getTitulo());
			method.addParameter("conteudo", oldPost.getConteudo());

			int statusCode = client.executeMethod(method);

			if (statusCode != -1) {
				in = method.getResponseBodyAsStream();
			}

			System.out.println(in);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return in.toString();
	}

	public void enviarComentario(Comentario comentario, String postId) {
		String url = "post/" + postId + "/comentario";
		InputStream in = null;

		try {
			PostMethod method = new PostMethod(url);
			HttpClient client = new HttpClient();

			//Configura HttpClient para atuar com proxy da Dextra
			client.getHostConfiguration().setProxy(PROXY_HOST, PROXY_PORT);
			Credentials credentials = new UsernamePasswordCredentials(PROXY_USERNAME, PROXY_PASSWORD);
			AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
			client.getState().setProxyCredentials(authScope, credentials);

//			method.addParameter("data", comentario.getData());
			method.addParameter("usuario", comentario.getUsuario());
			method.addParameter("conteudo", comentario.getComentario());

			int statusCode = client.executeMethod(method);

			if (statusCode != -1) {
				in = method.getResponseBodyAsStream();
			}

			System.out.println(in);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
