package leoaugustov.declaracaopagamentoautomatizada;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;

public class App {
	
	private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
    public static void main(String[] args) throws Exception {
    	final Parametros parametros = new Parametros();
    	
    	System.setProperty("webdriver.chrome.driver", parametros.getCaminhoWebDriver());
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        
        try {
        	DataUltimaParcelaPaga dataUltimaParcelaPaga = new DataUltimaParcelaPaga();
        	Navegador navegador = new Navegador(driver, new WebDriverWait(driver, 10));
            YearMonth data = transformarData(navegador.pegarDataUltimaParcelaPaga(parametros.getRegistroAcademicoUna(), parametros.getSenhaUna()));
            
            if(dataUltimaParcelaPaga.pegar().isBefore(data)) {
            	navegador.solicitarEnvioDeclaracaoPagamentoUltimaParcelaPaga();
            	
            	dataUltimaParcelaPaga.atualizar(data);
            }
            
            Thread.sleep(10000); // aguarda para garantir que o email vai estar na caixa de entrada.
            
        	ServicoAutenticacaoGoogle servicoAutenticacao = new ServicoAutenticacaoGoogle(JSON_FACTORY, parametros);
        	NetHttpTransport netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
        	Gmail gmail = new Gmail.Builder(netHttpTransport, JSON_FACTORY, servicoAutenticacao.gerarCredenciais(netHttpTransport))
    				.setApplicationName("Declaraçao de Pagamento Automatizada")
    				.build();
        	
        	CaixaEntradaEmail caixaEntradaEmail = new CaixaEntradaEmail(gmail, parametros.getUsuarioGmail());
        	System.out.println(caixaEntradaEmail.buscarLinkDeclaracaoPagamento());
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
