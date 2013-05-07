package br.com.dextra.dextranet.delorian;

import java.sql.SQLException;
import java.util.ArrayList;

public class MigracaoService {

	/**
	 * Consulta os posts na antiga Dextranet e trata todos os que possuem anexo
	 */
	public ArrayList<OldPost> consultarPostsTratados() throws SQLException {
		OldDextranetDAO oldDextranetDAO = new OldDextranetDAO();
		ArrayList<OldPost> postsOld = oldDextranetDAO.consultarPostsAntigos();
		ArrayList<OldAnexo> oldAnexos = oldDextranetDAO.consultarPostsComAnexos();

		postsOld = tratarPostsComAnexos(postsOld, oldAnexos);

		return postsOld;
	}

	/**
	 * Adiciona um texto alertando que o post possuia anexos
	 */
	private ArrayList<OldPost> tratarPostsComAnexos(ArrayList<OldPost> oldPosts, ArrayList<OldAnexo> oldAnexos) {
		for (OldPost oldPost : oldPosts) {

			for (OldAnexo oldAnexo : oldAnexos) {
				if (oldPost.getTitulo().equals(oldAnexo.getTituloDoPost())) {
					System.out.println("NID: " + oldAnexo.getPostId());
				}
			}

		}
		return null;
	}

//	public static void main(String[] args) throws SQLException {
//		MigracaoService migracaoService = new MigracaoService();
//
//		migracaoService.consultarPostsTratados();
//	}

}
