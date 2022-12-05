package br.firzen.cacacapsulas.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.firzen.cacacapsulas.httpscraper.IScraper;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.repository.ItemRepository;
import br.firzen.cacacapsulas.repository.RegistroPrecoRepository;

@Service
public class RegistroPrecoService extends AbstractService<RegistroPreco>{
	
	@Autowired
	private RegistroPrecoRepository repository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public void executarImportacao(IScraper scraper) throws IOException{
		List<RegistroPreco> lista = scraper.extract();
		salvarEmLote(lista);
	}
	
	public void salvarEmLote(List<RegistroPreco> lista) {
		if(lista.size() > 0) {
			Integer numExecucao = findMaxNumExecucaoByTipo(lista.get(0).getItem().getTipo()) + 1;
			for(RegistroPreco rp: lista) {
				rp.setNumExecucao(numExecucao);
				itemRepository.save(rp.getItem());
			}
			repository.saveAll(lista);
		}
	}
	
	public Integer findMaxNumExecucaoByTipo(String tipo) {
		RegistroPreco first = repository.findFirstByItemTipoOrderByNumExecucaoDesc(tipo);
		return first.getNumExecucao();
	}
	
	public List<RegistroPreco> listarAtual(String tipo) {
		Integer numExecucao = findMaxNumExecucaoByTipo(tipo);
		return repository.findByItemTipoAndNumExecucaoOrderByItemNome(tipo, numExecucao);
	}
	
	public List<RegistroPreco> listarPorItem(Long idItem) {
		return repository.findByItemIdOrderByDataCriacaoDesc(idItem);
	}
	
	public List<RegistroPreco> listarPorDataHoje(){
		return repository.findByDataHoje();
	}
}
