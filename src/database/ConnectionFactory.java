package database;

import java.sql.*;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost/lbd", "root", "admin");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
