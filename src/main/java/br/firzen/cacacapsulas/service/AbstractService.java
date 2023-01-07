package br.firzen.cacacapsulas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractService<T> {
	
	@Autowired
	protected JpaRepository<T, Long> repository;
	
	public Iterable<T> findAll(){
		return repository.findAll();
	}
	
	public T save(T obj)  {
		return repository.save(obj);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public void delete(T obj) {
		repository.delete(obj);
	}
	
	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	public Optional<T> findById(Long id){
		return repository.findById(id);
	}
}
