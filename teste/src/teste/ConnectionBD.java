package teste;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionBD {
	
	private static final String url = "jdbc:mysql://localhost:3306/sistemas_distribuidos";
	private static final String user = "root";
	private static final String password = "Senha123!";

	private static Connection conn;
	
	public static Connection connectBD() {
		
		try {
			if(conn ==null) {
				conn = DriverManager.getConnection(url, user, password);
				return conn;
			}else {
				return conn;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
