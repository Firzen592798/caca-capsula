package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;

@Component
public class CaixaScraper implements IScraper{

	public List<RegistroPreco> extract() throws IOException {
		Elements elements = Jsoup.connect("https://www.nescafe-dolcegusto.com.br/sabores").get().getElementsByClass("product-card__info ");
		List<RegistroPreco> lista = new ArrayList<RegistroPreco>();
		for (Element element : elements) {
			RegistroPreco registro = new RegistroPreco();
			registro.setItem(new Item());
			
			Elements idItemElem = element.getElementsByClass("price-box");
			
			if(idItemElem.size() > 0) {
				registro.getItem().setId(Long.valueOf(idItemElem.first().attr("data-product-id")));
			
				registro.getItem().setNome(element.getElementsByClass("product-card__name--link").text());
				Element oldPrice = element.getElementsByAttributeValue("data-price-type", "oldPrice").first();
				if(oldPrice != null) {
					registro.setPrecoOld(Double.valueOf(oldPrice.attr("data-price-amount")));
				}
				
				Element finalPriceContainer = element.getElementsByClass("product-card__price--current").first();
				Element finalPrice = finalPriceContainer.getElementsByClass("price-wrapper").first();
				if(finalPrice != null) {
					registro.setPreco(Double.valueOf(finalPrice.attr("data-price-amount")));
				}
				
				Element qtdCapsulasElem = element.getElementsByClass("product-card__capsules").select("b").first();
				registro.getItem().setQtd(qtdCapsulasElem != null ? Integer.valueOf(qtdCapsulasElem.text().replaceAll("[^0-9]", "")) : null);	
					
				registro.getItem().setTipo("CAIXA");
				if(registro.getItem().getQtd() != null && registro.getItem().getNome() != null && registro.getPreco() > 0)
					lista.add(registro);	
			}
		}
		return lista;
	}
}
