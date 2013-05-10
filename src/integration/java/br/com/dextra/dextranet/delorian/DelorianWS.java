package br.com.dextra.dextranet.delorian;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;

public class DelorianWS {
	private final String PROXY_HOST = "10.16.129.1";
	private final int PROXY_PORT = 3128;
	private final String PROXY_USERNAME = "bruno.delgado";
	private final String PROXY_PASSWORD = "321mudar";

	public String enviarPost(OldPost oldPost, int i, Log log) {
//		String url = "http://dev.dextra-dextranet.appspot.com/s/migracao/post";
		String url = "http://dev.brunodextranet.appspot.com/s/migracao/post";

		try {
			PostMethod method = new PostMethod(url);
			HttpClient client = new HttpClient();
			method.setRequestHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");

			//Configura HttpClient para atuar com proxy da Dextra
			client.getHostConfiguration().setProxy(PROXY_HOST, PROXY_PORT);
			Credentials credentials = new UsernamePasswordCredentials(PROXY_USERNAME, PROXY_PASSWORD);
			AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
			client.getState().setProxyCredentials(authScope, credentials);

			method.addParameter("data", DelorianService.formatarData(oldPost.getData()));
			method.addParameter("usuario", oldPost.getUsuario());
			method.addParameter("titulo", oldPost.getTitulo());
			method.addParameter("conteudo", oldPost.getConteudo());

			client.executeMethod(method);
			byte[] responseBody = method.getResponseBody();

			if (method.getStatusCode() == 200) {
				System.out.println("Salvando log...");
				log.salvarIndicePost(i);
				System.out.println("Log salvo com sucesso.");
			}

			method.releaseConnection();

			return new String(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void enviarComentario(Comentario comentario, String postId, int i, Log log) {
//		String url = "http://dev.dextra-dextranet.appspot.com/s/migracao/post/"+postId+"/comentario";
		String url = "http://dev.brunodextranet.appspot.com/s/migracao/post/"+postId+"/comentario";

		try {
			PostMethod method = new PostMethod(url);
			HttpClient client = new HttpClient();
			method.setRequestHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");

			//Configura HttpClient para atuar com proxy da Dextra
			client.getHostConfiguration().setProxy(PROXY_HOST, PROXY_PORT);
			Credentials credentials = new UsernamePasswordCredentials(PROXY_USERNAME, PROXY_PASSWORD);
			AuthScope authScope = new AuthScope(PROXY_HOST, PROXY_PORT);
			client.getState().setProxyCredentials(authScope, credentials);

			method.addParameter("data", DelorianService.formatarData(comentario.getData()));
			method.addParameter("usuario", comentario.getUsuario());
			method.addParameter("conteudo", comentario.getComentario());

			client.executeMethod(method);

			if (method.getStatusCode() == 200) {
				log.salvarIndiceComentario(i);
			}

			method.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
