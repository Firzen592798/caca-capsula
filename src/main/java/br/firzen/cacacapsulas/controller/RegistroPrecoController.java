package br.firzen.cacacapsulas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.service.RegistroPrecoService;

@Controller
@RequestMapping("/")
public class RegistroPrecoController {
	
	@Autowired
	private RegistroPrecoService registroPrecoService;
	
	@GetMapping("/caixa")
	public String index(Model model) {
		List<RegistroPreco> registroPrecoLista = registroPrecoService.listarAtual("CAIXA");
		model.addAttribute("registroPrecoLista", registroPrecoLista);
		return "items/list";
	}
	
	@GetMapping("/unidade")
	public String unidade(Model model) {
		List<RegistroPreco> registroPrecoLista = registroPrecoService.listarAtual("CAPSULA");
		model.addAttribute("registroPrecoLista", registroPrecoLista);
		return "items/list";
	}
	
	@GetMapping("/view/{id}")
	public String view(Model model, @PathVariable Long id) {
		List<RegistroPreco> registroPrecoLista = registroPrecoService.listarPorItem(id);
		model.addAttribute("registroPrecoLista", registroPrecoLista);
		model.addAttribute("item", registroPrecoLista.isEmpty() ? null : registroPrecoLista.get(0).getItem());
		return "items/view";
	}
	
	
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
	
}
