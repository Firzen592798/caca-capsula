package br.firzen.cacacapsulas.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.firzen.cacacapsulas.httpscraper.CaixaScraper;
import br.firzen.cacacapsulas.httpscraper.GraphQLScraper;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.service.RegistroPrecoService;

@RestController
@RequestMapping("/importacao")
public class MainController {
	
	@Autowired
	private RegistroPrecoService registroPrecoService;
	
	@Autowired
	private CaixaScraper caixaScraper;
	
	@Autowired
	private GraphQLScraper capsulaScraper;
	
	@GetMapping("/caixa")
	public String importar() {
		List<RegistroPreco> lista;
		try {
			lista = caixaScraper.extract();
			registroPrecoService.salvarEmLote(lista);
		} catch (IOException e) {
			return "erro";
		}
		
		return "Importado com sucesso";
	}
	
	@GetMapping("/capsula")
	public String importarCapsula() {
		List<RegistroPreco> lista;
		try {
			lista = capsulaScraper.extract();
			lista.stream().forEach(s -> System.out.println(s));
			registroPrecoService.salvarEmLote(lista);
		} catch (IOException e) {
			return "erro";
		}
		
		return "Importado com sucesso";
	}
}
