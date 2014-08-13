package com.fugitive.utils.mail;

public interface ISendMailHelper {

	void sendMail(String recipient, String subject, String body) 
		throws SendMailException;
	
}