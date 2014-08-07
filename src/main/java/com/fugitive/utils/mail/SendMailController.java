/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.fugitive.utils.mail;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * A typical simple backing bean, that is backed to <code>helloWorld.xhtml</code>
 */
@ManagedBean(name = "sendMail")
@RequestScoped
public class SendMailController
{

    //properties
    private String recipient;
    private String subject;
    private String body;
    private String mailSent;
    private String resultMessage;

	/**
     * default empty constructor
     */
    public SendMailController()
    {
    }

    /**
     * Method that is backed to a submit button of a form.
     */
    public String send()
    {
    	
    	mailSent = "No";
        try {
			SendMailHelper mailHelper = new SendMailHelper();
			mailHelper.sendMail(recipient, subject, body);
			mailSent = "Yes";
	    	resultMessage = "Mail sent successfully"; 
		} catch (SendMailException e) {
			resultMessage = e.getMessage();
		}
        
        return "mailResult.xhtml";
    }

    //-------------------getter & setter

    public String getRecipient()
    {
        return recipient;
    }

    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }

    public String getMailSent() {
		return mailSent;
	}

	public void setMailSent(String mailSent) {
		this.mailSent = mailSent;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
