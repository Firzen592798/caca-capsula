package br.firzen.cacacapsulas.builder;

import java.time.LocalDateTime;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;

public class RegistroPrecoBuilder {
	private Long id;
	
	private Item item;
	
	private double preco;
	
	private double precoOld;
	
	private LocalDateTime dataCriacao;
	
	private Integer numExecucao;
	
	public RegistroPrecoBuilder() {
	}

	public RegistroPrecoBuilder withId(Long id) {
		this.id = id;
		return this;
	}

	public RegistroPrecoBuilder withItem(Item item) {
		this.item = item;
		return this;
	}

	public RegistroPrecoBuilder withPreco(double preco) {
		this.preco = preco;
		return this;
	}

	public RegistroPrecoBuilder withPrecoOld(double precoOld) {
		this.precoOld = precoOld;
		return this;
	}

	public RegistroPrecoBuilder withDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
		return this;
	}

	public RegistroPrecoBuilder withNumExecucao(Integer numExecucao) {
		this.numExecucao = numExecucao;
		return this;
	}
	
	public RegistroPreco build() {
		RegistroPreco rp = new RegistroPreco();
		rp.setId(id);
		rp.setDataCriacao(dataCriacao);
		rp.setItem(item);
		rp.setPreco(preco);
		rp.setNumExecucao(numExecucao);
		rp.setPrecoOld(precoOld);
		return rp;
	}
	
	public static RegistroPrecoBuilder getDefault() {
		return new RegistroPrecoBuilder()
			.withItem(ItemBuilder.getDefault().build())
			.withNumExecucao(1)
			.withPreco(25);
	}
}
