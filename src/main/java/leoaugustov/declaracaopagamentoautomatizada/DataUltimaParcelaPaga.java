package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;

public class DataUltimaParcelaPaga {

	private static final String NOME_ARQUIVO = "src/main/resources/data-ultima-parcela-paga.txt";
	
	public void atualizar(YearMonth data) throws IOException {
		Path caminho = Paths.get(NOME_ARQUIVO);
	    Files.write(caminho, data.toString().getBytes());
	}
	
	public YearMonth pegar() throws IOException {
		Path caminho = Paths.get(NOME_ARQUIVO);
		return YearMonth.parse(Files.readAllLines(caminho).get(0));
	}
	
}
