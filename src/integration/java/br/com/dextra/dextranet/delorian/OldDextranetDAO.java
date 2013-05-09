package br.com.dextra.dextranet.delorian;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class OldDextranetDAO {

	/**
	 * Consulta todos os posts no banco da antiga Dextranet
	 * @return ArrayList contendo o conteudo que sera utilizado na migracao
	 */
    public ArrayList<OldPostWrapper> consultarPostsAntigos() throws SQLException {
        Connection conexao = ControladorDeConexoes.abreConexao();
        Statement stmt = conexao.createStatement();

        ResultSet rsPosts = stmt.executeQuery("SELECT users.name, node.title, nr1.body, node.created, node.nid FROM users JOIN node ON users.uid = node.uid JOIN node_revisions nr1 ON node.nid = nr1.nid WHERE node.type = 'blog' and nr1.timestamp = (SELECT max(nr2.timestamp) FROM node_revisions nr2 WHERE nr2.nid = nr1.nid) ORDER BY node.created ASC");

        conexao.close();

        ArrayList<OldPostWrapper> oldPosts = preenchePosts(rsPosts);

        return oldPosts;
    }

    /**
     * Consulta todos os comentarios do banco da antiga Dextranet
     * @return ArrayList contendo o conteudo que sera utilizado na migracao
     */
    public ArrayList<ComentarioWrapper> consultarComentariosAntigos() throws SQLException {
    	Connection conexao = ControladorDeConexoes.abreConexao();
        Statement stmt = conexao.createStatement();

        ResultSet rsComentarios = stmt.executeQuery("SELECT comments.name, comments.timestamp, comments.comment, node.nid FROM comments JOIN node ON comments.nid = node.nid WHERE node.type = 'blog' ORDER BY node.nid, node.created ASC;");

        conexao.close();

        ArrayList<ComentarioWrapper> comentarios = preencheComentarios(rsComentarios);

        return comentarios;
    }

    /**
     * Consulta o id e os arquivos de todos os posts que possuem algum tipo de anexo
     * @return ArrayList de Integer com todos os ids e uma descricao do anexo
     */
    public ArrayList<OldAnexo> consultarPostsComAnexos() throws SQLException {
    	Connection conexao = ControladorDeConexoes.abreConexao();
        Statement stmt = conexao.createStatement();

        ResultSet rsPostsAnexos = stmt.executeQuery("SELECT upload.nid, upload.description, node.title FROM upload JOIN node ON upload.nid = node.nid WHERE node.type = 'blog' ORDER BY upload.nid ASC;");

        conexao.close();

        ArrayList<OldAnexo> idsPostsComComentarios = preencheValorPostsComAnexos(rsPostsAnexos);

    	return idsPostsComComentarios;
    }

    /**
     * Insere os ids dos Posts que possuem anexo em um ArrayList
     */
    private ArrayList<OldAnexo> preencheValorPostsComAnexos(ResultSet rsPostsAnexos) throws SQLException {
    	ArrayList<OldAnexo> idsEAnexosPosts = new ArrayList<OldAnexo>();
    	String idPostComAnexo;
    	String anexo;
    	String tituloDoPost;

    	while (rsPostsAnexos.next()) {
    		idPostComAnexo = rsPostsAnexos.getString("nid");
    		anexo = rsPostsAnexos.getString("description");
    		tituloDoPost = rsPostsAnexos.getString("title");
    		System.out.println(idPostComAnexo);
			idsEAnexosPosts.add(new OldAnexo(Integer.parseInt(idPostComAnexo), anexo, tituloDoPost));
        }

    	return idsEAnexosPosts;
	}

	/**
     * Preenche um ArrayList com todos os posts
     */
	private ArrayList<OldPostWrapper> preenchePosts(ResultSet rsPosts) throws SQLException {

		ArrayList<OldPostWrapper> postsOld = new ArrayList<OldPostWrapper>();
		String nid;
		String usuario;
		String data;
		String titulo;
		String conteudo;
		Date dataTratada;
		OldPost oldPostTemp;

		while (rsPosts.next()) {
			nid = rsPosts.getString("nid");
			usuario = rsPosts.getString("name");
			data = rsPosts.getString("created");
			titulo = rsPosts.getString("title");
			conteudo = rsPosts.getString("body");

			dataTratada = tratarTimestamp(data);
			usuario = verificaUsuarioAnonimo(usuario);

			oldPostTemp = new OldPost(usuario, dataTratada, titulo, conteudo);

			postsOld.add(new OldPostWrapper(Integer.parseInt(nid), oldPostTemp));
        }
		return postsOld;
	}

	private Date tratarTimestamp(String dataString) {
		return new Date(Long.parseLong(dataString) * 1000);
	}

	/**
	 * Preenche um ArrayList com todos os comentarios
	 */
	private ArrayList<ComentarioWrapper> preencheComentarios(ResultSet rsComentarios) throws SQLException {
		ArrayList<ComentarioWrapper> comentarios = new ArrayList<ComentarioWrapper>();
		String usuario;
		String data;
		String comentario;
		String nid;
		Date dataTratada;

		while (rsComentarios.next()) {
			nid = rsComentarios.getString("nid");
			usuario = rsComentarios.getString("name");
			data = rsComentarios.getString("timestamp");
			comentario = rsComentarios.getString("comment");

			dataTratada = tratarTimestamp(data);
			usuario = verificaUsuarioAnonimo(usuario);

			Comentario comentarioTemp = new Comentario(dataTratada, usuario, comentario);

			comentarios.add(new ComentarioWrapper(Integer.parseInt(nid), comentarioTemp));
        }
		return comentarios;
	}

	/**
	 * Verifica se o nome do usuario eh vazio. Se for, eh pq o usuario nao existe mais.
	 * Nestes casos, substitui o usuario pelo nome anonimo.
	 */
	private String verificaUsuarioAnonimo(String usuario) {
		if (usuario.length() == 0) {
			usuario = "anonimo";
		}
		return usuario;
	}
}