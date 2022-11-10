package br.firzen.cacacapsulas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.repository.RegistroPrecoRepository;

@Service
public class RegistroPrecoService{
	
	@Autowired
	public RegistroPrecoRepository repository;
	
	public void salvarEmLote(List<RegistroPreco> lista) {
		repository.saveAll(lista);
	}
}
