package br.com.dextra.dextranet.delorian;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DelorianService {

	private OldDextranetDAO oldDextranetDao;

	public DelorianService() {
		oldDextranetDao = new OldDextranetDAO();
	}

	/**
	 * Consulta os posts na antiga Dextranet e trata todos os que possuem anexo
	 */
	public ArrayList<OldPost> consultarPostsTratados() throws SQLException {
		ArrayList<OldPost> oldPosts = oldDextranetDao.consultarPostsAntigos();
		ArrayList<OldAnexo> oldAnexos = oldDextranetDao.consultarPostsComAnexos();

		oldPosts = tratarPostsComAnexos(oldPosts, oldAnexos);

		return oldPosts;
	}

	/**
	 * Consulta no DAO os comentarios de cada post
	 */
	public ArrayList<Comentario> consultarComentarios() throws SQLException {
		return oldDextranetDao.consultarComentariosAntigos();
	}

	/**
	 * Retorna um array com os comentarios de um post especifico
	 */
	public ArrayList<Comentario> selecionarComentariosDoPost(Integer nid, ArrayList<Comentario> comentarios) {
		ArrayList<Comentario> comentariosTemp = new ArrayList<Comentario>();

		for (Comentario comentario : comentarios) {
			if (comentario.getNid().equals(nid)) {
				comentariosTemp.add(comentario);
			}
		}

		return comentariosTemp;
	}

	public static String formatarData(Date data) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
	}

	/**
	 * Adiciona um texto alertando que o post possuia anexos
	 */
	private ArrayList<OldPost> tratarPostsComAnexos(ArrayList<OldPost> oldPosts, ArrayList<OldAnexo> oldAnexos) {
		for (int i = 0; i < oldPosts.size() ; i++) {
			for (int j = 0; i < oldAnexos.size() ; i++) {

				if (oldPosts.get(i).getNid().equals(oldAnexos.get(j).getPostId())) {
					String mensagem = "<br /><br /><p style='font-weight: bold;'>ATENÇÃO: Este post continha um anexo que foi desconsiderado no processo de migracao</p>";
					String conteudoPost = oldPosts.get(i).getConteudo();
					conteudoPost = conteudoPost.concat(mensagem);

					oldPosts.get(i).setConteudo(conteudoPost);
					break;
				}

			}
		}
		return oldPosts;
	}
}
