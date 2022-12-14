package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@PropertySource("classpath:config.properties")
public class CapsulaScraper implements IScraper {
	
	@Value("${url.capsulas}")
	private String URL_CAPSULAS;
	
	
	public List<RegistroPreco> extract() throws IOException {
		OkHttpClient client = new OkHttpClient.Builder()
			      .connectTimeout(180, TimeUnit.SECONDS)
			      .readTimeout(180, TimeUnit.SECONDS)
			      .writeTimeout(180, TimeUnit.SECONDS)
			      .build();

		List<RegistroPreco> lista = new ArrayList<>();
		String url = URL_CAPSULAS;
		String requestBody = "{\"query\":\"query CategoryList{categoryList(filters: { ids: {eq:\\\"18\\\"}}){children{id,name,products(sort: {position: ASC},pageSize: 100, currentPage: 1){total_count,items{id,name,stock_status,pods_per_cup,price_range{minimum_price{regular_price{value}final_price{value}}}}}}}}\"}";


		@SuppressWarnings("deprecation")
		Request request = new Request.Builder()
				.url(url)
				.post(RequestBody.create(MediaType.parse("application/json"), requestBody))
				.addHeader("User-Agent", "Mozilla/5.0")
				.addHeader("accept", "*/*")
				.addHeader("content-type", "application/json")
				//.addHeader("Host", "www.nescafe-dolcegusto.com.br")
				//.addHeader(HttpHeaders.ORIGIN, "www.nescafe-dolcegusto.com.br")
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
				System.out.println("Erro: " + response.code());
			}
		}
		return lista;

	}
}
