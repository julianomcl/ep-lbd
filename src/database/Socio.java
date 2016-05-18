package database;

public class Socio {

	private Long nroSocio;
	private String nome;
	private String endereco;
	private String telefone;
	private String profissao;
	private String dadosBancarios;
	
	public Long getNroSocio() {
		return nroSocio;
	}
	public void setNroSocio(Long nroSocio) {
		this.nroSocio = nroSocio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getProfissao() {
		return profissao;
	}
	public void setProfissao(String profissao) {
		this.profissao = profissao;
	}
	public String getDadosBancarios() {
		return dadosBancarios;
	}
	public void setDadosBancarios(String dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}
	
}
