package database;

import java.sql.*;

public class SalasquashDAO {
	
	private Connection connection;
	
	public SalasquashDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	
}
