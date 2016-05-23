package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class SocioDAO {

	private Connection connection;
	
	public SocioDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public Socio getSocioById(Long socioId) {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"SELECT * FROM socio WHERE NroSocio = ?;");
			
			stmt.setLong(1, socioId);
			ResultSet rs = stmt.executeQuery(); 
			
			rs.next();
				
			Socio socio = new Socio();
			socio.setNroSocio(rs.getLong("NroSocio"));
			socio.setNome(rs.getString("Nome"));
			socio.setEndereco(rs.getString("Endereco"));
			socio.setTelefone(rs.getString("Telefone"));
			socio.setProfissao(rs.getString("Profissao"));
			socio.setDadosBancarios(rs.getString("DadosBancarios"));
			
			rs.close();
			stmt.close();
			return socio;
			
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
	public ArrayList<Socio> getAllSocios() {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"SELECT * FROM socio;");

			ResultSet rs = stmt.executeQuery(); 
			ArrayList<Socio> socios = new ArrayList<Socio>();
			while(rs.next()){
				
				Socio socio = new Socio();
				socio.setNroSocio(rs.getLong("NroSocio"));
				socio.setNome(rs.getString("Nome"));
				socio.setEndereco(rs.getString("Endereco"));
				socio.setTelefone(rs.getString("Telefone"));
				socio.setProfissao(rs.getString("Profissao"));
				socio.setDadosBancarios(rs.getString("DadosBancarios"));
				
				socios.add(socio);
			
			}
			rs.close();
			stmt.close();
			return socios;
			
		} catch(SQLException e) {
			throw new RuntimeException();
		}
		
	}
	
}
