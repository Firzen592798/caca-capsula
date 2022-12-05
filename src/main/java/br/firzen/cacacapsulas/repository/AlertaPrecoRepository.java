package br.firzen.cacacapsulas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.firzen.cacacapsulas.model.AlertaPreco;

public interface AlertaPrecoRepository extends JpaRepository<AlertaPreco, Long>{
	
	@Override
	@EntityGraph(value = "alerta-preco-graph", type = EntityGraph.EntityGraphType.LOAD)
	public List<AlertaPreco> findAll();
	
	@EntityGraph(value = "alerta-preco-graph", type = EntityGraph.EntityGraphType.LOAD)
	public AlertaPreco findByUsuarioIdAndTipo(Long usuarioId, String tipo);
}
