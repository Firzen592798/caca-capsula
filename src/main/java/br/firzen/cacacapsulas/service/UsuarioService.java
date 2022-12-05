package br.firzen.cacacapsulas.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.firzen.cacacapsulas.model.Papel;
import br.firzen.cacacapsulas.model.Usuario;
import br.firzen.cacacapsulas.repository.PapelRepository;
import br.firzen.cacacapsulas.repository.UsuarioRepository;

@Service
public class UsuarioService extends AbstractService<Usuario>{
	
	@Autowired
	private PapelRepository papelRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Usuario save(Usuario obj) {
		obj.setSenha(passwordEncoder.encode(obj.getSenha()));
		Papel papel = papelRepository.findByNome("COMUM");
		obj.setPapeis(new HashSet<Papel>());
		obj.getPapeis().add(papel);
		return super.save(obj);
	}
}
