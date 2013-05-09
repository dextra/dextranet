package br.com.dextra.dextranet.delorian;

import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class Principal {
/*
	public static void main(String[] args) throws SQLException {
		MigracaoService migracaoService = new MigracaoService();
		DelorianRS delorianRS = new DelorianRS();
		Gson gson = new Gson();
		NewPost newPost;

		//Busca posts e comentarios
		ArrayList<OldPostWrapper> oldPostsWrapper = migracaoService.consultarPostsTratados();
		ArrayList<ComentarioWrapper> comentariosWrapper = migracaoService.consultarComentarios();

		//Envia um post de cada vez e em seguida todos os seus comentarios
		for (OldPostWrapper oldPostWrapper : oldPostsWrapper) {
			System.out.println("Migrando post: " + oldPostWrapper.getOldPost().getTitulo());
			System.out.println("Criado em: " + oldPostWrapper.getOldPost().getData());
			System.out.println("Autor: " + oldPostWrapper.getOldPost().getUsuario());
			System.out.println();

			String newPostJson = delorianRS.enviarPost(oldPostWrapper.getOldPost());
			newPost = gson.fromJson(newPostJson, NewPost.class);

			//Recupera todos os comentarios do post enviado
			ArrayList<Comentario> comentarios = migracaoService.recuperarComentariosDoPost(oldPostWrapper.getNid(), comentariosWrapper);

			//Envia todos os comentarios recuperados
			for (Comentario comentario : comentarios) {
				System.out.println("Migrando comentario criado em: " + comentario.getData());
				System.out.println("Autor: " + comentario.getUsuario());

				delorianRS.enviarComentario(comentario, newPost.getId());
			}
			System.out.println("------------");
		}
		System.out.println("Migracao realizada com sucesso.");
	}
*/
}
