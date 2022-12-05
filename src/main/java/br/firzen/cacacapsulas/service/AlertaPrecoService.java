package br.firzen.cacacapsulas.service;

import org.springframework.stereotype.Service;

import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.repository.AlertaPrecoRepository;

@Service
public class AlertaPrecoService extends AbstractService<AlertaPreco>{
	public AlertaPreco findByTipo(Long usuarioId, String tipo) {
		return ((AlertaPrecoRepository)repository).findByUsuarioIdAndTipo(usuarioId, tipo);
	}
}
