package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

public class ReservaDAO {

	private Connection connection;
	
	public ReservaDAO() {
		this.connection = new ConnectionFactory().getConnection();
	}
	
	//cadastre as reservas da sala de squash
	public void adiciona(Socio socio, Salasquash salasquash, Calendar cal) {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"INSERT INTO reserva (NroIdSala, NroSocio, Hora, Data) "
					+ "VALUES (?, ?, ?, ?)");
			
			stmt.setLong(1, salasquash.getNroIdSala());
			stmt.setLong(2, socio.getNroSocio());
			//TODO na base de dados hora está como int, alterar e pegar a hora pelo calendar
			stmt.setInt(3, 1000);
			stmt.setDate(4, (Date) cal.getTime());
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	//Liste as reservas de uma sala por dia
	public List<Reserva> getReservaBySala(Sala sala) {
		
		try {
			List<Reserva> reservas = new ArrayList<Reserva>();
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("SELECT * FROM reserva WHERE NroIdSala = ?");
			
			stmt.setLong(1, sala.getNroId());
			ResultSet rs = stmt.executeQuery(); 
			
			while (rs.next()) {
				
				Reserva reserva = new Reserva();
				reserva.setNroIdSala(rs.getLong("NroIdSala"));
				reserva.setNroSocio(rs.getLong("NroSocio"));
				reserva.setHora(rs.getInt("Hora"));
				reserva.setData(rs.getDate("Data"));
				
				/*
				//Este é o exemplo do professor trabalhando com data
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("Data"));
				reserva.setData(data);
				 */
				
				reservas.add(reserva);
			}
			
			rs.close();
			stmt.close();
			return reservas;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	//Liste os usuários com o maior número de reservas por mês e por ano
	public Socio getReservaByMes(Calendar cal) {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"SELECT NroSocio, COUNT(NroSocio) as 'qtdReserva' "
					+ "FROM reserva WHERE MONTH(`Data`) = ? "
					+ "GROUP BY NroSocio "
					+ "ORDER BY `qtdReserva` DESC;");
			
			stmt.setInt(1, cal.get(Calendar.MONTH));
			ResultSet rs = stmt.executeQuery(); 
			
			rs.next();
				
			Reserva reserva = new Reserva();
			reserva.setNroIdSala(rs.getLong("NroIdSala"));
			reserva.setNroSocio(rs.getLong("NroSocio"));
			reserva.setHora(rs.getInt("Hora"));
			reserva.setData(rs.getDate("Data"));
			
			/*
			//Este é o exemplo do professor trabalhando com data
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("Data"));
			reserva.setData(data);
			 */
			
			SocioDAO socioDAO = new SocioDAO();
			
			rs.close();
			stmt.close();
			return socioDAO.getSocioById(reserva.getNroSocio());
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public Socio getReservaByAno(Calendar cal) {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"SELECT NroSocio, COUNT(NroSocio) as 'qtdReserva' "
					+ "FROM reserva WHERE YEAR(`Data`) = ? "
					+ "GROUP BY NroSocio "
					+ "ORDER BY `qtdReserva` DESC;");
			
			stmt.setInt(1, cal.get(Calendar.YEAR));
			ResultSet rs = stmt.executeQuery(); 
			
			rs.next();
				
			Reserva reserva = new Reserva();
			reserva.setNroIdSala(rs.getLong("NroIdSala"));
			reserva.setNroSocio(rs.getLong("NroSocio"));
			reserva.setHora(rs.getInt("Hora"));
			reserva.setData(rs.getDate("Data"));
			
			/*
			//Este é o exemplo do professor trabalhando com data
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("Data"));
			reserva.setData(data);
			 */
			
			SocioDAO socioDAO = new SocioDAO();
			
			rs.close();
			stmt.close();
			return socioDAO.getSocioById(reserva.getNroSocio());
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
}
