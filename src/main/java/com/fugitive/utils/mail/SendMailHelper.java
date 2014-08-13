package com.fugitive.utils.mail;

import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service( "mailer" )
public class SendMailHelper implements ISendMailHelper {

    private Logger log = LoggerFactory.getLogger( getClass() );

    @Inject
    @Named( value = "mailSender" )
    private JavaMailSender javaMailSender;
    
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
			
			SimpleMailMessage message = new SimpleMailMessage();
			
            message.setSubject( subject );
            message.setText( body );

            message.setTo( recipient );

            log.debug( "mail content {}", body );
		    
		    javaMailSender.send( message );
		    
			log.info("Mail message sent to: {}", recipient);
		    
		} catch (NamingException e) {
			log.error("Error getting JNDI mail session", e);
			throw new SendMailException("Couldn't get mail session: " + e.getMessage());
		} catch (MailException e) {
			log.error("Error sending mail message", e);
			throw new SendMailException("Couldn't send mail message: " + e.getMessage());
		}
	       		
	}
	
}
