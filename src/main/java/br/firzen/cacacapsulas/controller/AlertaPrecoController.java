package br.firzen.cacacapsulas.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.model.Usuario;
import br.firzen.cacacapsulas.service.AlertaPrecoService;

@Controller
@RequestMapping("/alerta-precos")
public class AlertaPrecoController extends AbstractController<AlertaPreco>{
	
	private final String URL_PAGE = "/alerta-precos";
	
	@GetMapping
	public String list(Model model) {
		Usuario usuario = usuarioAdvice.getUsuarioLogado();
		AlertaPreco alertaCapsula = ((AlertaPrecoService)service).findByTipo(usuario.getId(), "CAPSULA");
		AlertaPreco alertaCaixa = ((AlertaPrecoService)service).findByTipo(usuario.getId(), "CAIXA");
		model.addAttribute("alertaCaixa", alertaCaixa);
		model.addAttribute("alertaCapsula", alertaCapsula);
		return URL_PAGE + URL_LIST;
	}
	
	public String buildForm(Model model, String tipo){
		AlertaPreco alertaPreco =  new AlertaPreco();
		alertaPreco.setUsuario(usuarioAdvice.getUsuarioLogado());
		alertaPreco.setTipo(tipo);
		model.addAttribute("alertaPreco", alertaPreco);
		return URL_PAGE + URL_FORM;
	}

	
	@GetMapping("/caixa"+URL_FORM)
	public String formCaixa(Model model) {
		return buildForm(model, "CAIXA");
	}
	
	@GetMapping("/capsula"+URL_FORM)
	public String formCapsula(Model model) {
		return buildForm(model, "CAPSULA");
	}
	
	@GetMapping("/caixa"+URL_DELETE)
	public String deleteCaixa(Model model) {
		Usuario usuario = usuarioAdvice.getUsuarioLogado();
		AlertaPreco alertaCaixa = ((AlertaPrecoService)service).findByTipo(usuario.getId(), "CAIXA");
		service.delete(alertaCaixa);
		model.addAttribute("success", "Alerta removido com sucesso com sucesso");
		return "redirect:../.."+URL_PAGE;
	}
	
	@GetMapping("/capsula"+URL_DELETE)
	public String deleteCapsula(Model model) {
		Usuario usuario = usuarioAdvice.getUsuarioLogado();
		AlertaPreco alertaCapsula = ((AlertaPrecoService)service).findByTipo(usuario.getId(), "CAPSULA");
		service.delete(alertaCapsula);
		model.addAttribute("success", "Alerta removido com sucesso com sucesso");
		return "redirect:../.."+URL_PAGE;
	}

	@GetMapping(URL_EDIT + "/{id}")
	public String edit(@PathVariable Long id, Model model) {
		AlertaPreco alertaPreco = service.findById(id).orElseThrow(() -> new IllegalArgumentException());
		model.addAttribute("alertaPreco", alertaPreco);
		return URL_PAGE + URL_FORM;
	}

	@PostMapping(URL_FORM)
	public String form(@Valid AlertaPreco alertaPreco, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return URL_PAGE+URL_FORM;
		} else {
			service.save(alertaPreco);
			return "redirect:";
		}
	}

}
