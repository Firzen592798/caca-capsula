package br.firzen.cacacapsulas.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.httpscraper.CaixaScraper;
import br.firzen.cacacapsulas.httpscraper.GraphQLScraper;
import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.service.AlertaPrecoService;
import br.firzen.cacacapsulas.service.EnviarEmailService;
import br.firzen.cacacapsulas.service.RegistroPrecoService;
import br.firzen.cacacapsulas.telegram.TelegramConnection;

@Component
public class Scheduler {
	@Autowired
	private CaixaScraper caixaScraper;
	
	@Autowired
	private GraphQLScraper capsulaScraper;
	
    @Autowired
    private RegistroPrecoService rpService;
    
    @Autowired
    private AlertaPrecoService alertaPrecoService;
    
    @Autowired
    private EnviarEmailService enviarEmailService;
    
    @Autowired
    private TelegramConnection telegramConnection;

    //Executa a ação de 10h todo dia
    @Scheduled(cron = "0 0 10 * * *")
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
    @Scheduled(cron = "0 5 10 * * *")
    //@Scheduled(initialDelay = 100, fixedRate = 1000000)
    public void executarEnvioAlertaPreco(){
    	Iterable<AlertaPreco> alertaPrecoLista = alertaPrecoService.findAll();
    	List<RegistroPreco> registroPrecolista = rpService.listarPorDataHoje();
    	for(AlertaPreco alertaPreco: alertaPrecoLista) {
    		List<RegistroPreco> listaPrecosUsuario = registroPrecolista.stream().filter((x) -> executarFiltro(x, alertaPreco)).collect(Collectors.toList());
    		enviarEmailService.sendMailAlertaCapsula(alertaPreco, listaPrecosUsuario);
    	}
    }
    
    //Executa a ação de 10:05h todo dia
    //@Scheduled(cron = "0 5 10 * * *")
    @Scheduled(initialDelay = 100, fixedRate = 1000000)
    public void enviarMensagensTelegram(){
    	telegramConnection.prepararBot();
    }
}
