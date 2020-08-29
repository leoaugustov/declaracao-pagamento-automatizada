package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArquivosNuvem {

	private static final String ID_DIRETORIO_PARA_SALVAR = "1MNzvaikMhdz8CQt2v0hWKoGUolhblMJW";
	private final Drive drive;
	
	
	
	public void salvar(YearMonth dataReferencia, ByteArrayContent conteudoArquivo) throws IOException {
    	File metaDados = new File();
    	metaDados.setName(formatarDataComoNomeArquivo(dataReferencia));
    	metaDados.setParents(Arrays.asList(ID_DIRETORIO_PARA_SALVAR));
    	
		drive.files().create(metaDados, conteudoArquivo).execute();
	}
	
	
	
    private String formatarDataComoNomeArquivo(YearMonth data) {
    	return data.format(DateTimeFormatter.ofPattern("yyyyMM '- automático'"));
    }
	
}
