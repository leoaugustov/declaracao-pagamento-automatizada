package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArquivosNuvem {

	private final Drive drive;
	
	
	
	public void salvar(YearMonth dataReferencia, Optional<String> idDiretorioParaSalvar, 
			ByteArrayContent conteudoArquivo) throws IOException {
		
    	File metaDados = new File();
    	metaDados.setName(formatarDataComoNomeArquivo(dataReferencia));
    	
    	if(idDiretorioParaSalvar.isPresent()) {
    		metaDados.setParents(Arrays.asList(idDiretorioParaSalvar.get()));
    	}	
    	
		drive.files().create(metaDados, conteudoArquivo).execute();
	}
	
	
	
    private String formatarDataComoNomeArquivo(YearMonth data) {
    	return data.format(DateTimeFormatter.ofPattern("yyyyMM '- automático'"));
    }
	
}
