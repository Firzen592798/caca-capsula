package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;

public class CapsulaScraperOld implements IScraper {
	@Override
	public List<RegistroPreco> extract() throws IOException {

		Document document = Jsoup.connect("https://www.nescafe-dolcegusto.com.br/do-seu-jeito#/capsule-selection/100")
				.get();
		// document.text();
		System.out.println(document.select("body > *'"));
		// System.out.println(document.text());
		Elements elements = document.getElementsByClass("slick-slide");
		List<RegistroPreco> lista = new ArrayList<RegistroPreco>();
		for (Element element : elements) {
			RegistroPreco registro = new RegistroPreco();
			registro.setItem(new Item());

			Elements idItemElem = element.getElementsByClass("price-box");

			if (idItemElem.size() > 0) {
				registro.getItem().setId(Long.valueOf(idItemElem.first().attr("data-product-id")));

				Element idContainer = element.getElementsByClass("spc-product__qty").first();
				Element inputId = idContainer.select("input").first();
				registro.getItem().setId(10000 + Long.valueOf(inputId.id().replaceAll("spc_product_qty_", "")));

				registro.getItem().setNome(element.getElementsByClass("spc-product__name").text());

				Element oldPriceContainer = element.getElementsByClass("spc-product__price--special").first();
				registro.setPrecoOld(Double.valueOf(oldPriceContainer.text().replaceAll("R$", "").replace(",", ".")));

				Element finalPriceContainer = element.getElementsByClass("spc-product__price--regular").first();
				registro.setPreco(Double.valueOf(finalPriceContainer.text().replaceAll("R$", "").replace(",", ".")));

				Element qtdContainer = element.getElementsByClass("spc-product__pods-qty").first();
				registro.getItem().setQtd(Integer.valueOf(qtdContainer.select("span:not(:has(span))").first().text()));

				registro.getItem().setTipo("CAPSULA");
				lista.add(registro);
			}
		}
		return lista;

	}
}
