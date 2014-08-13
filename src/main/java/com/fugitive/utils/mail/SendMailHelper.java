package com.fugitive.utils.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service( "mailer" )
public class SendMailHelper implements ISendMailHelper {

    private Logger log = LoggerFactory.getLogger( getClass() );
	
	public void sendMail(String recipient, String subject, String body) 
			throws SendMailException {

		try {
			Context initial = new InitialContext();
			Session session = 
			    (Session) initial.lookup(
		        "java:comp/env/mail/Session");

			log.info("Mail session retrieved from JNDI");
			
			Properties sessionProperties = session.getProperties();
			log.info("Session properties : {}", sessionProperties);
			log.info("Mail user : {}", System.getProperty("mail.user"));
			
			Message message = new MimeMessage(session);
			
			InternetAddress[] address = {new InternetAddress(recipient)};
		    message.setRecipients(Message.RecipientType.TO, address);
		    message.setSubject(subject);
		    message.setText(body);
		    
		    Transport.send(message);
		    
			log.info("Mail message sent to: {}", recipient);
		    
		} catch (NamingException e) {
			log.error("Error getting JNDI mail session", e);
			throw new SendMailException("Couldn't get mail session: " + e.getMessage());
		} catch (MessagingException e) {
			log.error("Error sending mail message", e);
			throw new SendMailException("Couldn't send mail message: " + e.getMessage());
		}
	       		
	}
	
}
