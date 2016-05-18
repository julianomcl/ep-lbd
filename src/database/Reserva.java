package database;

import java.sql.*;

public class Reserva {

	private Long nroIdSala;
	private Long nroSocio;
	//TODO na base de dados esta´ estruturado como integer, mas talvez ideal seja Date 
	private Integer hora;
	private Date data;
	
	public Long getNroIdSala() {
		return nroIdSala;
	}
	public void setNroIdSala(Long nroIdSala) {
		this.nroIdSala = nroIdSala;
	}
	public Long getNroSocio() {
		return nroSocio;
	}
	public void setNroSocio(Long nroSocio) {
		this.nroSocio = nroSocio;
	}
	public Integer getHora() {
		return hora;
	}
	public void setHora(Integer hora) {
		this.hora = hora;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
}
