package br.firzen.cacacapsulas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.firzen.cacacapsulas.model.RegistroPreco;

@Repository
public interface RegistroPrecoRepository extends JpaRepository<RegistroPreco, Long>{
	
	public RegistroPreco findFirstByItemTipoOrderByNumExecucaoDesc(String itemTipo);

	@EntityGraph(value = "registro-item-graph", type = EntityGraph.EntityGraphType.LOAD)
	public List<RegistroPreco> findByItemTipoAndNumExecucaoOrderByItemNome(String itemTipo, Integer numExecucao);
}
