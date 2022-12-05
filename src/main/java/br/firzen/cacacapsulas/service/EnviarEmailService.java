package br.firzen.cacacapsulas.service;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import br.firzen.cacacapsulas.model.AlertaPreco;
import br.firzen.cacacapsulas.model.RegistroPreco;

@Service
@PropertySource(value = "application.properties")
public class EnviarEmailService {

	@Value("${APP_EMAIL}")
	public String appEmail;

	@Value("${APP_PASSWORD}")
	public String appPassword;

	public void sendMailAlertaCapsula(AlertaPreco alertaPreco, List<RegistroPreco> listaPrecosUsuario) {
		if(listaPrecosUsuario.size() > 0) {
			StringBuilder textoSb = new StringBuilder("Os seguintes itens estão em promoção:\n");
			for(RegistroPreco rp: listaPrecosUsuario) {
				textoSb.append(rp.getItem().getNome())
				.append( " - ")
				.append("R$ "+String.format("%.2f",rp.getPreco()))
				.append("\n");
			}
			textoSb.append("\nCaça-cápsulas");
			sendMail(alertaPreco.getUsuario().getEmail(), "Alerta de promoções | "+alertaPreco.getTipo() +"S", textoSb.toString());
		}
	}
	
	public void sendMail(String to, String assunto, String msg) {
		String from = "appcolecaodemangas@gmail.com";

		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "fgaqhetxdibwrmib");
			}
		});
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(assunto);

			// Now set the actual message
			message.setText(msg);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			System.out.println("Problemas no envio de emails");
		}
	}
}
