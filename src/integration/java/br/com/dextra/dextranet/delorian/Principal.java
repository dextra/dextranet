package br.com.dextra.dextranet.delorian;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Principal {
/*
	public static void main(String[] args) throws SQLException, IOException {

		Log log = new Log();
		DelorianService migracaoService = new DelorianService();
		DelorianWS delorianWS = new DelorianWS();
		Gson gson = new Gson();
		NewPost newPost;

		//Busca posts e comentarios
		ArrayList<OldPost> oldPosts = migracaoService.consultarPostsTratados();
		ArrayList<Comentario> comentarios = migracaoService.consultarComentarios();

		//Se verdadeiro, quer dizer que a migracao foi interrompida enquanto salvava comentarios
		migrarComentariosInterrompidos(log, migracaoService, delorianWS, oldPosts, comentarios);

		int indicePost = log.getIndicePost() == 0 ? 0 : log.getIndicePost() + 1; //Se verdadeiro, migracao nunca foi feita. Se falso, migracao foi iniciada e interrompida.
		//Envia um post de cada vez e em seguida todos os seus comentarios
		for (int i = indicePost; i < oldPosts.size() ; i++) {
			System.out.println("Migrando post: " + oldPosts.get(i).getTitulo());
			System.out.println("Criado em: " + oldPosts.get(i).getData());
			System.out.println("Autor: " + oldPosts.get(i).getUsuario());
			System.out.println();

			String newPostJson = delorianWS.enviarPost(oldPosts.get(i), i, log);
			newPost = gson.fromJson(newPostJson, NewPost.class);
			log.salvarPostId(i, newPost.getId());

			//Recupera todos os comentarios do post enviado
			ArrayList<Comentario> comentariosDoPost = migracaoService.selecionarComentariosDoPost(oldPosts.get(i).getNid(), comentarios);
			System.out.println("Id do post no GAE: " + newPost.getId());

			//Envia todos os comentarios recuperados
			migrarComentarios(delorianWS, newPost.getId(), comentariosDoPost, 0, log);

			System.out.println("------------");
		}
		System.out.println("Migracao realizada com sucesso.");
	}
*/
	private static void migrarComentariosInterrompidos(Log log, DelorianService migracaoService, DelorianWS delorianRS, ArrayList<OldPost> oldPosts, ArrayList<Comentario> comentarios) {
		if (log.getIndiceComentario() != 0) {
			int indicePost = log.getIndicePost();
			int indiceComentario = log.getIndiceComentario();
			String postId = log.getPostId();

			System.out.println("A migracao de comentarios havia sido interrompida. Iniciando de onde parou.");

			ArrayList<Comentario> comentariosDoPostMigrado = migracaoService.selecionarComentariosDoPost(oldPosts.get(indicePost).getNid(), comentarios);

			if (indiceComentario + 1 <= comentariosDoPostMigrado.size()) {
				migrarComentarios(delorianRS, postId, comentariosDoPostMigrado, indiceComentario + 1, log);
			}
		}
	}

	private static void migrarComentarios(DelorianWS delorianRS, String postId, ArrayList<Comentario> comentariosDoPost, int indice, Log log) {
		for (int j = indice; j < comentariosDoPost.size(); j++) {
			System.out.println("Migrando comentario criado em: " + comentariosDoPost.get(j).getData());
			System.out.println("Autor: " + comentariosDoPost.get(j).getUsuario());

			delorianRS.enviarComentario(comentariosDoPost.get(j), postId, j, log);
		}
		log.zerarComentario();
	}

}
