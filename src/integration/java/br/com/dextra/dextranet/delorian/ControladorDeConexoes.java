package br.com.dextra.dextranet.delorian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControladorDeConexoes {

	/*private static final String URL = "jdbc:postgresql://dbserver:5432/bruno_old_dextranet";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres123";*/

	private static final String URL = "jdbc:postgresql://dbserver:5432/bruno_old_dextranet";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres123";

    public static Connection abreConexao() {
    	try {
			return DriverManager.getConnection(URL,USUARIO,SENHA);
		} catch (SQLException e) {
			System.out.println("Nao foi possivel estabelecer uma conexao com o banco.");
		}
		return null;
    }
}
