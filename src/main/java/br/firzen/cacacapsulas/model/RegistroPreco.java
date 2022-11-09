package br.firzen.cacacapsulas.model;

import java.time.LocalDateTime;

public class RegistroPreco {
	
	private long id;
	
	private Item item;
	
	private double preco;
	
	private double precoOld;
	
	private LocalDateTime dataCriacao = LocalDateTime.now();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public double getPrecoOld() {
		return precoOld;
	}

	public void setPrecoOld(double precoOld) {
		this.precoOld = precoOld;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: ").append(item.getId()).append("\n")
		.append("Item: ").append(item.getNome()).append("\n")
		.append("Preço: ").append(preco).append("\n")
		.append("Data: ").append(dataCriacao);
		return sb.toString();
	}
}
