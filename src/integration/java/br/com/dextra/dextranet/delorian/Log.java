package br.com.dextra.dextranet.delorian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class Log {
	private final String LOG_POST = "log-post.txt";
	private final String LOG_COMENTARIO = "log-comentario.txt";
	private int indicePost;
	private String postId;
	private int indiceComentario;

	public int getIndicePost() {
		return indicePost;
	}

	public void setIndicePost(int indicePost) {
		this.indicePost = indicePost;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public int getIndiceComentario() {
		return indiceComentario;
	}

	public void setIndiceComentario(int indiceComentario) {
		this.indiceComentario = indiceComentario;
	}

	/**
	 * Inicializa os LOGs
	 */
	public Log() throws IOException {
		String[] dados;

		while (!new File(LOG_POST).exists()) {
			System.out.println("Criando log de post.");
			criarLog(LOG_POST);
			System.out.println("Log de post criado com sucesso.");
		}
		while (!new File(LOG_COMENTARIO).exists()) {
			System.out.println("Criando log de comentario.");
			criarLog(LOG_COMENTARIO);
			System.out.println("Log de comentario criado com sucesso.");
		}

		dados = lerLog(LOG_POST).split(" ");
		this.indicePost = Integer.parseInt(dados[0]);
		this.postId = dados[1];

		dados = lerLog(LOG_COMENTARIO).split(" ");
		this.indiceComentario = Integer.parseInt(dados[0]);
	}

	public void salvarIndicePost(int indicePost) {
		try {
			FileUtils.writeStringToFile(new File(LOG_POST), indicePost + " 0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvarPostId(int indicePost, String postId) {
		try {
			FileUtils.writeStringToFile(new File(LOG_POST), indicePost + " " + postId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvarIndiceComentario(int indiceLog) {
		try {
			FileUtils.writeStringToFile(new File(LOG_COMENTARIO), indiceLog + " 0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void zerarComentario() {
		try {
			FileUtils.writeStringToFile(new File(LOG_COMENTARIO), "0 0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void criarLog(String arquivo) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
			writer.write("0 0");
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String lerLog(String arquivo) throws FileNotFoundException, IOException {
		FileInputStream inputStream = new FileInputStream(arquivo);
		String dados = IOUtils.toString(inputStream);
		inputStream.close();
		return dados;
	}
}
