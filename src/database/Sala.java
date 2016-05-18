package database;

public class Sala {

	private Long nroId;
	private Integer area;
	private String localizacao;
	private Integer tipoSala;
	
	public Long getNroId() {
		return nroId;
	}
	public void setNroId(Long nroId) {
		this.nroId = nroId;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public Integer getTipoSala() {
		return tipoSala;
	}
	public void setTipoSala(Integer tipoSala) {
		this.tipoSala = tipoSala;
	}
	
}
