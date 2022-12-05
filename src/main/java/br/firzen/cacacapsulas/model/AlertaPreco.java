package br.firzen.cacacapsulas.model;

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
  name = "alerta-preco-graph",
  attributeNodes = {
    @NamedAttributeNode("usuario"),
  }
)
@Entity
public class AlertaPreco {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_alerta_preco")
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
	private Usuario usuario;
	
	private String tipo;
	
	private double preco;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}
}
