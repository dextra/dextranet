package br.com.dextra.dextranet.delorian;

import java.sql.SQLException;
import java.util.ArrayList;

public class MigracaoService {

	private OldDextranetDAO oldDextranetDao;

	public MigracaoService() {
		oldDextranetDao = new OldDextranetDAO();
	}

	/**
	 * Consulta os posts na antiga Dextranet e trata todos os que possuem anexo
	 */
	public ArrayList<OldPost> consultarPostsTratados() throws SQLException {
		ArrayList<OldPost> postsOld = oldDextranetDao.consultarPostsAntigos();
		ArrayList<OldAnexo> oldAnexos = oldDextranetDao.consultarPostsComAnexos();

		postsOld = tratarPostsComAnexos(postsOld, oldAnexos);

		return postsOld;
	}

	/**
	 * Consulta no DAO os comentarios de cada post
	 */
	public ArrayList<Comentario> consultarComentarios() throws SQLException {
		return oldDextranetDao.consultarComentariosAntigos();
	}

	/**
	 * Adiciona um texto alertando que o post possuia anexos
	 */
	private ArrayList<OldPost> tratarPostsComAnexos(ArrayList<OldPost> oldPosts, ArrayList<OldAnexo> oldAnexos) {
		for (int i = 0; i < oldPosts.size() ; i++) {
			for (OldAnexo oldAnexo : oldAnexos) {

				if (oldPosts.get(i).getTitulo().equals(oldAnexo.getTituloDoPost())) {
					String mensagem = "<br /><p>ATENÇÃO: Este post continha um anexo que foi desconsiderado no processo de migracao</p>";
					String conteudoPost = oldPosts.get(i).getConteudo();
					conteudoPost = conteudoPost.concat(mensagem);

					oldPosts.get(i).setConteudo(conteudoPost);

					System.out.println(conteudoPost);

					break;
				}

			}
		}
		return oldPosts;
	}

	public static void main(String[] args) throws SQLException {
		MigracaoService migracaoService = new MigracaoService();

		migracaoService.consultarPostsTratados();
	}

}
