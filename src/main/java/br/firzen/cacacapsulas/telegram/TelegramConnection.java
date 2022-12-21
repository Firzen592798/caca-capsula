package br.firzen.cacacapsulas.telegram;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

//API utilizada: https://github.com/pengrad/java-telegram-bot-api
@PropertySource("classpath:config.properties")
@Component
public class TelegramConnection {
	@Value("${telegram.api.key}")
	private String TELEGRAM_API_KEY;
	
	public void createConnection() {
		// Create your bot passing the token received from @BotFather
		TelegramBot bot = new TelegramBot(TELEGRAM_API_KEY);
			
		// Register for updates
		bot.setUpdatesListener(new UpdatesListener() {
		    @Override
		    public int process(List<Update> updates) {

		        // process updates
		    	updates.forEach(update -> {
		    		long chatId = update.message().chat().id();
		    		String responseText = tratarMensagem(update.message().text());
		    		SendResponse response = bot.execute(new SendMessage(chatId, responseText));
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
