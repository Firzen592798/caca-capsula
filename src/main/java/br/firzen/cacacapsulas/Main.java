package br.firzen.cacacapsulas;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.firzen.cacacapsulas.httpscraper.GraphQLScraper;
import br.firzen.cacacapsulas.httpscraper.IScraper;
import br.firzen.cacacapsulas.model.RegistroPreco;

public class Main {
	
	public static void minerar() throws IOException {
		IScraper caixa = new GraphQLScraper();
		List<RegistroPreco> lista = caixa.extract();
		for(RegistroPreco reg: lista) {
			System.out.println(reg.toString());
			System.out.println("=================================");
		}
	}
	
	public static void sendMail() {
	      // Recipient's email ID needs to be mentioned.
	      String to = "appcolecaodemangas@gmail.com";

	      // Sender's email ID needs to be mentioned
	      String from = "appcolecaodemangas@gmail.com";

	      // Assuming you are sending email from localhost
	      String host = "smtp.gmail.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);
	      properties.put("mail.smtp.port", "465");
	      properties.put("mail.smtp.ssl.enable", "true");
	      properties.put("mail.smtp.auth", "true");
	     // properties.setProperty("mail.user", from);
	     // properties.setProperty("mail.password", "fgaqhetxdibwrmib");

	      // Get the default Session object.
	      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication(from, "senha");

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
	         message.setSubject("This is the Subject Line!");

	         // Now set the actual message
	         message.setText("This is actual message");

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}
	
	public static void main(String[] args) throws IOException {
		sendMail();
	}
}
