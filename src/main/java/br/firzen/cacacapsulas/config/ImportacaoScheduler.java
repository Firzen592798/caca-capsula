package br.firzen.cacacapsulas.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.httpscraper.CaixaScraper;
import br.firzen.cacacapsulas.httpscraper.GraphQLScraper;
import br.firzen.cacacapsulas.service.RegistroPrecoService;

@Component
public class ImportacaoScheduler {
	@Autowired
	private CaixaScraper caixaScraper;
	
	@Autowired
	private GraphQLScraper capsulaScraper;
	
    @Autowired
    private RegistroPrecoService rpService;

    //Executa a ação de 10h todo dia
    @Scheduled(cron = "0 0 10 * * *")
    public void execute(){
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
}
