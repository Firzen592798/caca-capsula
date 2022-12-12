package br.firzen.cacacapsulas.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.firzen.cacacapsulas.model.Usuario;
import br.firzen.cacacapsulas.service.UsuarioService;

@RequestMapping
@Controller
public class PublicController {

	@Autowired
	private UsuarioService usuarioService;
	
	private final static String URL_FORM_USUARIO = "/cadastro";
	
	@GetMapping(URL_FORM_USUARIO)
	public String form(Model model) {
		model.addAttribute("usuario", new Usuario());
		return URL_FORM_USUARIO;
	}

	@PostMapping(URL_FORM_USUARIO)
	public String form(@Valid Usuario usuario, BindingResult br, Model model) {
		if (br.hasErrors()) {
			return URL_FORM_USUARIO;
		} else {
			usuarioService.save(usuario);
			return "redirect:";
		}
	}
}
