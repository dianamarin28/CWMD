package edu.ubb.cwmdEjb.util;

import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.ubb.cwmdEjbClient.interfaces.UserBeanInterface;

@SessionScoped
public class EmailHelper {

	@EJB
	private UserBeanInterface userBeanInterface;

	public static void sendEmail(String address, String subject, String message) {

		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("cwmdapp", "parola01");
			}
		});
		try {

			Message mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress("cwmdapp@gmail.com"));
			mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", "cwmdapp", "parola01");
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
