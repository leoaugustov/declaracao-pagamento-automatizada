package leoaugustov.declaracaopagamentoautomatizada;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CaixaEntradaEmail {

	private final Gmail gmail;
	private final String usuarioGmail;
	
	
	
	public String buscarLinkDeclaracaoPagamento() throws IOException {
		String idEmail = buscarIdEmail();
		String corpoEmail =  Optional.of(buscarEmail(idEmail))
				.map(email -> email.getPayload().getBody().getData())
				.map(Base64::decodeBase64)
				.map(String::new)
				.orElse("");
		
		return Jsoup.parse(corpoEmail).selectFirst("blockquote p a").attr("href");
	}
	
	
	
	private String buscarIdEmail() throws IOException {
		List<Message> emails = gmail.users().messages()
			.list(usuarioGmail)
			.setQ("from:noreply@ulifemail.com.br subject:pagamento in:anywhere newer_than:1d")
			.execute()
			.getMessages();
		
		if(emails.isEmpty()) {
			throw new RuntimeException("Email não encontrado!");
		}
		
		return emails.get(0).getId();
	}
	
	private Message buscarEmail(String idEmail) throws IOException {
		return gmail.users().messages()
			.get(usuarioGmail, idEmail)
			.execute();
	}
	
}
