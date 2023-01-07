package br.firzen.cacacapsulas.builder;

import br.firzen.cacacapsulas.model.Item;

public class ItemBuilder {
	private Long id;

	private String nome;

	private String tipo; 

	private Integer qtd;
	
	public ItemBuilder() {}
	
	public ItemBuilder withNome(String nome) {
		this.nome = nome;
		return this;
	}

	public ItemBuilder withTipo(String tipo) {
		this.tipo = tipo;
		return this;
	}
	
	public ItemBuilder withQtd(Integer qtd) {
		this.qtd = qtd;
		return this;
	}
	
	public ItemBuilder withId(Long id) {
		this.id = id;
		return this;
	}
	
	public Item build() {
		Item item = new Item();
		item.setId(id);
		item.setNome(nome);
		item.setTipo(tipo);
		item.setQtd(qtd);
		return item;
	}
	
	public static ItemBuilder getDefault() {
		return new ItemBuilder()
			.withNome("Item")
			.withQtd(16)
			.withTipo("CAIXA");
	}
}
