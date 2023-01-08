package br.firzen.cacacapsulas.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<RegistroPreco> salvarEmLote(List<RegistroPreco> lista) {
		if(lista != null && lista.size() > 0) {
			Integer numExecucao = findMaxNumExecucaoByTipo(lista.get(0).getItem().getTipo()) + 1;
			for(RegistroPreco rp: lista) {
				rp.setNumExecucao(numExecucao);
				itemRepository.save(rp.getItem());
			}
			return repository.saveAll(lista);
		}
		return Arrays.asList();
	}
	
	public Integer findMaxNumExecucaoByTipo(String tipo) {
		RegistroPreco first = repository.findFirstByItemTipoOrderByNumExecucaoDesc(tipo);
		if(first == null) {
			return 0;
		}
		return first.getNumExecucao();
	}
	
    private boolean executarFiltro(RegistroPreco reg){
    	double preco = 1.4;
    	int qtdItems = reg.getItem().getTipo().equals("CAIXA") ? reg.getItem().getQtd() : 1;
    	return reg.getPreco() / qtdItems <= preco;
    }
	
	public List<RegistroPreco> encontrarListaPromocoes() {
    	List<RegistroPreco> registroPrecoBanco = listarPorDataHoje();
    	List<RegistroPreco> listaPrecosUsuario = registroPrecoBanco.stream()
    			.filter(this::executarFiltro)
    			.sorted((x1, x2) -> x1.getItem().getTipo().compareTo(x2.getItem().getTipo()))
    			.collect(Collectors.toList());
    	return listaPrecosUsuario;
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
