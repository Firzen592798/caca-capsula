package br.firzen.cacacapsulas.httpscraper;

import java.io.IOException;
import java.util.List;

import br.firzen.cacacapsulas.model.RegistroPreco;

public interface IScraper {
	public List<RegistroPreco> extract() throws IOException;
}
