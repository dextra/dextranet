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

	public ArrayList<Comentario> recuperarComentariosDoPost(Integer nid, ArrayList<ComentarioWrapper> comentariosWrapper) {
		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();

		for (ComentarioWrapper comentarioWrapper : comentariosWrapper) {
			if (comentarioWrapper.getNid().equals(nid)) {
				comentarios.add(comentarioWrapper.getComentario());
			}
		}

		return comentarios;
	}
/*
	public static void main(String[] args) throws SQLException {
		MigracaoService migracaoService = new MigracaoService();

		ArrayList<OldPostWrapper> oldPostsWrapper = migracaoService.consultarPostsTratados();
		ArrayList<ComentarioWrapper> comentariosWrapper = migracaoService.consultarComentarios();

		//envia um post
		for (OldPostWrapper oldPostWrapper : oldPostsWrapper) {
			//envia o post com json
			//recebe o post novamente
			//tratar json que veio de resposta

			ArrayList<Comentario> comentarios = migracaoService.recuperarComentariosDoPost(oldPostWrapper.getNid(), comentariosWrapper);

			for (Comentario comentario : comentarios) {
				//inserir o postId que veio do servidor no comentario
				//pra cada comentario, enviar uma requisicao para o servidor
			}
		}
	}
*/
}
