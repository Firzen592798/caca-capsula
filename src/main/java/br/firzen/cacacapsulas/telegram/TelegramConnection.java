package br.firzen.cacacapsulas.telegram;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.model.TelegramChat;
import br.firzen.cacacapsulas.repository.TelegramChatRepository;
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
    private TelegramChatRepository repository;
    
	String criarMensagem(List<RegistroPreco> listaPrecosUsuario){
		List<RegistroPreco> caixas = listaPrecosUsuario.stream().filter(x -> x.getItem().getTipo().equals("CAIXA")).collect(Collectors.toList());
		List<RegistroPreco> capsulas = listaPrecosUsuario.stream().filter(x -> x.getItem().getTipo().equals("CAPSULA")).collect(Collectors.toList());
		String data = "("+LocalDate.now(ZoneId.of("America/Sao_Paulo")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")";
		if(listaPrecosUsuario.size() > 0) {
			StringBuilder textoSb = new StringBuilder(data+" Os seguintes itens estão em promoção\n");
			
			if(caixas.size() > 0) {
				textoSb.append("Caixas:\n");
				for(RegistroPreco rp: caixas) {
					textoSb.append(rp.getItem().getNome())
					.append( " - ")
					.append("R$ "+String.format("%.2f",rp.getPreco()))
					.append("\n");
				}
			}
			if(capsulas.size() > 0) {
				textoSb.append("Cápsulas:\n");
				for(RegistroPreco rp: capsulas) {
					textoSb.append(rp.getItem().getNome())
					.append( " - ")
					.append("R$ "+String.format("%.2f",rp.getPreco()))
					.append("\n");
				}
			}
			
			textoSb.append("\nCaça-cápsulas");
			return textoSb.toString();
		}
		return data+" Não há promoções hoje";
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
    			List<RegistroPreco> promocoes = rpService.encontrarListaPromocoes();
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
	
	public void dispararMensagensAgendadas(){
		List<TelegramChat> chatLista = repository.findAll();
		TelegramBot bot = new TelegramBot(TELEGRAM_API_KEY);
		List<RegistroPreco> listaPromocoes = rpService.encontrarListaPromocoes();
		chatLista.forEach(chat -> {
			enviarMensagemPromocoes(listaPromocoes, bot, chat.getChatId());
		});
	}
}
