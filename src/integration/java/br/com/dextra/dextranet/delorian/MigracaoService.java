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
	public ArrayList<OldPostWrapper> consultarPostsTratados() throws SQLException {
		ArrayList<OldPostWrapper> postsOld = oldDextranetDao.consultarPostsAntigos();
		ArrayList<OldAnexo> oldAnexos = oldDextranetDao.consultarPostsComAnexos();

		postsOld = tratarPostsComAnexos(postsOld, oldAnexos);

		return postsOld;
	}

	/**
	 * Consulta no DAO os comentarios de cada post
	 */
	public ArrayList<ComentarioWrapper> consultarComentarios() throws SQLException {
		return oldDextranetDao.consultarComentariosAntigos();
	}

	/**
	 * Adiciona um texto alertando que o post possuia anexos
	 */
	private ArrayList<OldPostWrapper> tratarPostsComAnexos(ArrayList<OldPostWrapper> oldPostsWrapper, ArrayList<OldAnexo> oldAnexos) {
		for (int i = 0; i < oldPostsWrapper.size() ; i++) {
			for (OldAnexo oldAnexo : oldAnexos) {

				if (oldPostsWrapper.get(i).getNid().equals(oldAnexo.getPostId())) {
					String mensagem = "<br /><p>ATENÇÃO: Este post continha um anexo que foi desconsiderado no processo de migracao</p>";
					String conteudoPost = oldPostsWrapper.get(i).getOldPost().getConteudo();
					conteudoPost = conteudoPost.concat(mensagem);

					oldPostsWrapper.get(i).getOldPost().setConteudo(conteudoPost);

					System.out.println("Entrou");

					break;
				}

			}
		}
		return oldPostsWrapper;
	}
/*
	public static void main(String[] args) throws SQLException {
		MigracaoService migracaoService = new MigracaoService();

		ArrayList<OldPostWrapper> oldPosts = migracaoService.consultarPostsTratados();
		ArrayList<ComentarioWrapper> comentarios = migracaoService.consultarComentarios();

		//envia um post
		for (OldPostWrapper oldPostWrapper : oldPosts) {
			//envia o post
			//recebe o post novamente


		}
		//recebe o retorno do post


	}
*/
}
