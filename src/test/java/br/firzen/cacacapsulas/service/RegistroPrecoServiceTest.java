package br.firzen.cacacapsulas.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.firzen.cacacapsulas.builder.ItemBuilder;
import br.firzen.cacacapsulas.builder.RegistroPrecoBuilder;
import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;
import br.firzen.cacacapsulas.repository.ItemRepository;
import br.firzen.cacacapsulas.repository.RegistroPrecoRepository;

@ExtendWith(MockitoExtension.class)
public class RegistroPrecoServiceTest {
	
	@InjectMocks
	private RegistroPrecoService service;
	
	@Mock
	private RegistroPrecoRepository repository;
	
	@Mock
	private ItemRepository itemRepository;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void salvarEmLotePrimeiroItemTest(){
		RegistroPreco registroPreco1 = RegistroPrecoBuilder.getDefault().withNumExecucao(null).build();
		Mockito.when(repository.findFirstByItemTipoOrderByNumExecucaoDesc("CAIXA")).thenReturn(null);
		Mockito.when(repository.saveAll(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
		List<RegistroPreco> lista = Arrays.asList(registroPreco1);
		
		List<RegistroPreco> response = service.salvarEmLote(lista);
		
		Mockito.verify(repository).saveAll(lista);
		Assertions.assertEquals(1, response.get(0).getNumExecucao());
	}
	
	@Test
	void salvarEmLoteDoisItemsTest(){
		
		Item item = ItemBuilder.getDefault().withNome("Item 2").build();
		RegistroPreco registroPreco1 = RegistroPrecoBuilder.getDefault().build();
		RegistroPreco registroPreco2 = RegistroPrecoBuilder.getDefault().withItem(item).build();
		Mockito.when(repository.findFirstByItemTipoOrderByNumExecucaoDesc("CAIXA")).thenReturn(registroPreco1);
		Mockito.when(repository.saveAll(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
		List<RegistroPreco> lista = Arrays.asList(registroPreco1, registroPreco2);
		
		List<RegistroPreco> response = service.salvarEmLote(lista);
		Mockito.verify(repository).saveAll(lista);
		Assertions.assertEquals(2, response.get(0).getNumExecucao());
		Assertions.assertEquals(2, response.get(1).getNumExecucao());
	}
}
