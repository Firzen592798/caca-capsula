package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class CapsulaScraper implements IScraper {
	public List<RegistroPreco> extract() throws IOException {
		OkHttpClient client = new OkHttpClient();

		List<RegistroPreco> lista = new ArrayList<>();
		String url = "https://www.nescafe-dolcegusto.com.br/graphql";
		String requestBody = "{\"query\":\"query CategoryList{categoryList(filters: { ids: {eq:\\\"18\\\"}}){children{id,name,products(sort: {position: ASC},pageSize: 100, currentPage: 1){total_count,items{id,name,stock_status,pods_per_cup,price_range{minimum_price{regular_price{value}final_price{value}}}}}}}}\"}";


		@SuppressWarnings("deprecation")
		Request request = new Request.Builder()
				.url(url)
				.post(RequestBody.create(MediaType.parse("application/json"), requestBody))
				.addHeader("User-Agent", "Mozilla/5.0")
				.addHeader("accept", "*/*")
				.addHeader("content-type", "application/json")
				.addHeader("Access-Control-Request-Method", "POST")
				.addHeader("Access-Control-Request-Headers", "Content-Type")
				.addHeader("Cookie", "_abck=F7341FB4116FFE36757E696DF0EC0D10~-1~YAAQl2dCF3CkPByFAQAAydZrjQmZEP5z7Zc8NLpFq0t0L1c8FvO6zFaWDSBcSzbaIIQL+3riMWcE02WD+qTDDAxEE3VvbCpu0AAgXAi/FmhYGf4BBARQcOi5UqCz+xK6TaF21MoRDi1XyusYsH9HvSGNOWZlvgKskPWYlfGJLCgXtWWYE4vwObxwNwXB06T4HuLDCkxSnTlUhdXunu1CBQPWU17R8N9ohqd0seM1U281yJ7ffkuXwAnINguGe+fueTaNGBJZ2CSrRftCIAQ065nk8e/KFv8mi+BlZ/cta1A+QzxLrEOgbXjae3zANgY9lMmkyZ8kWdPGgGDgtcrwTkGbDMceFblWO9i3MGhV3bcintxwnlAsJ88RpXRqn2SmPGK3ncySnbI0pP3VmbBud7i5bRSYTA==~-1~-1~-1; bm_sz=87D136479D781EFF48BA5083E4D3027A~YAAQl2dCF3GkPByFAQAAydZrjRKazTpVv5+7XdqZ4wj1sUcXOmQKBu5x0ZRjEttP0BUrGisNMZdo0Twwu1BihpgNXiKVuS/7GOe3qeUHS7dBt063LE5i4LpofFC070faMHzRDyzfPJ7cNTOuKj2iU/WCiZNDCKiWIMfbLWIWR7NymY3Xv9jD/kslNKo+nx+E0jhgcIvQtb2YMFT4ZYYKHxX9RPpjXWnwqypyf+77WfHZqqV4wsfgAjEUVk0hHw+SBwDb//zdR7nHCN3XUhTPEYpiCyVOE1Eb3zxHnsp5jthYxCV/CZBoKjC1v6HlpTVRXu4=~4408882~3621177; PHPSESSID=41931a8dbd12ad7818b84f508f6a9e3a; mage-messages=%5B%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%2C%7B%22type%22%3A%22error%22%2C%22text%22%3A%22Chave%20de%20formul%5Cu00e1rio%20inv%5Cu00e1lida.%20Por%20favor%2C%20atualize%20a%20p%5Cu00e1gina.%22%7D%5D; private_content_version=f3e6412dee06aebb2957ada383c60300")
				.build();

		try (Response response = client.newCall(request).execute()) {
			// Verifica se a resposta foi bem-sucedida
			
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				JSONObject object = new JSONObject(responseBody);
				JSONArray categoryList = object.getJSONObject("data").getJSONArray("categoryList");
				for(int i = 0; i < categoryList.length(); i++) {
					JSONArray children = categoryList.getJSONObject(i).getJSONArray("children");
					for(int j = 0; j < children.length(); j++) {
						JSONObject categoria = children.getJSONObject(j);
						JSONObject products = categoria.getJSONObject("products");
						JSONArray items = products.getJSONArray("items");
						for(int z = 0; z < items.length(); z++) {
							JSONObject capsulaObj = items.getJSONObject(z);
							if(capsulaObj.getString("stock_status").equals("IN_STOCK")) {
								RegistroPreco rp = new RegistroPreco();
								rp.setItem(new Item());
								rp.getItem().setTipo("CAPSULA");
								rp.getItem().setId(10000 + capsulaObj.getLong("id"));
								rp.getItem().setQtd(1);
								rp.getItem().setNome(capsulaObj.getString("name"));
								Double original =  capsulaObj.getJSONObject("price_range").getJSONObject("minimum_price").getJSONObject("regular_price").getDouble("value");
								Double preco = capsulaObj.getJSONObject("price_range").getJSONObject("minimum_price").getJSONObject("final_price").getDouble("value");
								rp.setPreco(preco);
								rp.setPrecoOld(original);
								if(capsulaObj.get("pods_per_cup") == null) {
									rp.getItem().setQtd(2);
								}
								lista.add(rp);
							}
						}
					}
				}
				
			} else {
				System.out.println("Erro: " + response.code() +" "+response.body());
			}
		}
		return lista;

	}
}
