package br.firzen.cacacapsulas.telegram;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.firzen.cacacapsulas.builder.ItemBuilder;
import br.firzen.cacacapsulas.builder.RegistroPrecoBuilder;
import br.firzen.cacacapsulas.model.RegistroPreco;

@ExtendWith(MockitoExtension.class)
public class TelegramConnectionTest {

	@InjectMocks
	private TelegramConnection telegramConnection;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void criarMensagemSemPromocoesTest(){
		String response = telegramConnection.criarMensagem(Arrays.asList());
		Assertions.assertTrue(response.contains("Não há promoções hoje"));
	}
	
	@Test
	void criarMensagemPromocaoCaixaTest(){
		RegistroPreco rp = RegistroPrecoBuilder.getDefault().withPreco(15.0).build();
		String response = telegramConnection.criarMensagem(Arrays.asList(rp));
		Assertions.assertTrue(response.contains("Caixas:"));
		Assertions.assertFalse(response.contains("Cápsulas:"));
		Assertions.assertTrue(response.contains("Item"));
	}
	
	@Test
	void criarMensagemPromocaoCapsulasTest(){
		RegistroPreco rp = RegistroPrecoBuilder.getDefault().withItem(new ItemBuilder().withQtd(1).withTipo("CAPSULA").withNome("CAFE EXPRESSO").build()).withPreco(1.0).build();
		String response = telegramConnection.criarMensagem(Arrays.asList(rp));
		Assertions.assertFalse(response.contains("Caixas:"));
		Assertions.assertTrue(response.contains("Cápsulas:"));
		Assertions.assertTrue(response.contains("CAFE EXPRESSO"));
	}
	
	@Test
	void criarMensagemPromocaoCaixasECapsulasTest(){
		RegistroPreco rp1 = RegistroPrecoBuilder.getDefault().withPreco(15.0).withItem(ItemBuilder.getDefault().withNome("CAIXA EXPRESSO").build()).build();
		RegistroPreco rp2 = RegistroPrecoBuilder.getDefault().withPreco(15.0).withItem(ItemBuilder.getDefault().withNome("CAIXA CAPUCCINO").build()).build();
		RegistroPreco rp3 = RegistroPrecoBuilder.getDefault().withItem(new ItemBuilder().withQtd(1).withTipo("CAPSULA").withNome("CAPSULA EXPRESSO").build()).withPreco(1.0).build();
		RegistroPreco rp4 = RegistroPrecoBuilder.getDefault().withItem(new ItemBuilder().withQtd(1).withTipo("CAPSULA").withNome("CAPSULA CAPUCCINO").build()).withPreco(1.0).build();
		String response = telegramConnection.criarMensagem(Arrays.asList(rp1, rp2, rp3, rp4));
		Assertions.assertTrue(response.contains("Caixas:"));
		Assertions.assertTrue(response.contains("Cápsulas:"));
		Assertions.assertTrue(response.contains("CAIXA EXPRESSO - R$ 15,00"));
		Assertions.assertTrue(response.contains("CAIXA CAPUCCINO - R$ 15,00"));
		Assertions.assertTrue(response.contains("CAPSULA EXPRESSO - R$ 1,00"));
		Assertions.assertTrue(response.contains("CAPSULA CAPUCCINO - R$ 1,00"));
	}
}
