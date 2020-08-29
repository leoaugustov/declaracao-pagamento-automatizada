package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Parametros {

	private static final Properties PARAMETROS = new Properties();
	
	static {
		try {
			final InputStream arquivoCredenciais = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("app.properties");
			
			if(arquivoCredenciais != null) {
				PARAMETROS.load(arquivoCredenciais);
			}
		}catch(IOException ex) {}
	}
	
	
	
	public String getCaminhoWebDriver() {
		return PARAMETROS.getProperty("webdriver");
	}
	
	public String getUsuarioGmail() {
		return PARAMETROS.getProperty("gmail.usuario");
	}
	
	public String getRegistroAcademicoUna() {
		return PARAMETROS.getProperty("una.registro-academico");
	}
	
	public String getSenhaUna() {
		return PARAMETROS.getProperty("una.senha");
	}
	
	public String getClientId() {
		return PARAMETROS.getProperty("client-id");
	}
	
	public String getSecret() {
		return PARAMETROS.getProperty("secret");
	}
	
	public String getAccessToken() {
		return PARAMETROS.getProperty("access-token");
	}
	
	public String getRefreshToken() {
		return PARAMETROS.getProperty("refresh-token");
	}
	
}
