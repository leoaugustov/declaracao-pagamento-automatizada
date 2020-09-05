package leoaugustov.declaracaopagamentoautomatizada;

import java.net.URL;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.gmail.Gmail;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class App {
	
	private static final String NOME_APLICACAO = "Declaraçao de Pagamento Automatizada";
	private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
    public static void main(String[] args) {
    	Parametros parametros = new Parametros();
    	
    	System.setProperty("webdriver.chrome.driver", parametros.getCaminhoWebDriver());
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        
        try {
        	DataUltimaParcelaPaga dataUltimaParcelaPaga = new DataUltimaParcelaPaga();
        	Navegador navegador = new Navegador(driver, new WebDriverWait(driver, 10));
            YearMonth data = transformarData(navegador.pegarDataUltimaParcelaPaga(parametros.getRegistroAcademicoUna(), parametros.getSenhaUna()));
            
            if(dataUltimaParcelaPaga.pegar().isPresent() == false || dataUltimaParcelaPaga.pegar().get().isBefore(data)) {
            	navegador.solicitarEnvioDeclaracaoPagamentoUltimaParcelaPaga();
            	
            	log.info("Documento enviado para o email.");
            	
            	dataUltimaParcelaPaga.atualizar(data);
            	
                Thread.sleep(10000); // aguarda para garantir que o email vai estar na caixa de entrada.
                
            	ServicoAutenticacaoGoogle servicoAutenticacao = new ServicoAutenticacaoGoogle(JSON_FACTORY, parametros);
            	NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
            	
            	Gmail gmail = new Gmail.Builder(netHttpTransport, JSON_FACTORY, servicoAutenticacao.gerarCredenciais(netHttpTransport))
        				.setApplicationName(NOME_APLICACAO)
        				.build();
            	
            	CaixaEntradaEmail caixaEntradaEmail = new CaixaEntradaEmail(gmail, parametros.getUsuarioGmail());
            	
            	Drive drive = new Drive.Builder(netHttpTransport, JSON_FACTORY, servicoAutenticacao.gerarCredenciais(netHttpTransport))
        				.setApplicationName(NOME_APLICACAO)
        				.build();
            	
            	ArquivosNuvem nuvem = new ArquivosNuvem(drive);
            	
            	byte[] arquivo = IOUtils.toByteArray(new URL(caixaEntradaEmail.buscarLinkDeclaracaoPagamento()));
            	nuvem.salvar(data, parametros.getIdDiretorioParaSalvarDrive(), new ByteArrayContent("application/pdf", arquivo));
            	
            	log.info("A declaração de pagamento da mensalidade do mês " +data+ " foi salva no Google Drive com sucesso.");
            }else {
            	log.info("O documento referente a data " +data.plusMonths(1)+ " ainda não está disponível.");
            }
        
        }catch(Exception e) {
        	log.error("Ocorreu um erro durante o processo.", e);
        }finally {
        	driver.quit();
        }
    }
    
    
    
    /**
     * Transforma a data de texto para YearMonth.
     */
    private static YearMonth transformarData(String dataComoTexto) {
    	return YearMonth.parse(dataComoTexto, DateTimeFormatter.ofPattern("MMMM/yyyy", Locale.getDefault()));
    }
    
}
