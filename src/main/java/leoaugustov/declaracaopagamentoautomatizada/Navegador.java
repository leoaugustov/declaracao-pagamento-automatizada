package leoaugustov.declaracaopagamentoautomatizada;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Navegador {

	private static final String URL_INICIAL = "https://aluno.una.br/SOL/aluno/index.php/index/seguranca/dev/instituicao/3/acesso";
	private final WebDriver driver;
	private final WebDriverWait espera;

	
	
	public Navegador(WebDriver driver, WebDriverWait espera) {
		this.espera = espera;
		this.driver = driver;
		
		this.driver.get(URL_INICIAL);
	}
	
	
	
	public String pegarDataUltimaParcelaPaga(String ra, String senha) {
		realizarLogin(ra, senha);
		acessarExtratoFinanceiro();
		selecionarStatus();
		
		return buscarElemento(By.cssSelector(".lista h3")).getText();
		
	}
	
	public void solicitarEnvioDeclaracaoPagamentoUltimaParcelaPaga() {
		buscarElemento(By.cssSelector(".lista .lista__item .lista__coluna--c3 button")).click();
		buscarElemento(By.cssSelector("div[role=\"menu\"] div[role=\"menuitem\"]")).click();
	}
	
	
	
	private void realizarLogin(String ra, String senha) {
		buscarElemento(By.name("matricula")).sendKeys(ra);
		buscarElemento(By.name("senha")).sendKeys(senha);
		buscarElemento(By.id("logar")).click();
	}
	
	private void acessarExtratoFinanceiro() {
		//fecha tela modal caso existam
		clicarSeElementoExistir(By.cssSelector(".ui-dialog-buttonset button"));
		
		buscarElemento(By.cssSelector(".menu-search-bar i")).click();
		buscarElemento(By.cssSelector("li[data-target=\"#menu_6\"]")).click();
		buscarElemento(By.cssSelector("#menu_6 a li")).click();
	}
	
	private void selecionarStatus() {
		//fecha telas modais caso existam
		clicarSeElementoExistir(By.cssSelector(".conpass-close-button"));
		clicarSeElementoExistir(By.cssSelector(".conpass-close-button"));
		
		//realiza a seleção do status
		buscarElemento(By.cssSelector("#filtro_status .v-input__control")).click();
		buscarElemento(By.cssSelector("div[id*=\"pago-list-item\"]")).click();
	}
	
	/**
	 * Tenta buscar o elemento relacionado a referência informada e, caso ele exista, realiza um clique sobre ele.
	 */
	private void clicarSeElementoExistir(By referencia) {
		try {
			WebElement elemento = buscarElemento(referencia);
			elemento.click();
		}catch(TimeoutException e) {
			
		}
	}

	/**
	 * Busca no DOM o primeiro elemento relacionado a referência informada. 
	 * Espera até que o elemento seja visível e clicável (espera máxima de 10 segundos).
	 */
	private WebElement buscarElemento(By referencia) {
		return espera.until(elementToBeClickable(referencia));
	}
	
}
