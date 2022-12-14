package br.firzen.cacacapsulas.controller;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.firzen.cacacapsulas.model.Usuario;

@Controller
@RequestMapping("/usuario")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsuarioController extends AbstractController<Usuario>{
	
	private final String URL_PAGE= "usuario";
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("usuarioLista", service.findAll());
		return URL_PAGE + URL_LIST;
	}

	@GetMapping(URL_FORM)
	public String form(Model model) {
		model.addAttribute("usuario", new Usuario());
		return URL_PAGE + URL_FORM;
	}

	@GetMapping(URL_EDIT + "/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Usuario user = service.findById(id).orElseThrow(() -> new IllegalArgumentException());
		model.addAttribute("usuario", user);
		return URL_PAGE + URL_FORM;
	}

	@PostMapping(URL_FORM)
	public String form(@Valid Usuario usuario, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return URL_PAGE+URL_FORM;
		} else {
			service.save(usuario);
			return "redirect:";
		}
	}

	@GetMapping(URL_DELETE + "/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes ra) {
		service.deleteById(id);
		return "redirect:/user";
	}
}
