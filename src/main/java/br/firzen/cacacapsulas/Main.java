package br.firzen.cacacapsulas;

import java.io.IOException;
import java.util.List;

import br.firzen.cacacapsulas.httpscraper.CaixaScraper;
import br.firzen.cacacapsulas.model.RegistroPreco;

public class Main {
	public static void main(String[] args) throws IOException {
		CaixaScraper caixa = new CaixaScraper();
		List<RegistroPreco> lista = caixa.extract();
		for(RegistroPreco reg: lista) {
			System.out.println(reg.toString());
			System.out.println("=================================");
		}
	}
}
