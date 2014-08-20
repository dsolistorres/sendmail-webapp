package com.fugitive.utils.mail;

public interface ISendMailHelper {

    String EMAIL_FROM_ADDRESS = "email.from.address";

    String EMAIL_FROM_NAME = "email.from.name";

    String EMAIL_USER_NAME = "email.user.name";

    String EMAIL_USER_PASSWORD = "email.user.password";
    
	void sendMail(String recipient, String subject, String body) 
		throws SendMailException;
	
}