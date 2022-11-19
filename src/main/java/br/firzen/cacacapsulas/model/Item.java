package br.firzen.cacacapsulas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {
	
	@Id
	@Column(name="id_item")
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String tipo; //CAIXA ou CAPSULA
	
	@Column
	private Integer qtd;
	
	public Item(Long id, String nome, String tipo, Integer qtd) {
		new Item(id, nome, tipo);
		this.qtd = qtd;
	}
	
	public Item(Long id, String nome, String tipo) {
		this.id = id;
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
	
	public String getPageName() {
		return tipo.equals("CAIXA") ? "caixa" : "unidade";
	}
}
