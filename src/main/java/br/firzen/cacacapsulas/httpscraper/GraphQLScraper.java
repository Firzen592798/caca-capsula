package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import br.firzen.cacacapsulas.model.Item;
import br.firzen.cacacapsulas.model.RegistroPreco;

@Component
public class GraphQLScraper implements IScraper {
	public List<RegistroPreco> extract() throws IOException {
		List<RegistroPreco> lista = new ArrayList<>();
		String requestBody = "{\"query\":\"query CategoryList{categoryList(filters: { ids: {eq:\\\"18\\\"}}){children{id,name,products(sort: {position: ASC},pageSize: 100, currentPage: 1){total_count,items{id,name,stock_status,pods_per_cup,price_range{minimum_price{regular_price{value}final_price{value}}}}}}}}\"}";
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost("https://www.nescafe-dolcegusto.com.br/graphql");

		StringEntity params = new StringEntity(requestBody);
		request.addHeader("content-type", "application/json");
		request.addHeader("accept", "application/json");
		request.addHeader("accept-encoding", "gzip, deflate, br");
		request.setEntity(params);
		HttpResponse response = httpClient.execute(request);
		String responseBody = EntityUtils.toString(response.getEntity());
		System.out.println(responseBody);
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
		return lista;
	}
}
