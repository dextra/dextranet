package br.com.dextra.dextranet.delorian;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OldDextranetDAO {

	/**
	 * Consulta todos os posts no banco da antiga Dextranet
	 * @return ArrayList contendo o conteudo que sera utilizado na migracao
	 */
    public ArrayList<PostOld> consultarPostsAntigos() throws SQLException {
        Connection conexao = ControladorDeConexoes.abreConexao();
        Statement stmt = conexao.createStatement();

        ResultSet rsPosts = stmt.executeQuery("SELECT users.name, node.title, nr1.body, node.created FROM users JOIN node ON users.uid = node.uid JOIN node_revisions nr1 ON node.nid = nr1.nid WHERE node.type = 'blog' and nr1.timestamp = (SELECT max(nr2.timestamp) FROM node_revisions nr2 WHERE nr2.nid = nr1.nid) ORDER BY node.created ASC");

        conexao.close();

        ArrayList<PostOld> postsOld = preenchePosts(rsPosts);

        return postsOld;
    }

    /**
     * Consulta todos os comentarios do banco da antiga Dextranet
     * @return ArrayList contendo o conteudo que sera utilizado na migracao
     */
    public ArrayList<Comentario> consultarComentariosAntigos() throws SQLException {
    	Connection conexao = ControladorDeConexoes.abreConexao();
        Statement stmt = conexao.createStatement();

        ResultSet rsComentarios = stmt.executeQuery("SELECT comments.name, comments.timestamp, comments.comment FROM comments JOIN node ON comments.nid = node.nid WHERE node.type = 'blog' ORDER BY node.nid, node.created ASC;");

        ArrayList<Comentario> comentarios = preencheComentarios(rsComentarios);

        return comentarios;
    }

    /**
     * Preenche um ArrayList com todos os posts
     */
	private ArrayList<PostOld> preenchePosts(ResultSet rsPosts) throws SQLException {
		ArrayList<PostOld> postsOld = new ArrayList<PostOld>();
		String usuario;
		String data;
		String titulo;
		String conteudo;

		while (rsPosts.next()) {
			usuario = rsPosts.getString("name");
			data = rsPosts.getString("created");
			titulo = rsPosts.getString("title");
			conteudo = rsPosts.getString("body");

			usuario = verificaUsuarioAnonimo(usuario);

			postsOld.add(new PostOld(usuario, data, titulo, conteudo));
        }
		return postsOld;
	}

	/**
	 * Preenche um ArrayList com todos os comentarios
	 */
	private ArrayList<Comentario> preencheComentarios(ResultSet rsComentarios) throws SQLException {
		ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
		String usuario;
		String data;
		String comentario;

		while (rsComentarios.next()) {
			usuario = rsComentarios.getString("name");
			data = rsComentarios.getString("timestamp");
			comentario = rsComentarios.getString("comment");

			usuario = verificaUsuarioAnonimo(usuario);

			comentarios.add(new Comentario(data, usuario, comentario));
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