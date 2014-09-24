package com.fugitive.utils.mail;

import java.io.StringWriter;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("mailGenerator")
public class MailGenerator implements IMailGenerator {

    private Logger log = LoggerFactory.getLogger( getClass() );
	
    @Inject
    @Named(value = "velocityEngine")
    private VelocityEngine velocityEngine;
		
	public String generateMail(String templateName, String body) {
		
		VelocityContext context = new VelocityContext();
		context.put("body", body);
		
        String templateFile = "templates/" + templateName + ".vm";

        StringWriter writer = new StringWriter();
        try {
        	velocityEngine.mergeTemplate( templateFile, "UTF-8", context, writer );
        } catch ( ResourceNotFoundException e ) {
            log.error( "No such template: '{}'.", templateFile );
        } catch ( ParseErrorException | MethodInvocationException e ) {
            log.error( "Unable to generate email for template '{}': {}", templateFile, e.getMessage(), e );
        }
        
        return writer.getBuffer().toString();
        
	}

}
