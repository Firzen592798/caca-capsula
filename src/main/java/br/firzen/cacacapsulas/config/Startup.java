package br.firzen.cacacapsulas.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.telegram.TelegramConnection;

@Component
public class Startup {
	
	@Autowired
	TelegramConnection telegramConn;
	
	@PostConstruct
	public void performStateChecks() {
		telegramConn.prepararBot();
	}

}
