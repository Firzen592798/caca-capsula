package br.firzen.cacacapsulas.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.httpscraper.CaixaScraper;
import br.firzen.cacacapsulas.httpscraper.CapsulaScraper;
import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.service.RegistroPrecoService;
import br.firzen.cacacapsulas.telegram.TelegramConnection;

@Component
public class Scheduler {
	
	public final static String BR_TIMEZONE = "GMT-3";
	
	@Autowired
	private CaixaScraper caixaScraper;
	
	@Autowired
	private CapsulaScraper capsulaScraper;
	
    @Autowired
    private RegistroPrecoService rpService;
    
    @Autowired
    private TelegramConnection telegramConnection;

    //Executa a ação de 10h todo dia
    @Scheduled(cron = "0 0 10 * * *", zone = BR_TIMEZONE)
    public void executarImportacao(){
    	try {
    		rpService.executarImportacao(caixaScraper);
			Thread.sleep(10000);
			rpService.executarImportacao(capsulaScraper);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    boolean executarFiltro(RegistroPreco reg, AlertaPreco alertaPreco){
    	int qtdItems = reg.getItem().getTipo().equals("CAIXA") ? 1 : reg.getItem().getQtd();
    	return reg.getPreco() / qtdItems <= alertaPreco.getPreco() && reg.getItem().getTipo().equals(alertaPreco.getTipo());
    }
    
    
    //Executa a ação de 10:05h todo dia
    @Scheduled(cron = "0 5 10 * * *", zone = BR_TIMEZONE)
    public void dispararMensagensAgendadasTelegram(){
    	telegramConnection.dispararMensagensAgendadas();
    }
}
