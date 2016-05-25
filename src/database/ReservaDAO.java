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
			stmt.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public void UtilizaSala(Reserva reserva) {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"UPDATE Reserva SET Utilizada = ? WHERE NroSocio = ? AND NroIdSala = ? AND Hora = ? AND Data = ?");
			
			stmt.setInt(1, (reserva.isUtilizada() ? 1 : 0));
			stmt.setLong(3, reserva.getNroIdSala());
			stmt.setLong(2, reserva.getNroSocio());
			stmt.setInt(4, reserva.getHora());
			stmt.setDate(5, reserva.getData());
			
			stmt.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	//cadastre as reservas da sala de squash
	public void adiciona(Reserva reserva) {
		
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"INSERT INTO reserva (NroIdSala, NroSocio, Hora, Data) "
					+ "VALUES (?, ?, ?, ?)");
			
			stmt.setLong(1, reserva.getNroIdSala());
			stmt.setLong(2, reserva.getNroSocio());
			//TODO na base de dados hora está como int, alterar e pegar a hora pelo calendar
			stmt.setInt(3, reserva.getHora());
			stmt.setDate(4, reserva.getData());
			
			stmt.execute();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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
				reserva.setUtilizada(rs.getInt("Utilizada"));
				
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
	
	public ArrayList<Reserva> getReservaBySocio(Socio socio) {
		
		try {
			ArrayList<Reserva> reservas = new ArrayList<Reserva>();
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("SELECT * FROM reserva WHERE NroSocio = ?");
			
			stmt.setLong(1, socio.getNroSocio());
			ResultSet rs = stmt.executeQuery(); 
			
			while (rs.next()) {
				
				Reserva reserva = new Reserva();
				reserva.setNroIdSala(rs.getLong("NroIdSala"));
				reserva.setNroSocio(rs.getLong("NroSocio"));
				reserva.setHora(rs.getInt("Hora"));
				reserva.setData(rs.getDate("Data"));
				reserva.setUtilizada(rs.getInt("Utilizada"));
				
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
	
	public ArrayList<Integer> getHorasDisponiveisBySalaAndData(Sala sala, Date data) {
		
		try {
			ArrayList<Integer> horasLivres = new ArrayList<Integer>();
			for(int i = 0;i<24;i++){
				horasLivres.add(i);
			}
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("SELECT Distinct(Hora) FROM reserva WHERE NroIdSala = ? AND Data = ?");
			
			stmt.setLong(1, sala.getNroId());
			stmt.setDate(2, data);
			ResultSet rs = stmt.executeQuery(); 
			
			while (rs.next()) {
				
				Integer horaReservada = rs.getInt("Hora");
				
				/*
				//Este é o exemplo do professor trabalhando com data
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("Data"));
				reserva.setData(data);
				 */
				
				horasLivres.remove(horaReservada);
			}
			
			rs.close();
			stmt.close();
			
			return horasLivres;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	//Liste os usuários com o maior número de reservas por mês e por ano
	public String getReservaByMes(Calendar cal) {
		
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
			reserva.setNroSocio(rs.getLong("NroSocio"));
			int reservas = rs.getInt("qtdReserva");
			
			/*
			//Este é o exemplo do professor trabalhando com data
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("Data"));
			reserva.setData(data);
			 */
			
			SocioDAO socioDAO = new SocioDAO();
			
			rs.close();
			stmt.close();
			return socioDAO.getSocioById(reserva.getNroSocio()).getNome() + " - " + reservas;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}
	
	public String getReservaByAno(Calendar cal) {
		
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
			reserva.setNroSocio(rs.getLong("NroSocio"));
			int reservas = rs.getInt("qtdReserva");
			
			/*
			//Este é o exemplo do professor trabalhando com data
			Calendar data = Calendar.getInstance();
			data.setTime(rs.getDate("Data"));
			reserva.setData(data);
			 */
			
			SocioDAO socioDAO = new SocioDAO();
			
			rs.close();
			stmt.close();
			return socioDAO.getSocioById(reserva.getNroSocio()).getNome() + " - " + reservas;
			
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	public ArrayList<Object> getReservasNaoUtilizadas() {
		try {
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement(
					"SELECT NroSocio, COUNT(NroSocio) as 'qtdReserva' "
					+ "FROM reserva WHERE Utilizada = 'false' "
					+ "GROUP BY NroSocio "
					+ "ORDER BY `qtdReserva` DESC;");
			
			ResultSet rs = stmt.executeQuery(); 
			
			ArrayList<Object> resultado = new ArrayList<Object>();
			
			while(rs.next()){
				
				Reserva reserva = new Reserva();
				reserva.setNroSocio(rs.getLong("NroSocio"));
				int reservas = rs.getInt("qtdReserva");
				
				/*
				//Este é o exemplo do professor trabalhando com data
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("Data"));
				reserva.setData(data);
				 */
				
				SocioDAO socioDAO = new SocioDAO();
				
				resultado.add(socioDAO.getSocioById(reserva.getNroSocio()).getNome() + " - " + reservas);
			
			}
			rs.close();
			stmt.close();
			return resultado;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException();
		}
	}

	public ArrayList<Object> getReservasSalaPorDia(Sala sala) {
		try {
			ArrayList<Object> reservas = new ArrayList<Object>();
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("SELECT * FROM reserva WHERE NroIdSala = ? ORDER BY Data");
			
			stmt.setLong(1, sala.getNroId());
			ResultSet rs = stmt.executeQuery(); 
			
			while (rs.next()) {
				
				Reserva reserva = new Reserva();
				reserva.setNroIdSala(rs.getLong("NroIdSala"));
				reserva.setNroSocio(rs.getLong("NroSocio"));
				reserva.setHora(rs.getInt("Hora"));
				reserva.setData(rs.getDate("Data"));
				reserva.setUtilizada(rs.getInt("Utilizada"));
				
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
	
}
