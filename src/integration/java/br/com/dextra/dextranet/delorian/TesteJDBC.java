package br.com.dextra.dextranet.delorian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteJDBC {

    public static void main(String[] args) throws SQLException {
        Connection conexao = DriverManager.getConnection("jdbc:postgresql://dbserver:5432/bruno_old_dextranet","postgres", "postgres123");

        Statement stmt = conexao.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT users.name, node.title, nr1.body, node.created FROM users JOIN node ON users.uid = node.uid JOIN node_revisions nr1 ON node.nid = nr1.nid WHERE node.type = 'blog' and nr1.timestamp = (SELECT max(nr2.timestamp) FROM node_revisions nr2 WHERE nr2.nid = nr1.nid LIMIT 25)");

        while (rs.next()) {
            System.out.println("Nome: " + rs.getString("name") + "\nData: " + rs.getString("created") + "\nTitulo: " + rs.getString("title") + "\nMensagem: " + rs.getString("body") + "\n");
        }

        conexao.close();
    }
}