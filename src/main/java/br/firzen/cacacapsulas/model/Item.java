package br.firzen.cacacapsulas.model;

public class Item {
	
	private Long id;
	
	private Integer idApi;
	
	private String nome;
	
	private String tipo; //CAIXA ou CAPSULA
	
	private Integer qtd;
	
	public Item(Integer idApi, String nome, String tipo, Integer qtd) {
		new Item(idApi, nome, tipo);
		this.qtd = qtd;
	}
	
	public Item(Integer idApi, String nome, String tipo) {
		this.idApi = idApi;
		this.nome = nome;
		this.tipo = tipo;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getQtd() {
		return qtd;
	}

	public void setQtd(Integer qtd) {
		this.qtd = qtd;
	}

	public Integer getIdApi() {
		return idApi;
	}

	public void setIdApi(Integer idApi) {
		this.idApi = idApi;
	}
	

}
