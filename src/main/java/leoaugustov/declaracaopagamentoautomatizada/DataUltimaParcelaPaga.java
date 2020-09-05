package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.YearMonth;
import java.util.Optional;

public class DataUltimaParcelaPaga {

	private static final String NOME_ARQUIVO = "data-ultima-parcela-paga.txt";
	
	public void atualizar(YearMonth data) throws IOException {
		Path referenciaArquivo = Paths.get(NOME_ARQUIVO);
		
		if(Files.exists(referenciaArquivo) == false) {
			Files.createFile(referenciaArquivo);
		}
		
		Files.write(referenciaArquivo, data.toString().getBytes());
	}
	
	public Optional<YearMonth> pegar() throws IOException {
		Path referenciaArquivo = Paths.get(NOME_ARQUIVO);
		
		YearMonth data = null;
		if(Files.exists(referenciaArquivo)) {
			data = YearMonth.parse(Files.readAllLines(referenciaArquivo).get(0));
		}
		
		return Optional.ofNullable(data);
	}
	
}
