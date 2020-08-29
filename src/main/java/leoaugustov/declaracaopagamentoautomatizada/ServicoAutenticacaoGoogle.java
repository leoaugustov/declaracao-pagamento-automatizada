package leoaugustov.declaracaopagamentoautomatizada;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServicoAutenticacaoGoogle {

	private final JsonFactory jsonFactory;
	private final Parametros parametros;
	
	/**
	 * Gera o objeto que encapsula a autenticação e a autorização OAuth2 com o Google.
	 */
	public Credential gerarCredenciais(HttpTransport httpTransport) {
		return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setClientSecrets(parametros.getClientId(), parametros.getSecret())
                .build()
                .setAccessToken(parametros.getAccessToken())
                .setRefreshToken(parametros.getRefreshToken());
	}
	
}
