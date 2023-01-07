package br.firzen.cacacapsulas.telegram;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.model.TelegramChat;
import br.firzen.cacacapsulas.repository.TelegramChatRepository;
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
    
    @Autowired
    private TelegramChatRepository repository;
    
	private String criarMensagem(List<RegistroPreco> listaPrecosUsuario){
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
	
    private boolean executarFiltro(RegistroPreco reg, AlertaPreco alertaPreco){
    	int qtdItems = reg.getItem().getTipo().equals("CAIXA") ? 1 : reg.getItem().getQtd();
    	return reg.getPreco() / qtdItems <= alertaPreco.getPreco() && reg.getItem().getTipo().equals(alertaPreco.getTipo());
    }
    
    private void processarUpdates(TelegramBot bot, List<Update> updates) {
		updates.forEach(update -> {
    		long chatId = update.message().chat().id();
    		//String responseText = tratarMensagem(update.message().text());
    		if(update.message().text().equals("/start")) {
    			processarRespostaStart(bot, chatId);
    		}else if(update.message().text().equals("/end")){
    			processarRespostaEnd(bot, chatId);
    		}
    		if(update.message().text().equals("/promocoes")) {
    			List<RegistroPreco> promocoes = encontrarListaPromocoes();
    			enviarMensagemPromocoes(promocoes, bot, chatId);
    		}
    	});
    }
    
	public void prepararBot() {
		// Create your bot passing the token received from @BotFather
		TelegramBot bot = new TelegramBot(TELEGRAM_API_KEY);
		
		// Register for updates
		bot.setUpdatesListener(updates -> {
			processarUpdates(bot, updates);
			return UpdatesListener.CONFIRMED_UPDATES_ALL;
		});
	}

	private void processarRespostaStart(TelegramBot bot, long chatId) {
		TelegramChat chat = repository.findByChatId(chatId);
		if(chat == null) {
			chat = new TelegramChat();
			chat.setChatId(chatId);
			repository.save(chat);
			bot.execute(new SendMessage(chatId, "Você foi registrado para receber notificações de promoções no bot"));
		}else {
			bot.execute(new SendMessage(chatId, "Você já está registrado no bot"));
		}
		
	}
	
	private void processarRespostaEnd(TelegramBot bot, long chatId) {
		TelegramChat chat = repository.findByChatId(chatId);
		if((chat) != null) {
			repository.deleteById(chat.getId());
			bot.execute(new SendMessage(chatId, "Você cancelou o registro para receber notificações de promoções"));
		}
	}
	
	private void enviarMensagemPromocoes(List<RegistroPreco> listaPrecosUsuario, TelegramBot bot, long chatId) {
		String mensagemBot = criarMensagem(listaPrecosUsuario);
		bot.execute(new SendMessage(chatId, mensagemBot));
	}	
	
	private List<RegistroPreco> encontrarListaPromocoes() {
		Iterable<AlertaPreco> alertaPrecoLista = alertaPrecoService.findAll();
    	List<RegistroPreco> registroPrecoBanco = rpService.listarPorDataHoje();
    	List<RegistroPreco> listaPrecosUsuario = new LinkedList<>();
    	for(AlertaPreco alertaPreco: alertaPrecoLista) {
    		listaPrecosUsuario.addAll(registroPrecoBanco.stream().filter((x) -> executarFiltro(x, alertaPreco)).collect(Collectors.toList()));
    	}
    	return listaPrecosUsuario;
	}
	
	public void dispararMensagensAgendadas(){
		List<TelegramChat> chatLista = repository.findAll();
		TelegramBot bot = new TelegramBot(TELEGRAM_API_KEY);
		List<RegistroPreco> listaPromocoes = encontrarListaPromocoes();
		chatLista.forEach(chat -> {
			enviarMensagemPromocoes(listaPromocoes, bot, chat.getChatId());
		});
	}
}
