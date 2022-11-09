package br.firzen.cacacapsulas.model;

public class Item {
	
	private long id;
	
	private String nome;
	
	private String tipo; //CAIXA ou CAPSULA
	
	private Integer qtd;
	
	public Item(long id, String nome, String tipo, Integer qtd) {
		new Item(id, nome, tipo);
		this.qtd = qtd;
	}
	
	public Item(long id, String nome, String tipo) {
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	

}
