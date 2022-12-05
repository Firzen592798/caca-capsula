package br.firzen.cacacapsulas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.firzen.cacacapsulas.model.Usuario;
import br.firzen.cacacapsulas.repository.UsuarioRepository;


public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired 
	private UsuarioRepository usuarioRepository;
	
	
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findByLogin(username);
         
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
         
        return new UserDetailsImpl(user);
    }
}
