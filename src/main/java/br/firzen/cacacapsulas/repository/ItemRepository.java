package br.firzen.cacacapsulas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.firzen.cacacapsulas.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

}
