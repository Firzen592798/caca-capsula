package br.firzen.cacacapsulas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;

import br.firzen.cacacapsulas.model.Usuario;
import br.firzen.cacacapsulas.repository.UsuarioRepository;

@ControllerAdvice
public class UsuarioAdvice {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario getUsuarioLogado(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		return usuarioRepository.findByLogin(username);
	}
}
