package br.firzen.cacacapsulas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.firzen.cacacapsulas.model.RegistroPreco;

@Repository
public interface RegistroPrecoRepository extends JpaRepository<RegistroPreco, Long>{

}
