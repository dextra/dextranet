package br.com.dextra.dextranet.delorian;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
	 * Retorna um array com os comentarios de um post especifico
	 */
	public ArrayList<Comentario> recuperarComentariosDoPost(Integer nid, ArrayList<ComentarioWrapper> comentariosWrapper) {
		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();

		for (ComentarioWrapper comentarioWrapper : comentariosWrapper) {
			if (comentarioWrapper.getNid().equals(nid)) {
				comentarios.add(comentarioWrapper.getComentario());
			}
		}

		return comentarios;
	}

	public static String formatarData(Date data) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data);
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
					break;
				}

			}
		}
		return oldPostsWrapper;
	}
}
