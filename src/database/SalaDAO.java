package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class SalaDAO {

	private Connection connection;
	
	public SalaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public ArrayList<Sala> getAllSalasSquash() {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"SELECT * FROM sala WHERE TipoSala = 1;");

			ResultSet rs = stmt.executeQuery(); 
			ArrayList<Sala> salas = new ArrayList<Sala>();
			while(rs.next()){
				
				Sala sala = new Sala();
				sala.setNroId(rs.getLong("NroId"));
				sala.setArea(rs.getInt("Area"));
				sala.setLocalizacao(rs.getString("Localizacao"));
				sala.setTipoSala(rs.getInt("TipoSala"));
				
				salas.add(sala);
			
			}
			rs.close();
			stmt.close();
			return salas;
			
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
}
