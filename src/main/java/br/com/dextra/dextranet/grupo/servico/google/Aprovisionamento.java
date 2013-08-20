package br.com.dextra.dextranet.grupo.servico.google;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import br.com.dextra.dextranet.seguranca.AutenticacaoService;

public class Aprovisionamento {

		private GoogleKeyRepository repo = new GoogleKeyRepository();

		protected String obtemUsuarioLogado() {
			return AutenticacaoService.identificacaoDoUsuarioLogado();
		}

		List<GoogleKey> googleKey = repo.lista();
		String key = googleKey.get(0).getKey().toString();

		public void doPost(String acao, String nomeEmail, String grupoEmail, List<String> emails ) throws IOException{

			URL url = new URL("https://script.google.com/macros/s/AKfycbxG9oiWD1a4TvGuJg0QxTCs8FrazbUx8jga1gBZJUnsjSEU6wA/exec?");

			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		    urlConn.setDoInput (true);
		    urlConn.setDoOutput (true);
		    urlConn.setUseCaches (false);
		    urlConn.setRequestMethod("POST");
		    urlConn.setReadTimeout(0);
		    urlConn.connect();

		    DataOutputStream output = new DataOutputStream(urlConn.getOutputStream());

			String listString = "";
			if(emails != null){
				for (String s : emails)
				{
				    listString += s + ",";
				}
				listString = listString.substring(0, listString.length()-1);
			}

			System.out.println(listString);

		    String content =
		      "key=" + URLEncoder.encode(key, "UTF-8") +
		      "&funcao=" + URLEncoder.encode(acao, "UTF-8") +
		      "&emailgrupo=" + URLEncoder.encode(grupoEmail, "UTF-8") +
		      "&nomegrupo=" + URLEncoder.encode(nomeEmail, "UTF-8") +
		      "&emailmembro=" + URLEncoder.encode(listString, "UTF-8") +
		      "&autorRequisicao=" + URLEncoder.encode(obtemUsuarioLogado(), "UTF-8");


	        output.writeBytes(content);
	        output.flush();
	        output.close();
	        System.out.println(content);

		    DataInputStream input = new DataInputStream (urlConn.getInputStream());
//		    while (null != ((str = input.readLine()))) {
//		        //System.out.println(str);
//		    }
		    input.close ();
		}

		  public String doGet(String acao, String email) throws IOException {
		      HttpURLConnection connection = null;
		      BufferedReader rd  = null;
		      StringBuilder sb = null;
		      String line = null;

		      URL serverAddress = null;

		          serverAddress = new URL("https://script.google.com/macros/s/AKfycbxG9oiWD1a4TvGuJg0QxTCs8FrazbUx8jga1gBZJUnsjSEU6wA/exec?key=" + key +"&funcao=" + acao + "&emailgrupo=" + email + "&autorRequisicao=" + obtemUsuarioLogado());
		          System.out.println(serverAddress);
		          connection = null;
		          connection = (HttpURLConnection)serverAddress.openConnection();
		          connection.setRequestMethod("GET");
		          connection.setDoOutput(true);
		          connection.setReadTimeout(0);

		          connection.connect();

		          rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		          sb = new StringBuilder();

		          while ((line = rd.readLine()) != null)
		          {
		              sb.append(line + '\n');
		          }

		          String result = sb.toString();

		          connection.disconnect();
		          rd = null;
		          sb = null;
		          connection = null;
			return result;
		  }
}