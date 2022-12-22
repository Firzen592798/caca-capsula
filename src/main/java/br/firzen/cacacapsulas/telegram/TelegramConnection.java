package br.firzen.cacacapsulas.telegram;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.service.AlertaPrecoService;
import br.firzen.cacacapsulas.service.RegistroPrecoService;

//API utilizada: https://github.com/pengrad/java-telegram-bot-api
@PropertySource("classpath:config.properties")
@Component
public class TelegramConnection {
	@Value("${telegram.api.key}")
	private String TELEGRAM_API_KEY;
	
    @Autowired
    private RegistroPrecoService rpService;
    
    @Autowired
    private AlertaPrecoService alertaPrecoService;
    
	public String criarMensagem(List<RegistroPreco> listaPrecosUsuario){
		if(listaPrecosUsuario.size() > 0) {
			StringBuilder textoSb = new StringBuilder("Os seguintes itens estão em promoção:\n");
			for(RegistroPreco rp: listaPrecosUsuario) {
				textoSb.append(rp.getItem().getNome())
				.append( " - ")
				.append("R$ "+String.format("%.2f",rp.getPreco()))
				.append("\n");
			}
			textoSb.append("\nCaça-cápsulas");
			return textoSb.toString();
		}
		return "Não há promoções hoje";
	}
	
    boolean executarFiltro(RegistroPreco reg, AlertaPreco alertaPreco){
    	int qtdItems = reg.getItem().getTipo().equals("CAIXA") ? 1 : reg.getItem().getQtd();
    	return reg.getPreco() / qtdItems <= alertaPreco.getPreco() && reg.getItem().getTipo().equals(alertaPreco.getTipo());
    }
    
	public void prepararBot() {
		// Create your bot passing the token received from @BotFather
		TelegramBot bot = new TelegramBot(TELEGRAM_API_KEY);
			
		// Register for updates
		bot.setUpdatesListener(new UpdatesListener() {
		    @Override
		    public int process(List<Update> updates) {

		        // process updates
		    	updates.forEach(update -> {
		    		long chatId = update.message().chat().id();
		    		//String responseText = tratarMensagem(update.message().text());
		    		
		    		if(update.message().text().equals("/caixas")) {
			    		Iterable<AlertaPreco> alertaPrecoLista = alertaPrecoService.findAll();
			        	List<RegistroPreco> registroPrecolista = rpService.listarPorDataHoje();
			        	for(AlertaPreco alertaPreco: alertaPrecoLista) {
			        		List<RegistroPreco> listaPrecosUsuario = registroPrecolista.stream().filter((x) -> executarFiltro(x, alertaPreco)).collect(Collectors.toList());
			        		String mensagemBot = criarMensagem(listaPrecosUsuario);
			        		SendResponse response = bot.execute(new SendMessage(chatId, mensagemBot));
			        	}
		    		}
		    	});
				
		        return UpdatesListener.CONFIRMED_UPDATES_ALL;
		    }
		});

		// Send messages

	}
	
	
	public String tratarMensagem(String text) {
		String response;
		switch(text) {
			case "/start":
				response = "Olá! Bem vindo ao bot de cápsulas de café da dolce gusto";
				break;
			default:
				response = "Não há nada a ser tratado";
		}
		return response;
	}
}
