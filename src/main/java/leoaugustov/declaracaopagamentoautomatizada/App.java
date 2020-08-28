package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {
	
    public static void main(String[] args) throws IOException, GeneralSecurityException {
    	if(args.length < 3) {
    		throw new IllegalArgumentException("Informe o caminho do WebDriver, o R.A. e a senha para login no portal respectivamente!");
    	}
    	
    	final String caminhoWebDriver = args[0];
    	final String ra = args[1];
    	final String senha = args[2];
    	
    	System.setProperty("webdriver.chrome.driver", caminhoWebDriver);
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments("--headless"));
        
        try {
        	DataUltimaParcelaPaga dataUltimaParcelaPaga = new DataUltimaParcelaPaga();
        	Navegador navegador = new Navegador(driver, new WebDriverWait(driver, 10));
            YearMonth data = transformarData(navegador.pegarDataUltimaParcelaPaga(ra, senha));
            
            if(dataUltimaParcelaPaga.pegar().isBefore(data)) {
            	navegador.solicitarEnvioDeclaracaoPagamentoUltimaParcelaPaga();
            	
            	dataUltimaParcelaPaga.atualizar(data);
            }
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
