package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;




public class CaixaScraper {

	public List<RegistroPreco> extract() throws IOException {
		Elements elements = Jsoup.connect("https://www.nescafe-dolcegusto.com.br/sabores").get().getElementsByClass("product-card__info ");
		List<RegistroPreco> lista = new ArrayList<RegistroPreco>();
		for (Element element : elements) {
			RegistroPreco registro = new RegistroPreco();
			registro.setItem(new Item());
			registro.getItem().setNome(element.getElementsByClass("product-card__name--link").text());
			Element oldPrice = element.getElementsByAttributeValue("data-price-type", "oldPrice").first();
			if(oldPrice != null) {
				registro.setPrecoOld(Double.valueOf(oldPrice.attr("data-price-amount")));
			}
			
			Element finalPrice = element.getElementsByAttributeValue("data-price-type", "finalPrice").first();
			if(finalPrice != null) {
				registro.setPreco(Double.valueOf(finalPrice.attr("data-price-amount")));
			}
			
			Element qtdCapsulasElem = element.getElementsByClass("product-card__capsules").select("b").first();
			registro.getItem().setQtd(qtdCapsulasElem != null ? Integer.valueOf(qtdCapsulasElem.text().replaceAll("[^0-9]", "")) : null);			
			lista.add(registro);
		}
		return lista;
	}
}
