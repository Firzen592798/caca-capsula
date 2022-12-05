package br.firzen.cacacapsulas.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;


@NamedEntityGraph(
  name = "registro-item-graph",
  attributeNodes = {
    @NamedAttributeNode("item"),
  }
)
@Entity
public class RegistroPreco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_registro_preco")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
	@JoinColumn(name = "id_item", referencedColumnName = "id_item")
	private Item item;
	
	@Column
	private double preco;
	
	@Column
	private double precoOld;
	
	@Column
	private LocalDateTime dataCriacao = LocalDateTime.now();
	
	@Column
	private Integer numExecucao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	public Double getPrecoPorCapsula() {
		return preco / item.getQtd();
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

	public Integer getNumExecucao() {
		return numExecucao;
	}

	public void setNumExecucao(Integer numExecucao) {
		this.numExecucao = numExecucao;
	}
}
