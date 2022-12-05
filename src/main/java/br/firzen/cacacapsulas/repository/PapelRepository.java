package br.firzen.cacacapsulas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.firzen.cacacapsulas.model.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long>{
	public Papel findByNome(String nome);
}
