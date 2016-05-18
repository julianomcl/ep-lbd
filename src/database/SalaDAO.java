package database;

import java.sql.Connection;

public class SalaDAO {

	private Connection connection;
	
	public SalaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
}
